package com.ssafy.domain.image.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.domain.image.model.dto.response.TextResponse;

public interface ImageService {

	List<Map<String, String>> imageToText(MultipartFile multipartFile) throws IOException;
}
