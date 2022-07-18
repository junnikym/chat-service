package edu.junnikym.chatservice.member.controller;

import edu.junnikym.chatservice.member.dto.JoinDto;
import edu.junnikym.chatservice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

	final private MemberService memberService;

	@GetMapping("/join")
	public String joinPage() {
		return "join/index.html";
	}

	@PostMapping("/join")
	public String join(JoinDto dto) {
		memberService.join(dto);
		return "redirect:/";
	}

}
