package com.biz.tour.controller.mypage;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.tour.domain.member.MemberVO;
import com.biz.tour.service.fileupload.FileUploadToServerService;
import com.biz.tour.service.member.MemberService;
import com.biz.tour.service.mypage.MyPageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@SessionAttributes("memberVO")
@RequestMapping(value = "/mypage")
public class MyPageController {
	private final MyPageService mypageService;
	private final MemberService memberService;
	private final FileUploadToServerService fUploadService;
	
	@ModelAttribute("memberVO")
	public MemberVO makeMemberVO() {
		MemberVO memberVO=new MemberVO();
		return memberVO;
	}
	
	@RequestMapping(value = "/view",method=RequestMethod.GET)
	public String view(HttpSession session,Model model) {
		//로그인 안되있으면 intercept 발동되게 해야함
		String loggedName=(String) session.getAttribute("U_NAME");
		if(loggedName==null || loggedName.isEmpty()) return null;
		
		MemberVO memberVO=memberService.findByUName(loggedName);
		memberVO.setU_password("dummy");
		memberVO.setU_repassword("dummy");
		model.addAttribute("memberVO", memberVO);
		//마이페이지 보여줄 jsp
		return null;
	}
	
	@RequestMapping(value = "/update",method=RequestMethod.GET)
	public String update(HttpSession session,Model model) {
		//로그인 안되있으면 intercept 발동되게 해야함
		String loggedName=(String) session.getAttribute("U_NAME");
		if(loggedName==null || loggedName.isEmpty()) return null;
		
		MemberVO memberVO=memberService.findByUName(loggedName);
		memberVO.setU_password("dummy");
		memberVO.setU_repassword("dummy");
		model.addAttribute("memberVO", memberVO);
		//마이페이지 수정 form 보여줄 jsp
		return null;
	}
	
	@RequestMapping(value = "/update",method=RequestMethod.POST)
	public String update(MemberVO memberVO,MultipartHttpServletRequest uploaded_files) {
		//update을 하고나서, update한 VO의 ID값을 받아와야함
		int ret=memberService.update(memberVO);
		long fk=memberVO.getU_id();
		
		//update한 VO의 ID값을 파일업로드+DB 업데이트까지 동시에 수행해주는 method에 Id값 전달
		fUploadService.filesUp(uploaded_files, "tbl_members",fk);
		
		return "redirect:/mypage/view";
	}
	
	@RequestMapping(value = "/resetpassword",method=RequestMethod.GET)
	public String resetPassword() {
		
		//이메일, 유저네임 입력받는 form jsp
		return null;
	}
	
	@RequestMapping(value = "/resetpassword",method=RequestMethod.POST)
	public String resetPassword(String username,String email) {
		MemberVO memberVO=memberService.findByUsernameNemail(username,email);
		
		//비밀번호 1111 로 만듬과 동시에 유저한테 메일도 같이 보내줘야함
		int ret=memberService.resetPassword(memberVO);
		
		//이메일, 유저네임 입력받는 form jsp
		return "redirect:/";
	}
	
	@RequestMapping(value = "/changepassword",method=RequestMethod.GET)
	public String changePassword() {
		// 비밀번호만 변경 입력받는 form jsp
		return null;
	}
	
	@RequestMapping(value = "/changepassword",method=RequestMethod.POST)
	public String changePassword(String inputtedPrevPass,String inputtedNewPass) {
		int ret=memberService.changePassword(inputtedPrevPass,inputtedNewPass);
		// 비밀번호만 변경 입력받는 form jsp
		return "redirect:/";
	}
}
