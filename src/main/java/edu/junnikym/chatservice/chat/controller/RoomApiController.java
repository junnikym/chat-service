package edu.junnikym.chatservice.chat.controller;

import edu.junnikym.chatservice.chat.dto.CreateRoomRequestDto;
import edu.junnikym.chatservice.chat.dto.InviteRoomRequestDto;
import edu.junnikym.chatservice.chat.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomApiController {

	private final RoomService roomService;

	@PostMapping()
	public ResponseEntity<?> create (
			Principal principal,
			@RequestBody CreateRoomRequestDto dto
	) {
		dto.addEmail(principal.getName());
		if(dto.getEmails().size() < 2)
			return ResponseEntity.badRequest().build();

		roomService.createRoom(dto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/invite")
	public ResponseEntity<?> invite (
			Principal principal,
			@RequestBody InviteRoomRequestDto dto
	) {
		final String inverterEmail = principal.getName();
		if(dto.getEmails().contains(inverterEmail))
			throw new IllegalIdentifierException("can not invite yourself");

		dto.setInviterEmail(inverterEmail);
		if(dto.getEmails().size() < 1)
			return ResponseEntity.badRequest().build();

		roomService.inviteRoom(dto);
		return ResponseEntity.ok().build();
	}

}
