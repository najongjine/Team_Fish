package com.biz.tour.service.member;

import java.text.SimpleDateFormat;
import java.util.Date;
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
		return memDao.update(memberVO);
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
	public int raisePoint(@Valid MemberVO memberVO) {
		// 년 월 일 변수 생성
		SimpleDateFormat curDate = new SimpleDateFormat ( "yyyy-MM-dd");
		
		// 현재 날짜 데이터 생성 후 date 변수에 주입
		Date date = new Date();
		log.debug("date : " + date);
		
		// strcurDate 변수에 년월일 방식으로 현재 날짜 데이터를 주입
		String strcurDate = curDate.format(date);
		
		/*
		 *  현재 날짜와 u_date(마지막 로그인 날짜)가 틀리면 포인트 +1
		 *  같다면 오늘 로그인 했었단 뜻이니 포인트 중복 방지하기 위함 
		 */
		if(!strcurDate.equals(memberVO.getU_date())) {
			log.debug("현재 포인트 : " + memberVO.getPoint());//테스트용 로그
			int intPoint = memberVO.getPoint();
			log.debug("intPoint : " + intPoint);//테스트용 로그
			// null이면 회원가입 후 최초 로그인
			if(memberVO.getU_date() == null) {
				intPoint = intPoint + 100;
				memberVO.setPoint(intPoint);
				log.debug("최초 로그인 포인트 + 100  : " + memberVO.getPoint());//테스트용 로그
				
				// VO에 strcurDate 셋팅
				memberVO.setU_date(strcurDate);
				
				// u_date,point 칼럼만 업데이트(VO)
				memDao.date_update(memberVO);
				return 0;
			}
			// 포인트 + 1 해서 VO에 point 칼럼에 셋팅
			intPoint =  intPoint + 1;
			memberVO.setPoint(intPoint);
			log.debug("일일 로그인 포인트 + 1 : " + memberVO.getPoint());// 테스트용 로그
			
			// VO에 strcurDate 셋팅
			memberVO.setU_date(strcurDate);
			
			// u_date,point 칼럼만 업데이트(VO)
			memDao.date_update(memberVO);
			return 0;
		}
		
		// VO에 strcurDate 셋팅
		memberVO.setU_date(strcurDate);
		
		// u_date,point 칼럼만 업데이트(VO)
		memDao.date_update(memberVO);
		
		return 0;
	}

	@Override
	public String findId_getToken(MemberVO memberVO) {
		
		// 인증키 값 생성
		String email_token = UUID.randomUUID().toString().split("-")[0].toUpperCase();
		log.debug("EMAIL-TOKEN : " + email_token);
		
		// 인증키 값 암호화
		String enc_email_token = PbeEncryptor.getEncrypt(email_token);
		
		// 암호화되지 않은 UUID 인증키 값 메일 보내기
		mailService.email_auth(memberVO, email_token);
		
		// 암호화 된 인증키 값 리턴
		return enc_email_token;
	}
	
	// ID찾기,비번 재설정 전에 이메일 인증키 값 검증 메서드
	@Override
	public boolean findId_email_token_ok(String email, String secret_key, String secret_value) {
		
		boolean bKey = PbeEncryptor.getDecrypt(secret_key).equals(secret_value);
		
		log.debug("인증키  key : ",secret_key);
		log.debug("인증키 value : " , secret_value);
		
		if(bKey) {
			log.debug("이메일  : " + email);
			
			MemberVO memberVO = memDao.findByUserEmail(email);
			
			memberVO.setEnabled(true);
			memDao.update(memberVO);
		}
		
		return bKey;
	}
	
	/*
	 *  ID찾기,비번 재설정에서 이메일 인증을 완료하고 id와 비번재설정을 위한 메서드
	 *  email로 db를 검색해서 vo에 담은 다음 비번은 널로 재설정 후 리턴
	 */
	@Override
	public MemberVO findByIdresetpass(MemberVO memberVO) {
		
		MemberVO re_memberVO = memDao.findByUserEmail(memberVO.getEmail());
		
		re_memberVO.setU_password(null);
		
		return re_memberVO;
	}
	@Override
	public int re_member_join(MemberVO memberVO) {
		
		log.debug("암호화 하기 전 비번 : " + memberVO.getU_password());
		String encPassword = passwordEncoder.encode(memberVO.getU_password());
		memberVO.setU_password(encPassword);
		log.debug("암호화 후 비번 : " + memberVO.getU_password());
		memberVO.setEnabled(true);
		int ret = memDao.re_update(memberVO);
		
		return ret;
	}
	@Override
	public MemberVO findById(long id) {
		// TODO Auto-generated method stub
		return memDao.findById(id);
	}
}
