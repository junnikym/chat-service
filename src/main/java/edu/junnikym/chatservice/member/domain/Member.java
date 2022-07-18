package edu.junnikym.chatservice.member.domain;

import edu.junnikym.chatservice.common.domain.DateBaseEntity;
import edu.junnikym.chatservice.member.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class Member extends DateBaseEntity {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	private String email;

	private String password;

	@Column(nullable = false)
	private Role role;

	public Member(String email, String password) {
		this.email = email;
		this.password = password;
		this.role = Role.USER;
	}

	public Member () { }
}
