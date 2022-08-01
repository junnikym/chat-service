package edu.junnikym.chatservice.chat.service;

import edu.junnikym.chatservice.chat.dto.CreateRoomRequestDto;
import edu.junnikym.chatservice.chat.dto.InviteRoomRequestDto;

public interface RoomService {

	void createRoom(CreateRoomRequestDto dto);

	void inviteRoom(InviteRoomRequestDto dto);

}
