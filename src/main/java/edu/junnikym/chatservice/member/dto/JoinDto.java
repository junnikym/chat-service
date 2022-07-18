package edu.junnikym.chatservice.member.dto;

import edu.junnikym.chatservice.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class JoinDto {

	public String email;

	public String password;

}
