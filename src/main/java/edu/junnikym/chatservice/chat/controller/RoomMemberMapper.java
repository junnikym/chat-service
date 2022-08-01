package edu.junnikym.chatservice.chat.controller;

import edu.junnikym.chatservice.chat.domain.Room;
import edu.junnikym.chatservice.member.domain.Member;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class RoomMemberMapper {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@OneToOne
	@JoinColumn(name = "roomId", nullable = false)
	private Room room;

	@OneToOne
	@JoinColumn(name = "memberId", nullable = false)
	private Member Member;

}
