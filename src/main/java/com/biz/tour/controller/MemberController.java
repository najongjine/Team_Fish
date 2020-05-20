package com.biz.tour.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.biz.tour.domain.member.MemberVO;
import com.biz.tour.service.member.MemberService;
import com.biz.tour.service.util.PbeEncryptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@SessionAttributes("memberVO")
@RequestMapping(value = "/member")
public class MemberController {
	private final MemberService memService;
	
	@ModelAttribute("memberVO")
	public MemberVO makeMemVO() {
		return new MemberVO();
	}
	@RequestMapping(value = "/register",method=RequestMethod.GET)
	public String register(@ModelAttribute("memberVO") MemberVO memberVO,Model model) {
		memberVO=makeMemVO();
		model.addAttribute("memberVO", memberVO);
		return "member/register";
	}
	/**
	 *@since 2020-04-21
	 *최초회원 가입 화면에서 username과 password를 입력한 후 회원가입 버튼을 클릭하면
	 *memberVO에 데이터를 받아서 sessionAttributes에 설정된 저장소에 저장해두고 이메일 인증 화면 보여주기  
	 */
	@RequestMapping(value = "/register_next",method=RequestMethod.POST)
	public String join_next(@ModelAttribute("memberVO") MemberVO memberVO,BindingResult result) {
		if(result.hasErrors()) {
			return "member/register";
		}
		return "member/register_email";
	}
	
	/**
	 *@since 2020-04-21
	 *Email 인증폼에서 Email 보내기 버튼을 클릭했을때 
	 *memberVO에 데이터를 받아서(Email) sessionattribute에 저장된 데이터와 통합(merge)하고
	 *DB에 저장한 후 인증정보를 Email로 보내고 인증코드를 입력받는 화면을 다시 보여주기
	 *이땐 JOIn 변수에 EMAIL_OK 문자열을 실어서 보내고
	 *화면에는 인증코드 입력하는 란이 보이도록 설정 
	 */
	@RequestMapping(value = "/register_last",method=RequestMethod.POST)
	public String join_last(@ModelAttribute("memberVO") MemberVO memberVO,Model model) {
		//jsp로 내려보낸 인증코드(token)은 암호화 시킨거고, 메일로 보낸건 암호화 안시킨 인증코드(token)임.
		//인증코드(token)만들면서 이메일도 같이 보냄.
		String email_token=memService.insert_getToken(memberVO);//요건 암호화 된 인증코드(token)
		model.addAttribute("My_Email_Secret", email_token);
		model.addAttribute("JOIN", "EMAIL_OK");
		model.addAttribute("username", PbeEncryptor.getEncrypt(memberVO.getU_name()));
		return "member/register_email";
	}
	
	/**
	 *@since 2020-04-21
	 *이메일 인증폼에서 인증키와 인증값을 받아서 인증처리 
	 */
	@ResponseBody
	@RequestMapping(value = "/email_token_check",method=RequestMethod.POST)
	public String email_token_check(@RequestParam("secret_id") String username,
			@RequestParam("secret_key") String secret_key,@RequestParam("secret_value") String secret_value) {
		log.debug("## entered email token check: "+username+","+secret_key+","+secret_value);
		boolean bKey=memService.email_token_ok(username,secret_key,secret_value);
		//jsp로 내려보낸 인증코드(token)은 암호화 시킨거고, 메일로 보낸건 암호화 안시킨 인증코드(token)임.
		
		if(bKey) return "OK";
		else return "FAIL";
	}
	

	@RequestMapping(value = "/register",method=RequestMethod.POST)
	public String register(@Valid @ModelAttribute("memberVO") MemberVO memberVO,BindingResult result) {
		if(result.hasErrors()) {
			return "member/register";
		}
		if(!memberVO.getU_password().equals(memberVO.getU_repassword())) {
			return null;
		}
		int ret=memService.insert(memberVO);
		return "redirect:/";
	}
	
=======

	@RequestMapping(value = "/login",method=RequestMethod.GET)
	public String login(@ModelAttribute("memberVO") MemberVO memberVO,Model model) {
		model.addAttribute("memberVO", memberVO);
		return "member/login";
	}
	@RequestMapping(value = "/login",method=RequestMethod.POST)
	public String login(@Valid @ModelAttribute("memberVO") MemberVO memberVO,BindingResult result,HttpSession session,Model model,SessionStatus sstatus) {
		if(result.hasErrors()) {
			return "member/login";
		}
		memberVO=memService.checkLogin(memberVO);
		
		//로그인시 포인트 적립. 서브테이블에 username 있으면 포인트 적립 안함
		memService.raisePoint(memberVO);
		
		if(memberVO==null) return "member/loginFail";
		session.setAttribute("U_NAME", memberVO.getU_name());
		sstatus.setComplete();// 로그인 완료 후 session에 남아있는 id,비번 정보 초기화
		return "redirect:/";
	}
	@RequestMapping(value = "/logout",method=RequestMethod.GET)
	public String logout(HttpSession session,SessionStatus sstatus) {
		session.removeAttribute("U_NAME");
		sstatus.setComplete();// 로그아웃 후 session에 남아있는 id,비번 정보 초기화
		return "redirect:/";
	}
	@RequestMapping(value = "/delete",method=RequestMethod.GET)
	public String delete(String u_name,HttpSession session) {
		int ret=memService.delete(u_name);
		return "redirect:/";
	}
	
	// email로 ID찾기,비번 재설정을 하기위한 email 입력 페이지
	@RequestMapping(value="/findID",method=RequestMethod.GET)
	public String findID(@ModelAttribute("memberVO") MemberVO memberVO) {
		//memService.findByIdresetpass(email);
		return "member/repass_email";
	}
	// email로 ID찾기,비번 재설정을 하기위한 email 입력 페이지
	@RequestMapping(value="/findID",method=RequestMethod.POST)
	public String findID(@ModelAttribute("memberVO") MemberVO memberVO,Model model) {
		String email_token = memService.findId_getToken(memberVO);
		
		model.addAttribute("My_Email_Secret",email_token);
		model.addAttribute("JOIN","EMAIL_OK");
		
		return "member/repass_email";
	}
	
	// ID찾기,비번 초기화 이메일 인증키 체크메서드
	@ResponseBody
	@RequestMapping(value = "/findId_email_token_check",method=RequestMethod.POST)
	public String findId_email_token_check(
			@RequestParam("email") String email,
			@RequestParam("secret_key") String secret_key,
			@RequestParam("secret_value") String secret_value) {
//		log.debug("## entered email token check: "+username+","+secret_key+","+secret_value);
		boolean bKey=memService.findId_email_token_ok(email,secret_key,secret_value);
		
		if(bKey) return "OK";
		else return "FAIL";
	}
	

	/*
	 *  ID찾기,비번 재설정 이메일 인증키 체크 완료 후 비번 재설정 메서드
	 *  db에서 이메일로 검색 후 비번만 초기화 하고 vo에 담아서 보내온 vo를 
	 *  re_join.jsp로 보내기 
	 */
//	@ResponseBody
	@RequestMapping(value="/re_join",method=RequestMethod.GET)
	public String re_join(@ModelAttribute("memberVO")MemberVO memberVO, Model model) {
		
		MemberVO re_join = memService.findByIdresetpass(memberVO);
		
		model.addAttribute("memberVO",re_join);
		
		return "mypage/re_join";
//		return re_join;
	}
	/*
	 * ID찾기,비번 재설정 이메일 인증키 체크 완료 후 비번 재설정 메서드
	 * re_join.jsp에서 비번 변경값 받아서 비번 변경 
	 */
	@RequestMapping(value="/re_join",method=RequestMethod.POST)
	public String re_join(@ModelAttribute("memberVO")MemberVO memberVO, Model model,String email,SessionStatus session) {
		
		int ret = memService.re_member_join(memberVO);
		
		session.setComplete();// 로그인 완료 후 session에 남아있는 id,비번 정보 초기화
		
		return "redirect:/";
		
	}
		
	
	
	
}
