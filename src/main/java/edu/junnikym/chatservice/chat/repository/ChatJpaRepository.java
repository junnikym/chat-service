package edu.junnikym.chatservice.chat.repository;

import edu.junnikym.chatservice.chat.config.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatJpaRepository extends JpaRepository<Chat, UUID> {

}
