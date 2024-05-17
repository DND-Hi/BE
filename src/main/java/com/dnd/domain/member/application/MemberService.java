package com.dnd.domain.member.application;

import com.dnd.domain.auth.dto.AuthUserInfo;
import com.dnd.domain.auth.dto.Tokens;
import com.dnd.domain.auth.service.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.domain.member.domain.Member;
import com.dnd.domain.member.dao.MemberRepository;
import com.dnd.global.error.exception.CustomException;
import com.dnd.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;
	private final TokenService tokenService;

	@Transactional(readOnly = true)
	public Member findOneMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
	}

	public void login() {

		// onSuccess example
		AuthUserInfo authUserInfo = AuthUserInfo.from(1L);
		Tokens tokens = tokenService.createTokens(authUserInfo);
	}
}
