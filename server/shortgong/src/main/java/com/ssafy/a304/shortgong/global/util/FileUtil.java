package com.ssafy.a304.shortgong.global.util;

import static com.ssafy.a304.shortgong.global.errorCode.FileErrorCode.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.a304.shortgong.global.error.CustomException;
import com.ssafy.a304.shortgong.global.validator.FileValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

	private static final AmazonS3 amazonS3Client = new AmazonS3Client();

	private static FileUtil instance;

	@Value("${file.path.user-profile-folder}")
	private static String userProfileFolderPath;

	@Value("${file.path.summary-folder}")
	private static String summaryListFolderPath;

	@Value("${cloud.aws.s3.bucket}")
	private static String bucket;

	/*
	 * 유저 이미지 업로드하기
	 * */
	public static String uploadUserProfileImgFileByUuid(MultipartFile file, String uuid) throws CustomException {

		FileValidator.checkImageExt(getExtension(file));
		return upload(convert(file), userProfileFolderPath, uuid, getExtension(file));
	}

	/*
	 * 해당 요약집 폴더에 문자 보이스 mp3 파일 업로드하기
	 * */
	public static String uploadSentenceVoiceFileBySummaryUuid(MultipartFile file, String summaryUuid,
		String mp3FileUuid) throws
		CustomException {

		FileValidator.checkMp3Ext(getExtension(file));
		return upload(convert(file), summaryListFolderPath + "/" + summaryUuid, mp3FileUuid, getExtension(file));
	}

	public static String getUserProfileImgUrl(String fileName) throws CustomException {

		return amazonS3Client.getUrl(bucket, userProfileFolderPath + "/" + fileName).toString();
	}

	public static String getSentenceVoiceFileUrl(String summaryUuid, String fileName) throws CustomException {

		return amazonS3Client.getUrl(bucket, summaryListFolderPath + "/" + summaryUuid + "/" + fileName).toString();
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

	public static boolean isExistUserProfileImgFile(String fileName) throws CustomException {

		return true;
	}

	public static boolean isExistSentenceVoiceFile(MultipartFile file, String uuid) throws CustomException {

		return true;
	}

	public static void deleteUserProfileImgFile(String fileName) {

		deleteFileFromS3(userProfileFolderPath, fileName);
	}

	public static void deleteSentenceVoiceFile(String summaryUuid, String fileName) {

		deleteFileFromS3(summaryListFolderPath + "/" + summaryUuid, fileName);
	}

	private static void deleteFileFromS3(String folderPath, String fileName) throws CustomException {

		try {
			amazonS3Client.deleteObject(bucket, folderPath + "/" + fileName);
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

		amazonS3Client.putObject(
			new PutObjectRequest(bucket, fileName, uploadFile)
			// .withCannedAcl(CannedAccessControlList.PublicRead)
		);
		log.debug("S3에 파일 업로드 중: {}", fileName);
		return amazonS3Client.getUrl(bucket, fileName).toString();
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
}
