package edu.junnikym.chatservice.chat.repository;

import edu.junnikym.chatservice.chat.config.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomJpaRepository extends JpaRepository<Room, UUID> {

}
