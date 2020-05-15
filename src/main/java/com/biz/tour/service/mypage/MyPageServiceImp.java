package com.biz.tour.service.mypage;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.biz.tour.dao.member.MemberDao;
import com.biz.tour.domain.member.MemberVO;
import com.biz.tour.service.mail.MailSendServiceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageServiceImp implements MyPageService{

	private final BCryptPasswordEncoder passwordEncoder;
	private final MemberDao memDao;
	private final MailSendServiceImp mailService;
	
	@Override
	public int update(MemberVO memberVO) {
		int ret = memDao.update(memberVO);
		return ret;
	}

	@Override
	public MemberVO findByUsernameNemail(String username, String email) {
		
		return null;
	}
	
	@Override
	public int resetPassword(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int changePassword(MemberVO memberVO) {
		
		// DB에 저장
		String encPassword=passwordEncoder.encode(memberVO.getU_password());
		memberVO.setU_password(encPassword);
		
		int ret = memDao.pwupdate(memberVO);
		return ret;
	}

	
	
	
}
