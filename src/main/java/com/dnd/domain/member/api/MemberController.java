package com.dnd.domain.member.api;

import com.dnd.domain.common.annotation.LoginUsers;
import com.dnd.domain.member.domain.Member;
import com.dnd.domain.member.domain.MemberResponse;
import com.dnd.global.config.security.CustomUserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.domain.member.application.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/me")
	public MemberResponse me(@LoginUsers CustomUserDetails userDetails) {
		return memberService.findOneMember(userDetails.getMemberId());
	}
}
