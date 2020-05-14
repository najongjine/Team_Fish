package com.biz.tour.service.member;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.tour.dao.member.MemberDao;
import com.biz.tour.domain.member.MemberVO;
import com.biz.tour.service.mail.MailSendServiceImp;
import com.biz.tour.service.util.PbeEncryptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImp implements MemberService{
	private final BCryptPasswordEncoder passwordEncoder;
	private final MemberDao memDao;
	private final MailSendServiceImp mailService;
	
	public MemberVO findByUName(String u_name) {
		return memDao.findByUName(u_name);
	}
	public int insert(MemberVO MemberVO) {
		if(!MemberVO.getU_password().equals(MemberVO.getU_repassword())){
			return -1;
		}
		String encPass=passwordEncoder.encode(MemberVO.getU_password());
		MemberVO.setU_password(encPass);
		return memDao.insert(MemberVO);
	}
	public int delete(String u_name) {
		return memDao.delete(u_name);
	}
	public MemberVO checkLogin(MemberVO inputtedMemberVO) {
		// TODO Auto-generated method stub
		MemberVO existingMemberVO=memDao.findByUName(inputtedMemberVO.getU_name());
		if(existingMemberVO==null) return null;
		if(!passwordEncoder.matches(inputtedMemberVO.getU_password(), existingMemberVO.getU_password())) {
			return null;
		}
		
		return existingMemberVO;
	}
	
	@Transactional
	public boolean email_token_ok(String username, String secret_key, String secret_value) {
		boolean bKey=PbeEncryptor.getDecrypt(secret_key).equals(secret_value);
		if(bKey) {
			String strUsername=PbeEncryptor.getDecrypt(username);
			MemberVO memberVO=memDao.findByUName(strUsername);
			memberVO.setEnabled(true);
			memDao.update(memberVO);
		}
		return bKey;
	}
	
	/**
	 *@since 2020-04-21
	 *회원정보를 받아서 DB에 저장하고
	 *회원정보를 활성화 할수 있도록 하기위해 인증정보를 생성한후 controller로 return
	 */
	@Transactional
	public String insert_getToken(MemberVO memberVO) {
		// DB에 저장
		memberVO.setEnabled(false);
		String encPassword=passwordEncoder.encode(memberVO.getU_password());
		memberVO.setU_password(encPassword);
		memDao.insert(memberVO);
		
		String email_token=UUID.randomUUID().toString().split("-")[0].toUpperCase();
		String enc_email_token=PbeEncryptor.getEncrypt(email_token);
		//email보내기
		mailService.email_auth(memberVO,email_token);
		return enc_email_token;
	}
	@Override
	public int update(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public MemberVO findByUsernameNemail(String username, String email) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int resetPassword(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int changePassword(String inputtedPrevPass, String inputtedNewPass) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void raisePoint(@Valid MemberVO memberVO) {
		// TODO Auto-generated method stub
		
	}
}
