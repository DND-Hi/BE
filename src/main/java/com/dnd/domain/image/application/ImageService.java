package com.dnd.domain.image.application;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.dnd.domain.event.dao.EventRepository;
import com.dnd.domain.event.domain.Event;
import com.dnd.domain.image.dao.ImageRepository;
import com.dnd.domain.image.domain.Image;
import com.dnd.domain.image.domain.ImageFileExtension;
import com.dnd.domain.image.domain.ImageType;
import com.dnd.domain.image.dto.request.EventImageCreateRequest;
import com.dnd.domain.image.dto.request.EventImageUploadCompleteRequest;
import com.dnd.domain.image.dto.response.PresignedUrlResponse;
import com.dnd.domain.member.dao.MemberRepository;
import com.dnd.domain.member.domain.Member;
import com.dnd.global.common.constants.UrlConstants;
import com.dnd.global.error.exception.CustomException;
import com.dnd.global.error.exception.ErrorCode;
import com.dnd.global.util.SpringEnvironmentUtil;
import com.dnd.infra.storage.StorageProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

	private final ImageRepository imageRepository;
	private final MemberRepository memberRepository;
	private final EventRepository eventRepository;
	private final StorageProperties storageProperties;
	private final AmazonS3 amazonS3;
	private final SpringEnvironmentUtil springEnvironmentUtil;

	public PresignedUrlResponse createEventPresignedUrl(final EventImageCreateRequest request, Long memberId) {
		final Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
		Event event = findEventById(request.eventId());

		validateEventUserMismatch(event, member);

		String imageKey = generateUUID();
		String fileName =
			createFileName(
				ImageType.EVENT,
				request.eventId(),
				imageKey,
				request.imageFileExtension()
			);

		GeneratePresignedUrlRequest generatePresignedUrlRequest =
			createGeneratePreSignedUrlRequest(
				storageProperties.bucket(),
				fileName,
				request.imageFileExtension().getUploadExtension()
			);

		String presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
		event.updateImageUploadStatusPending();

		imageRepository.save(
			Image.createImage(
				ImageType.EVENT,
				request.eventId(),
				imageKey,
				request.imageFileExtension()
			)
		);
		return PresignedUrlResponse.from(presignedUrl);
	}

	// 축제 이미지 업로드
	public void uploadCompleteEventImage(final EventImageUploadCompleteRequest request, Long memberId) {
		final Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
		Event event = findEventById(request.eventId());

		validateEventUserMismatch(event, member);

		Image image = findImage(
			ImageType.EVENT,
			request.eventId(),
			request.imageFileExtension()
		);
		String imageUrl =
			createReadImageUrl(
				ImageType.EVENT,
				request.eventId(),
				image.getImageKey(),
				request.imageFileExtension()
			);
		event.updateImageUploadStatusComplete(imageUrl);
	}

	private Event findEventById(final Long eventId) {
		return eventRepository
			.findById(eventId)
			.orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));
	}

	private void validateEventUserMismatch(final Event event, final Member member) {
		if (!event.getMember().getId().equals(member.getId())) {
			throw new CustomException(ErrorCode.EVENT_USER_MISMATCH);
		}
	}

	private GeneratePresignedUrlRequest createGeneratePreSignedUrlRequest(
		String bucket, String fileName, String fileExtension) {
		GeneratePresignedUrlRequest generatePresignedUrlRequest =
			new GeneratePresignedUrlRequest(bucket, fileName, HttpMethod.PUT)
				.withKey(fileName)
				.withContentType("image/" + fileExtension)
				.withExpiration(getPreSignedUrlExpiration());

		generatePresignedUrlRequest.addRequestParameter(
			Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());

		return generatePresignedUrlRequest;
	}

	private Image findImage(
		ImageType imageType, Long targetId, ImageFileExtension imageFileExtension) {
		return imageRepository
			.queryImageKey(imageType, targetId, imageFileExtension)
			.orElseThrow(() -> new CustomException(ErrorCode.IMAGE_KEY_NOT_FOUND));
	}

	private String generateUUID() {
		return UUID.randomUUID().toString();
	}

	private String createFileName(
		ImageType imageType,
		Long targetId,
		String imageKey,
		ImageFileExtension imageFileExtension) {
		return springEnvironmentUtil.getCurrentProfile()
			+ "/"
			+ imageType.getValue()
			+ "/"
			+ targetId
			+ "/"
			+ imageKey
			+ "."
			+ imageFileExtension.getUploadExtension();
	}

	private String createReadImageUrl(
		ImageType imageType,
		Long targetId,
		String imageKey,
		ImageFileExtension imageFileExtension) {
		return UrlConstants.IMAGE_DOMAIN_URL.getValue()
			+ "/"
			+ storageProperties.bucket()
			+ "/"
			+ springEnvironmentUtil.getCurrentProfile()
			+ "/"
			+ imageType.getValue()
			+ "/"
			+ targetId
			+ "/"
			+ imageKey
			+ "."
			+ imageFileExtension.getUploadExtension();
	}

	private Date getPreSignedUrlExpiration() {
		Date expiration = new Date();
		var expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 30;
		expiration.setTime(expTimeMillis);
		return expiration;
	}

}
