package edu.junnikym.chatservice.member.service;

import edu.junnikym.chatservice.member.domain.Member;
import edu.junnikym.chatservice.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final MemberJpaRepository memberJpaRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException {

		Member member = memberJpaRepository.findByEmail(email)
				.orElseThrow(IllegalArgumentException::new);

		return User.builder()
				.username(email)
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
	}

}
