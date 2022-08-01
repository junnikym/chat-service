package edu.junnikym.chatservice.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Getter
public class InviteRoomRequestDto {

	private UUID roomId;

	private UUID ownUserId;

	private Collection<String> emails;

	public InviteRoomRequestDto (
			UUID roomId,
			Collection<String> emails
	) {
		this.roomId = roomId;
		this.emails = emails;
	}

	public void setOwnUserId (UUID ownUserId) {
		this.ownUserId = ownUserId;
	}


}
