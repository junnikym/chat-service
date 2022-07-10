package edu.junnikym.chatservice.chat.controller;

import edu.junnikym.chatservice.chat.dto.HiDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

	@MessageMapping("/hi")
	@SendTo("/topic/hi")
	public String hi(HiDto message) throws Exception {
		return "hi! " + message.getName();
	}

}
