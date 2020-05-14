package com.biz.tour.controller.mail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.tour.service.mail.MailSendServiceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "mail")
@RequiredArgsConstructor
public class MailController {
	private final MailSendServiceImp mailService;
	
	@RequestMapping(value = "",method=RequestMethod.GET)
	public String sendmailToadmin() {
		return "mail/sendmailToadmin";
	}
	@RequestMapping(value = "",method=RequestMethod.POST)
	public String sendmailToadmin(String from_email,String subject,String content) {
		mailService.sendMailToAdmin(from_email, subject, content);
		return "redirect:/";
	}
}
