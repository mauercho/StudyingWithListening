package com.ssafy.domain.koala.model.entity;

import com.ssafy.domain.koala.repository.KoalaRepository;
import com.ssafy.domain.user.model.entity.Auth;
import com.ssafy.domain.user.model.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(classes = com.ssafy.KoalaBackApplication.class)
class KoalaTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private KoalaRepository koalaRepository;

    @Test
    public void testEntity() throws Exception {
//        Auth auth = new Auth("user");
//        em.persist(auth);
//        User user = new User("loginId", "password", auth, "name", "nickname", 0, 0L, 1);
//        em.persist(user);
//
//        Koala koala = new Koala(user, "koalaName", 1, 0, 1);
//        em.persist(koala);
//
//        Koala findKoala = em.find(Koala.class, koala.getKoalaId());
//
//        assertEquals(koala, findKoala);
//
//        em.remove(findKoala);
//
//        assertNull(em.find(Koala.class, koala.getKoalaId()));
    }


}