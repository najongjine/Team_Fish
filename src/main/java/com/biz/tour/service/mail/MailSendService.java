package com.biz.tour.service.mail;

import java.io.UnsupportedEncodingException;

import com.biz.tour.domain.member.MemberVO;

public interface MailSendService {
	public void sendMail();
	public void sendMail(String to_email,String subject,String content);
	public void sendMailToAdmin(String _from_email,String subject,String content);

	/**
	 *회원가입된 사용자에게 인증 email을 전송
	 *username을 암호화 시켜고 email 인증을 수행할수 있는 링크를 email 본문에 작성하여 전송을 한다. 
	 * @throws UnsupportedEncodingException 
	 */
	public String join_send(MemberVO memberVO)  throws UnsupportedEncodingException;

	/**
	 *@param memberVO 
	 * @since 2020-04-21
	 *이메일 인증을 위한 token정보를 email 전송하기 
	 */
	public void email_auth(MemberVO memberVO, String email_token);
}
