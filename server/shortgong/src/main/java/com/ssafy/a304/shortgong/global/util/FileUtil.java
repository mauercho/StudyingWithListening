package com.ssafy.a304.shortgong.global.util;

import static com.ssafy.a304.shortgong.global.errorCode.FileErrorCode.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.a304.shortgong.global.error.CustomException;
import com.ssafy.a304.shortgong.global.validator.FileValidator;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileUtil {

	private static AmazonS3 staticAmazonS3Client;

	private static String staticUserProfileFolderPath;

	private static String staticSummaryListFolderPath;

	private static String staticUploadContentFolderPath;

	private static String staticBucket;

	private static CloudFrontSignedUrlUtil staticCloudFrontSignedUrlUtil;

	private final AmazonS3 amazonS3Client;

	private final String userProfileFolderPath;

	private final String summaryListFolderPath;

	private final String uploadContentFolderPath;

	private final String bucket;

	private final CloudFrontSignedUrlUtil cloudFrontSignedUrlUtil;

	@Autowired
	public FileUtil(AmazonS3 amazonS3Client,
		CloudFrontSignedUrlUtil cloudFrontSignedUrlUtil,
		@Value("${file.path.user-profile-folder}") String userProfileFolderPath,
		@Value("${file.path.summary-folder}") String summaryListFolderPath,
		@Value("${file.path.upload-content-folder}") String uploadContentFolderPath,
		@Value("${cloud.aws.s3.bucket}") String bucket
	) {

		this.amazonS3Client = amazonS3Client;
		this.userProfileFolderPath = userProfileFolderPath;
		this.summaryListFolderPath = summaryListFolderPath;
		this.uploadContentFolderPath = uploadContentFolderPath;
		this.bucket = bucket;
		this.cloudFrontSignedUrlUtil = cloudFrontSignedUrlUtil;
	}

	/**
	 * 유저 이미지 업로드하기
	 * @return 파일명
	 * @author 정재영
	 */
	public static String uploadUserProfileImgFileByUuid(MultipartFile file, String uuid) throws
		CustomException {

		FileValidator.checkProfileImageExt(getExtension(file));
		return upload(convert(file), staticUserProfileFolderPath, uuid, getExtension(file));
	}

	/**
	 * 유저 이미지 업로드하기
	 * @return 파일명
	 * @author 정재영
	 */
	public static String uploadContentFileByUuid(MultipartFile file, String uuid) {
		// img 뿐만 아니라 txt 파일도 올라갈 수 있음
		// FileValidator.checkOcrImageExt(getExtension(file));
		return upload(convert(file), staticUploadContentFolderPath, uuid, getExtension(file));
	}

	/**
	 * 해당 요약집 폴더에 문자 보이스 mp3 파일 업로드하기
	 * @return 파일명
	 * @author 정재영
	 */
	public static String uploadSentenceVoiceFileByUuid(byte[] voiceData, String summaryUuid,
		String mp3FileUuid) throws CustomException {

		String ext = "mp3";
		File voiceFile = convertByteArrayToFile(voiceData, mp3FileUuid, ext);

		return upload(voiceFile, staticSummaryListFolderPath + "/" + summaryUuid, mp3FileUuid, ext);

	}

	public static String uploadSentenceVoiceFileByUuid(MultipartFile file, String summaryUuid,
		String mp3FileUuid) throws
		CustomException {

		FileValidator.checkMp3Ext(getExtension(file));
		return upload(convert(file), staticSummaryListFolderPath + "/" + summaryUuid, mp3FileUuid, getExtension(file));
	}

	// TODO : 세 get 메서드 모두 getFileUrl(String fileName) 로 통일 할 지 고민하기
	public static String getUserProfileImgUrl(String fileName) throws CustomException {

		return staticCloudFrontSignedUrlUtil.generateSignedUrl(fileName);
	}

	public static String getSentenceVoiceFileUrl(String fileName) throws CustomException {

		return staticCloudFrontSignedUrlUtil.generateSignedUrl(fileName);
	}

	public static String getUploadContentUrl(String fileName) throws CustomException {

		return staticCloudFrontSignedUrlUtil.generateSignedUrl(fileName);
	}

	public static String getExtension(MultipartFile file) throws CustomException {

		String originalFilename = file.getOriginalFilename();
		return getExtensionStringFromFileName(originalFilename);
	}

	public static String getExtensionStringFromPreSignedUrl(String s3Url) throws CustomException {
		
		FileValidator.checkFileNonEmpty(s3Url);
		int dotIndex = s3Url.lastIndexOf(".");
		int questionMarkIndex = s3Url.indexOf("?", dotIndex);

		if (dotIndex == -1) {
			throw new CustomException(EXTENSION_FIND_FAILED);
		}
		return s3Url.substring(dotIndex + 1, questionMarkIndex);
	}

	public static String getExtensionStringFromFileName(String fileName) throws CustomException {

		FileValidator.checkFileNonEmpty(fileName);
		int dotIndex = fileName.lastIndexOf(".");

		if (dotIndex == -1) {
			throw new CustomException(EXTENSION_FIND_FAILED);
		}
		return fileName.substring(dotIndex + 1);
	}

	public static boolean isExistUserProfileImgFile(String fileName) throws CustomException {

		return true;
	}

	public static boolean isExistSentenceVoiceFile(MultipartFile file, String uuid) throws CustomException {

		return true;
	}

	public static void deleteUserProfileImgFile(String fileName) {

		deleteFileFromS3(staticUserProfileFolderPath, fileName);
	}

	public static void deleteSentenceVoiceFile(String summaryUuid, String fileName) {

		deleteFileFromS3(staticSummaryListFolderPath + "/" + summaryUuid, fileName);
	}

	public static Path createTextFile(String text, String filename) {
		// 파일 경로 지정
		Path filePath = Paths.get(filename + ".txt");

		// 텍스트 파일 생성 및 내용 쓰기
		try {
			Files.write(filePath, text.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			// TODO : CustomException 으로 변경하기
			throw new IllegalArgumentException(e);
		}

		return filePath;
	}

	private static void deleteFileFromS3(String folderPath, String fileName) throws CustomException {

		try {
			staticAmazonS3Client.deleteObject(staticBucket, folderPath + "/" + fileName);
		} catch (Exception e) {
			throw new CustomException(S3_FILE_DELETION_FAILED); // 삭제 실패에 대한 예외 처리
		}
	}

	/**
	 * @return 파일명
	 * @author 정재영
	 */
	private static String upload(File uploadFile, String folderPath, String uploadName, String ext)
		throws CustomException {

		String fileName = folderPath + "/" + uploadName + "." + ext;
		String uploadFileName = putS3(uploadFile, fileName);
		removeLocalFile(uploadFile);

		return uploadFileName;
	}

	/**
	 * @return 파일명
	 * @author 정재영
	 */
	private static String putS3(File uploadFile, String fileName) {

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(uploadFile.length());

		staticAmazonS3Client.putObject(
			new PutObjectRequest(staticBucket, fileName, uploadFile)
		);

		return fileName; // 저장 시, filename 만 반환하고 싶어서
	}

	private static File convert(MultipartFile file) throws CustomException {

		String fileName = file.getOriginalFilename();
		if (fileName == null || fileName.isEmpty()) {
			throw new CustomException(FILE_NAME_EMPTY);
		}

		File convertFile = new File(file.getOriginalFilename());

		try {
			if (convertFile.exists()) {
				log.debug("파일이 이미 존재합니다: {}", convertFile.getName());
			} else if (!convertFile.createNewFile()) {
				throw new CustomException(FILE_CREATE_FAILED);
			}
		} catch (IOException e) {
			log.error("새로운 파일을 만드는 중 에러: {}", e.getMessage());
			throw new CustomException(CANNOT_CONVERT_MULTIPART_TO_FILE);
		}

		try (FileOutputStream fos = new FileOutputStream(convertFile)) {
			fos.write(file.getBytes());
			return convertFile;
		} catch (IOException e) {
			throw new CustomException(FILE_CREATE_FAILED);
		}
	}

	private static void removeLocalFile(File targetFile) throws CustomException {

		if (!targetFile.delete()) {
			log.error("로컬에서 파일이 삭제되지 못했습니다.");
			throw new CustomException(LOCAL_FILE_DELETION_FAILED);
		}
	}

	/**
	 * byte[]를 파일로 변환하는 메서드
	 */
	private static File convertByteArrayToFile(byte[] data, String fileName, String ext) throws CustomException {

		File file = new File(fileName + "." + ext);
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(data);
			return file;
		} catch (IOException e) {
			throw new CustomException(FILE_CREATE_FAILED);
		}
	}

	@PostConstruct
	public void init() {

		staticAmazonS3Client = amazonS3Client;
		staticUserProfileFolderPath = userProfileFolderPath;
		staticSummaryListFolderPath = summaryListFolderPath;
		staticUploadContentFolderPath = uploadContentFolderPath;
		staticBucket = bucket;
		staticCloudFrontSignedUrlUtil = cloudFrontSignedUrlUtil;
	}

}
