package com.dnd.domain.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.domain.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
	Optional<Member> findByNicknameAndOauthInfo_OauthId(String nickname, String oauthId);
}
