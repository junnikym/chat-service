package edu.junnikym.chatservice.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

@Getter
public class InviteRoomRequestDto {

	private UUID roomId;

	private String inviterEmail;

	private HashSet<String> emails;

	public InviteRoomRequestDto (
			UUID roomId,
			HashSet<String> emails
	) {
		this.roomId = roomId;
		this.emails = emails;
	}

	public void setInviterEmail (String inviterEmail) {
		this.inviterEmail = inviterEmail;
	}


}
