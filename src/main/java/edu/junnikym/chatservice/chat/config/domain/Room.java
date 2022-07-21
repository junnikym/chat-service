package edu.junnikym.chatservice.chat.config.domain;

import edu.junnikym.chatservice.common.domain.DateBaseEntity;
import edu.junnikym.chatservice.member.domain.Member;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
public class Room extends DateBaseEntity {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@OneToMany(mappedBy = "id")
	private Collection<Member> members;

	@OneToMany(mappedBy = "id")
	private Collection<Chat> chat;

}
