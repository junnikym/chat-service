package edu.junnikym.chatservice.member.service;

import edu.junnikym.chatservice.member.domain.Member;
import edu.junnikym.chatservice.member.dto.JoinDto;
import edu.junnikym.chatservice.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberJpaRepository memberJpaRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void join (JoinDto dto) {
		final Boolean isExist = memberJpaRepository.existsByEmail(dto.getEmail());
		if(!isExist)
			insertNewMember(dto);
	}

	private void insertNewMember(JoinDto dto) {
		final Member newMember = newMember(dto.getEmail(), dto.getPassword());
		memberJpaRepository.save(newMember);
	}

	private Member newMember(String email, String password) {
		final String encryptedPassword = passwordEncoder.encode(password);
		return new Member(email, encryptedPassword);
	}

	@Override
	public Collection<Member> findAll (Collection<String> emails) {
		return memberJpaRepository.findAllByEmailIn(emails);
	}

}
