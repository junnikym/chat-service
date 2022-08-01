package edu.junnikym.chatservice.member.repository;

import edu.junnikym.chatservice.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<Member, UUID> {

	Optional<Member> findByEmail (String email);

	Boolean existsByEmail (String email);

	Collection<Member> findAllByEmailIn(Collection<String> email);

}
