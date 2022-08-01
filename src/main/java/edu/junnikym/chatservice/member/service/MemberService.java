package edu.junnikym.chatservice.member.service;

import edu.junnikym.chatservice.member.domain.Member;
import edu.junnikym.chatservice.member.dto.JoinDto;

import java.util.Collection;

public interface MemberService {

	void join(JoinDto dto);

	Collection<Member> findAll(Collection<String> emails);

}
