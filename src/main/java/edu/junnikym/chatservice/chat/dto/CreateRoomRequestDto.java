package edu.junnikym.chatservice.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRequestDto implements Serializable {

	private Set<String> emails;

	public void addEmail(String email) {
		if(this.emails == null)
			return;

		emails.add(email);
	}

}
