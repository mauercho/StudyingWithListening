package com.ssafy.a304.shortgong.global.util;

import static com.ssafy.a304.shortgong.global.errorCode.FileErrorCode.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
	private final AmazonS3 amazonS3Client;
	private final String userProfileFolderPath;
	private final String summaryListFolderPath;
	private final String uploadContentFolderPath;
	private final String bucket;

	@Autowired
	public FileUtil(AmazonS3 amazonS3Client,
		@Value("${file.path.user-profile-folder}") String userProfileFolderPath,
		@Value("${file.path.summary-folder}") String summaryListFolderPath,
		@Value("${file.path.upload-content-folder}") String uploadContentFolderPath,
		@Value("${cloud.aws.s3.bucket}") String bucket) {

		this.amazonS3Client = amazonS3Client;
		this.userProfileFolderPath = userProfileFolderPath;
		this.summaryListFolderPath = summaryListFolderPath;
		this.uploadContentFolderPath = uploadContentFolderPath;
		this.bucket = bucket;
	}

	/*
	 * 유저 이미지 업로드하기
	 * */
	public static String uploadUserProfileImgFileByUuid(MultipartFile file, String uuid) throws
		CustomException {

		FileValidator.checkProfileImageExt(getExtension(file));
		return upload(convert(file), staticUserProfileFolderPath, uuid, getExtension(file));
	}

	public static String uploadContentFileByUuid(MultipartFile file, String uuid) {

		FileValidator.checkOcrImageExt(getExtension(file));
		return upload(convert(file), staticUploadContentFolderPath, uuid, getExtension(file));
	}

	/*
	 * 해당 요약집 폴더에 문자 보이스 mp3 파일 업로드하기
	 * */
	public static String uploadSentenceVoiceFileByUuid(MultipartFile file, String summaryUuid,
		String mp3FileUuid) throws
		CustomException {

		FileValidator.checkMp3Ext(getExtension(file));
		return upload(convert(file), staticSummaryListFolderPath + "/" + summaryUuid, mp3FileUuid, getExtension(file));
	}

	public static String getUserProfileImgUrl(String fileName) throws CustomException {

		return staticAmazonS3Client.getUrl(staticBucket, staticUserProfileFolderPath + "/" + fileName).toString();
	}

	public static String getSentenceVoiceFileUrl(String summaryUuid, String fileName) throws CustomException {

		return staticAmazonS3Client.getUrl(staticBucket,
			staticSummaryListFolderPath + "/" + summaryUuid + "/" + fileName).toString();
	}

	public static String getExtension(MultipartFile file) throws CustomException {

		String originalFilename = file.getOriginalFilename();
		FileValidator.checkFileNonEmpty(originalFilename);
		int index = originalFilename.lastIndexOf(".");
		if (index == -1) {
			throw new CustomException(EXTENSION_FIND_FAILED);
		}
		return originalFilename.substring(index + 1);
	}

	public static String getExtensionString(String s3Url) throws CustomException {

		FileValidator.checkFileNonEmpty(s3Url);
		int index = s3Url.lastIndexOf(".");
		if (index == -1) {
			throw new CustomException(EXTENSION_FIND_FAILED);
		}
		return s3Url.substring(index + 1);
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

	private static void deleteFileFromS3(String folderPath, String fileName) throws CustomException {

		try {
			staticAmazonS3Client.deleteObject(staticBucket, folderPath + "/" + fileName);
		} catch (Exception e) {
			throw new CustomException(S3_FILE_DELETION_FAILED); // 삭제 실패에 대한 예외 처리
		}
	}

	private static String upload(File uploadFile, String folderPath, String uploadName, String ext)
		throws CustomException {

		String fileName = folderPath + "/" + uploadName + "." + ext;
		String uploadImageUrl = putS3(uploadFile, fileName);
		removeLocalFile(uploadFile);

		return uploadImageUrl;
	}

	private static String putS3(File uploadFile, String fileName) {

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(uploadFile.length());

		staticAmazonS3Client.putObject(
			new PutObjectRequest(staticBucket, fileName, uploadFile)
			// .withCannedAcl(CannedAccessControlList.PublicRead)
		);
		log.debug("S3에 파일 업로드 중: {}", fileName);
		return fileName; // 저장 시, filename 만 반환하고 싶어서
		// amazonS3Client.getUrl(bucket, fileName).toString()
	}

	private static File convert(MultipartFile file) throws CustomException {

		String fileName = file.getOriginalFilename();
		if (fileName == null || fileName.isEmpty()) {
			throw new CustomException(FILE_NAME_EMPTY);
		}

		File convertFile = new File(file.getOriginalFilename());
		log.debug("convertFile: {}", convertFile);

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
		log.debug("로컬에서 파일이 삭제되었습니다.");

	}

	@PostConstruct
	public void init() {

		staticAmazonS3Client = amazonS3Client;
		staticUserProfileFolderPath = userProfileFolderPath;
		staticSummaryListFolderPath = summaryListFolderPath;
		staticUploadContentFolderPath = uploadContentFolderPath;
		staticBucket = bucket;
	}

}
