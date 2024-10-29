package com.ssafy.global.auth.jwt;

import com.ssafy.global.auth.jwt.dto.JwtToken;
import com.ssafy.global.error.exception.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final long accessTokenExpireTime;
    private final long refreshTokenExpireTime;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.access-token-ms}") long accessTokenExpireTime, @Value("${jwt.refresh-token-ms}") long refreshTokenExpireTime) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    public JwtToken generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        // Access Token 생성
        String accessToken = Jwts.builder().setSubject(authentication.getName()).claim("auth", authorities).claim("type", "access").setExpiration(new Date(System.currentTimeMillis() + accessTokenExpireTime)).signWith(secretKey, SignatureAlgorithm.HS256).compact();

        String refreshToken = Jwts.builder().setSubject(authentication.getName()).setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpireTime)).claim("type", "refresh").signWith(secretKey, SignatureAlgorithm.HS256).compact();

        return JwtToken.builder().grantType("Bearer").accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public JwtToken generateNewToken(Authentication authentication, String refreshToken) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        // Access Token 생성
        String accessToken = Jwts.builder().setSubject(authentication.getName()).claim("auth", authorities).claim("type", "access").setExpiration(new Date(System.currentTimeMillis() + accessTokenExpireTime)).signWith(secretKey, SignatureAlgorithm.HS256).compact();

        Claims claims = parseClaims(refreshToken);
        Date refreshTokenExpiration = claims.getExpiration();
        long currentTime = System.currentTimeMillis();
        long fiveHoursInMills = 5 * 60 * 60 * 1000L;
        if (refreshTokenExpiration.getTime() - currentTime < fiveHoursInMills) {
            refreshToken = Jwts.builder().setSubject(authentication.getName()).setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpireTime)).claim("type", "refresh").signWith(secretKey, SignatureAlgorithm.HS256).compact();
        }

        return JwtToken.builder().grantType("Bearer").accessToken(accessToken).refreshToken(refreshToken).build();
    }

    // JWT 토큰에서 사용자 인증 정보를 추출하여 Authentication 객체를 생성하는 메서드
    public Authentication getAuthentication(String accessToken) {
        // Authentication은 Spring Security에서 인증된 사용자의 정보를 나타내는 인터페이스이다.
        // Jwt 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("type").equals("refresh")) return null;

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        // UserDetails 객체 만들어서 Authentication return
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // accessToken
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new TokenException("Invalid JWT Token", HttpStatus.FORBIDDEN);
        } catch (ExpiredJwtException e) {
            throw new TokenException("Expired JWT Token", HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException e) {
            throw new TokenException("Unsupported JWT Token", HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            throw new TokenException("JWT claims string is empty.", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return false;
        }
    }

}
