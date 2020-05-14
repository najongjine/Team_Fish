package com.biz.tour.service.mail;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.biz.tour.domain.member.MemberVO;
import com.biz.tour.service.util.PbeEncryptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailSendServiceImp implements MailSendService{
	
	private final JavaMailSender javaMailSender;
	private final String from_email="najongjin5@naver.com";
	
	public MailSendServiceImp(@Qualifier("naverMailHandler") JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	}

	public void sendMail() {
		
		String to_email="najongjin5@naver.com";
		String subject="메일보내기 테스트";
		String content="반갑슴";
		this.sendMail(to_email, subject, content);
	}
	public void sendMail(String to_email,String subject,String content) {
		MimeMessage message=javaMailSender.createMimeMessage();
		MimeMessageHelper mHelper;
		mHelper=new MimeMessageHelper(message,"UTF-8");
		try {
			mHelper.setFrom(from_email);
			mHelper.setTo(to_email);
			mHelper.setSubject(subject);
			mHelper.setText(content,true);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug("!!! email failed");
		}
	}
	public void sendMailToAdmin(String _from_email,String subject,String content) {
		MimeMessage message=javaMailSender.createMimeMessage();
		MimeMessageHelper mHelper;
		mHelper=new MimeMessageHelper(message,"UTF-8");
		try {
			mHelper.setFrom(_from_email);
			mHelper.setTo("najongjin5@naver.com");
			mHelper.setSubject(subject);
			mHelper.setText(content,true);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug("!!! email failed");
		}
	}

	/**
	 *회원가입된 사용자에게 인증 email을 전송
	 *username을 암호화 시켜고 email 인증을 수행할수 있는 링크를 email 본문에 작성하여 전송을 한다. 
	 * @throws UnsupportedEncodingException 
	 */
	public String join_send(MemberVO memberVO) throws UnsupportedEncodingException {
		
		String userName=memberVO.getU_name();
		String email=memberVO.getEmail();
		
		String encUserName=PbeEncryptor.getEncrypt(userName);
		String encEmail=PbeEncryptor.getEncrypt(email);
		/*
		 * jsypt를 사용하여 username&email을 암호화 했더니 /,+ 등 URL을 통해서 보내면 문제를 발생시키는
		 * 특수문자들이 포함이 된다.
		 * 이 특수문자를 URL을 통해서 정상적으로 보낼수 있도록 암호화된 문자열을 URLEncoder.encode() 를 이용해서
		 * encoding을 수행해주어야한다.
		 * encoding 은 수동으로 해줘야 하지만, http 주소창에 encode된 문자열을 치면 http protocol 이 자동으로 decode를 해준다.
		 * 그래서 decrypt 할땐 ++ -> %2b%2b 가 %2b%2b -> ++ 로 다시 변해서 decrpy사 되는 거시다
		 */
		StringBuilder emailLink=new StringBuilder();
		emailLink.append("http://localhost:8080/sec/");
		emailLink.append("join/emailok");
		emailLink.append("?username="+URLEncoder.encode(encUserName,"UTF-8"));
		emailLink.append("&email="+URLEncoder.encode(encEmail,"UTF-8"));
		
		StringBuilder emailMessage=new StringBuilder();
		emailMessage.append("<h3>회원가입을 환영합니다<h3><br/>");
		emailMessage.append("회원가입을 마무리 하려면 email 인증을 하여야 합니다.<br/>");
		emailMessage.append("<a href='%s'> Email 인증 링크</a> 를 클릭하여 주세요<br/>");
		
		String send_message=String.format(emailMessage.toString(), emailLink.toString());
		String to_email=email;
		String subject="회원가입 인증 메일";
		this.sendMail(to_email, subject, send_message);
		return send_message;
	}

	/**
	 *@param memberVO 
	 * @since 2020-04-21
	 *이메일 인증을 위한 token정보를 email 전송하기 
	 */
	public void email_auth(MemberVO memberVO, String email_token) {
		StringBuilder email_content=new StringBuilder();
		email_content.append("<style>");
		email_content.append(".biz-token{");
		email_content.append("border:1px solid blue;");
		email_content.append("background-color:green;");
		email_content.append("color:white;");
		email_content.append("font-weight:bold;");
		email_content.append("}");
		email_content.append("</style>");
		email_content.append("<h2>회원가입을 환영합니다</h2>");
		email_content.append("<p>다음의 인증코드를 회원가입 인증코드란에 입력해주세요</p>");
		email_content.append("<div class='biz-token'>");
		email_content.append(email_token);
		email_content.append("</div>");
		String subject="회원가입 인증 코드";
		this.sendMail(memberVO.getEmail(),subject,email_content.toString());
	}
}
