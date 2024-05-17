package com.dnd.domain.member.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.domain.member.application.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;

	// @GetMapping
	// public Member memberFindOne(
	// 	@LoginUsers CustomUserDetails userDetails
	// ) {
	// 	return memberService.findOneMember(userDetails.getMemberId());
	// }

}
