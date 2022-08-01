package edu.junnikym.chatservice.chat.service;

import edu.junnikym.chatservice.chat.domain.Room;
import edu.junnikym.chatservice.chat.dto.CreateRoomRequestDto;
import edu.junnikym.chatservice.chat.dto.InviteRoomRequestDto;
import edu.junnikym.chatservice.chat.repository.RoomJpaRepository;
import edu.junnikym.chatservice.member.domain.Member;
import edu.junnikym.chatservice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

	private final RoomJpaRepository roomJpaRepository;

	private final MemberService memberService;

	@Override
	@Transactional
	public void createRoom (CreateRoomRequestDto dto) {
		final Collection<Member> participants = dto.getEmails()
				.stream().map(Member::new).collect(Collectors.toList());

		final Room savedRoom = roomJpaRepository.save(new Room(participants));

		if(savedRoom.getParticipants().size() < 2)
			throw new IllegalArgumentException();
	}

	@Override
	@Transactional
	public void inviteRoom (InviteRoomRequestDto dto) {
		final Room room = roomJpaRepository
				.findById(dto.getRoomId())
				.orElseThrow(()-> new IllegalIdentifierException("not exist room"));

		final Collection<Member> members = memberService.findAll(dto.getEmails());

		room.addParticipant(members);

		roomJpaRepository.flush();
	}

}
