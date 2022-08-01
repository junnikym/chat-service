package edu.junnikym.chatservice.chat.repository;

import edu.junnikym.chatservice.chat.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomJpaRepository extends JpaRepository<Room, UUID> {

}
