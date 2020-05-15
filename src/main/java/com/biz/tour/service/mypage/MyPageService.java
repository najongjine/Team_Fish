package com.biz.tour.service.mypage;

import com.biz.tour.domain.member.MemberVO;

public interface MyPageService {

	public int update(MemberVO memberVO);

	public MemberVO findByUsernameNemail(String username, String email);

	public int changePassword(MemberVO memberVO);

	int resetPassword(MemberVO memberVO);

}
