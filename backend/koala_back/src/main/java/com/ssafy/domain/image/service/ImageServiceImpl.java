package com.ssafy.domain.image.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.domain.image.model.dto.request.GeminiRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {

	private static final String bucketName = "koalabucket1";
	private final AmazonS3 amazonS3;
	private final GeminiService geminiService;

	private static final String baseUrl = "https://m.search.naver.com/p/csearch/ocontent/util/SpellerProxy";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
	private static final String REFERER = "https://search.naver.com/";

	@Override
	public List<Map<String, String>> imageToText(MultipartFile multipartFile) throws IOException {
		String originalName = multipartFile.getOriginalFilename();
		String filename = System.currentTimeMillis() + "_" + originalName;

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(multipartFile.getContentType());
		objectMetadata.setContentLength(multipartFile.getInputStream().available());

		amazonS3.putObject(bucketName, filename, multipartFile.getInputStream(), objectMetadata);

		String accessUrl = amazonS3.getUrl(bucketName, filename).toString();
		log.info("File uploaded to S3: {}", accessUrl);

		// 이미지 데이터를 Base64로 인코딩
		byte[] imageBytes = multipartFile.getBytes();
		String base64Image = Base64.getEncoder().encodeToString(imageBytes);

		// 제미나이 API를 사용하여 텍스트 추출
		GeminiRequest.InlineData inlineData = new GeminiRequest.InlineData(multipartFile.getContentType(), base64Image);
		String extractedText = geminiService.getCompletionWithImage(
			"이 이미지에서 문장을 추출해줘. \n"
				+ "괄호, 슬래시(/), 대시(-) 등 특수문자가 있는 문장은 반드시 반환에서 제외해줘. 문장에는 한글, 마침표(.), 쉼표(,), 느낌표(!), 물음표(?)만 있어야 해. \n"
				+ "마침표로 끝나는 구체적인 문장만 반환해줘. 모든 문장은 꼭 마침표로 끝나야 해. 한 문장마다 큰따옴표로 묶어줘.",
			inlineData);

		log.info("Extracted text from image: {}", extractedText);

		// 텍스트를 문장별로 분리하여 객체 리스트에 담음
		List<String> texts = extractSentences(extractedText);
		List<String> resultTexts = new ArrayList<>();
		String passportKey = null;

		// passportKey 확인
		passportKey = readKey();
		try {
			// passportKey 유효한지 확인
			checkSpelling("테스트 문장", passportKey);
			log.info(passportKey + "는 유효합니다.");
		} catch (Exception e) {
			try {
				passportKey = updateKey();
				log.info(passportKey+"를 업데이트 하였습니다.");
			} catch (IOException updateError) {
				resultTexts = texts;
				log.warn("passportKey 오류로 맞춤법 검사를 건너뜁니다.");
			}
		}

		if (resultTexts.isEmpty()) {
			// 문제 없다면 맞춤법 검사 시작
			log.info("맞춤법 검사를 시작합니다.");
			for (String text : texts) {
				try {
					resultTexts.add(checkSpelling(text, passportKey));
				} catch (IOException e) {
					resultTexts.add(text);
				}
			}
		}

		// [{sentence_text:어쩌구}, ...] 형식으로 보내기
		return parseJson(resultTexts);
	}

	// 파싱
	public static List<String> extractSentences(String text) {
		List<String> sentences = new ArrayList<>();
		String[] lines = text.split("\n");
		for (String line : lines) {
			if (line.startsWith("\"") && line.endsWith("\"")) {
				String sentence = line.substring(1, line.length() - 1);
				int length = sentence.length();
				if (length >= 5 && length <= 36) {
					sentences.add(sentence);
				}
			}
		}
		return sentences;
	}

	// 스펠링 체크
	public static String checkSpelling(String text, String passportKey) throws IOException {
		String encodedText = URLEncoder.encode(text, "UTF-8");
		String payload = "color_blindness=0&q=" + encodedText + "&passportKey=" + passportKey;
		URL url = new URL(baseUrl + "?" + payload);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("User-Agent", USER_AGENT);
		connection.setRequestProperty("Referer", REFERER);
		int responseCode = connection.getResponseCode();
		log.info("Response Code : " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { // 200이면 정상
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuilder content = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			connection.disconnect();

			String response = content.toString();
			// JSON 파싱
			JSONObject data = new JSONObject(response);
			String html = data.getJSONObject("message").getJSONObject("result").getString("notag_html");
			System.out.println(html);
			return html;
		} else {
			throw new IOException("Failed to connect, HTTP response code: " + responseCode);
		}
	}

	public static String readKey() {
		Path path = Paths.get("passportKey.txt");
		if (Files.exists(path)) {
			try {
				return new String(Files.readAllBytes(path));
			} catch (IOException e) {
				return null;
			}
		} else {
			try {
				Files.createFile(path);
				return null;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static String updateKey() throws IOException {
		String url = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=맞춤법검사기";
		Document doc = Jsoup.connect(url).get();
		String html = doc.html();

		Pattern pattern = Pattern.compile("passportKey=([a-zA-Z0-9]+)");
		Matcher matcher = pattern.matcher(html);

		String token = null;
		if (matcher.find()) {
			token = java.net.URLDecoder.decode(matcher.group(1), "UTF-8");
			try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("passportKey.txt"))) {
				writer.write(token);
			}
		}
		return token;
	}

	public static List<Map<String, String>> parseJson(List<String> sentences) {
		List<Map<String, String>> jsonList = new ArrayList<>();
		for (String sentence : sentences) {
			Map<String, String> sentenceMap = new HashMap<>();
			sentenceMap.put("sentence_text", sentence);
			jsonList.add(sentenceMap);
		}
		return jsonList;
	}

}
