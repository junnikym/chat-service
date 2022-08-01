package edu.junnikym.chatservice.chat.domain;

import edu.junnikym.chatservice.common.domain.DateBaseEntity;
import edu.junnikym.chatservice.member.domain.Member;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Chat extends DateBaseEntity {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	private String message;

	@OneToOne
	@JoinColumn(name = "memberId", nullable = false)
	private Member member;

	@ManyToOne
	@JoinColumn(name = "roomId", nullable = false)
	private Room room;

}
