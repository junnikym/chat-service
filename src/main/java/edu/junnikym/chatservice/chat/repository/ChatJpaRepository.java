package edu.junnikym.chatservice.chat.repository;

import edu.junnikym.chatservice.chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatJpaRepository extends JpaRepository<Chat, UUID> {

}
