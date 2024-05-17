package com.dnd.domain.image.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.domain.common.annotation.LoginUsers;
import com.dnd.domain.image.application.ImageService;
import com.dnd.domain.image.dto.request.EventImageCreateRequest;
import com.dnd.domain.image.dto.request.EventImageUploadCompleteRequest;
import com.dnd.domain.image.dto.request.MemberProfileImageCreateRequest;
import com.dnd.domain.image.dto.request.MemberProfileImageUploadCompleteRequest;
import com.dnd.domain.image.dto.response.PresignedUrlResponse;
import com.dnd.global.config.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;
	@Operation(
		summary = "축제 이미지 Presigned URL 생성",
		description = "축제 이미지 Presigned URL를 생성합니다.")
	@PostMapping("/event/upload-url")
	public PresignedUrlResponse eventPresignedUrlCreate(
		@Valid @RequestBody EventImageCreateRequest request,
		@Parameter(hidden = true)
		@LoginUsers CustomUserDetails userDetails) {
		return imageService.createEventPresignedUrl(request, userDetails.getMemberId());
	}

	@Operation(summary = "축제 이미지 업로드 완료처리", description = "축제 이미지 업로드 완료 시 호출하시면 됩니다.")
	@PostMapping("/event/upload-complete")
	public void eventImageUploaded(
		@Valid @RequestBody EventImageUploadCompleteRequest request,
		@Parameter(hidden = true)
		@LoginUsers CustomUserDetails userDetails) {
		imageService.uploadCompleteEventImage(request, userDetails.getMemberId());
	}

	@Operation(
		summary = "회원 프로필 이미지 Presigned URL 생성",
		description = "회원 프로필 이미지 Presigned URL을 생성합니다.")
	@PostMapping("/members/me/upload-url")
	public PresignedUrlResponse memberProfilePresignedUrlCreate(
		@Valid @RequestBody MemberProfileImageCreateRequest request,
		@Parameter(hidden = true)
		@LoginUsers CustomUserDetails userDetails) {
		// return imageService.createMemberProfilePresignedUrl(request, userDetails);
		return null;
	}
	@Operation(
		summary = "회원 프로필 이미지 업로드 완료처리")
	@PostMapping("/members/me/upload-complete")
	public ResponseEntity<Void> memberProfileUploadedV2(
		@Valid @RequestBody MemberProfileImageUploadCompleteRequest request) {
		// imageService.uploadCompleteMemberProfileV2(request);
		return ResponseEntity.ok().build();
	}


}
