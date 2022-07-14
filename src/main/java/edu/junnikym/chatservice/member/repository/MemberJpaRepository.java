package edu.junnikym.chatservice.member.repository;

import edu.junnikym.chatservice.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<Member, UUID> {

}
