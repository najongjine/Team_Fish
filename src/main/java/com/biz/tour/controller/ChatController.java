package com.biz.tour.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Controller
public class ChatController {
	
	@RequestMapping(value = "",method=RequestMethod.GET)
	public String chat(HttpSession httpSession,Model model) {
		String u_name=(String) httpSession.getAttribute("U_NAME");
		model.addAttribute("U_NAME", u_name);
		return "chat/chat";
	}
}
