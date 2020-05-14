package com.biz.tour.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.tour.domain.UserSearchVO;
import com.biz.tour.domain.userwater.FishUserWaterCommentVO;
import com.biz.tour.domain.userwater.FishUserWaterPicsVO;
import com.biz.tour.domain.userwater.FishUserWaterVO;
import com.biz.tour.domain.util.PageDTO;
import com.biz.tour.service.fileupload.FileUploadToServerService;
import com.biz.tour.service.userwater.UserWaterCommentService;
import com.biz.tour.service.userwater.UserWaterPicsService;
import com.biz.tour.service.userwater.UserWaterService;
import com.biz.tour.service.util.GetCurrentDateService;
import com.biz.tour.service.util.PagiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@SessionAttributes({"userSearchVO","userVO"})
@RequestMapping(value = "/fishUserWater")
public class FishUserWaterController {
	int itemLimit = 10;
	private final UserWaterService uWaterService;
	private final UserWaterPicsService uWPicsService;
	private final FileUploadToServerService fUploadService;
	private final UserWaterCommentService uWCommentService;
	private final PagiService pagiService;
	private final GetCurrentDateService dateService;

	@ModelAttribute("userSearchVO")
	public UserSearchVO makeSearchVO() {
		UserSearchVO userSearchVO = new UserSearchVO();
		return userSearchVO;
	}
	
	@ModelAttribute("userVO")
	public FishUserWaterVO makeUserVO() {
		FishUserWaterVO userVO=new FishUserWaterVO();
		return userVO;
	}

	@RequestMapping(value = "/findAndShow", method = RequestMethod.GET)
	public String findAndShow(UserSearchVO userSearchVO,
			@RequestParam(value = "pageno", defaultValue = "1") String strPageno, Model model) {
		int pageno = Integer.valueOf(strPageno);
		int maxListSize=-1;
		List<FishUserWaterVO> userList = new ArrayList<FishUserWaterVO>();
		if (userSearchVO.getSearchOption().equalsIgnoreCase("titleSearch")) {
			maxListSize=uWaterService.countFindByTitle(userSearchVO.getInputStr());
			userList = uWaterService.findByTitle(userSearchVO.getInputStr(), (pageno-1)*itemLimit, itemLimit);
		} else {
			maxListSize=uWaterService.countFindAll();
			userList = uWaterService.findAll((pageno-1)*itemLimit,itemLimit);
		}
		if (!userList.isEmpty()) {
			try {
				for (FishUserWaterVO vo : userList) {
					if (vo.getPicsList().size() > 0 && vo.getPicsList() != null) {
						vo.setMainPic(vo.getPicsList().get(0).getUfp_uploadedFName());
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}// 메인 사진 고르기
		PageDTO pageDTO=pagiService.makePageNation(maxListSize, pageno, itemLimit);
		model.addAttribute("userSearchVO", userSearchVO);
		model.addAttribute("userList", userList);
		model.addAttribute("PAGE", pageDTO);
		model.addAttribute("MODE", "water");
		return "fishing/userList";
	}

	@RequestMapping(value = "/waterInsert", method = RequestMethod.GET)
	public String insert(Model model,HttpSession session) {
		String loggedName=(String) session.getAttribute("U_NAME");
		FishUserWaterVO userVO = new FishUserWaterVO();
		userVO.setUf_username(loggedName);
		userVO.setUf_date(dateService.getCurDate());
		model.addAttribute("userVO", userVO);
		model.addAttribute("MODE", "insert");
		return "fishing/userInput";
	}

	@RequestMapping(value = "/waterInsert", method = RequestMethod.POST)
	public String insert(FishUserWaterVO userVO, MultipartHttpServletRequest uploaded_files,
			HttpSession session,Model model) {
		int ret = uWaterService.insert(userVO, session);
		long fk=userVO.getUf_id();

		// 파일 업로드와 파일 이름을 DB에 저장을 같이함.
		fUploadService.filesUp(uploaded_files, "water",fk);
		return "redirect:/fish/water";
	}

	@RequestMapping(value = "/waterUpdate", method = RequestMethod.GET)
	public String update(@ModelAttribute("userVO") FishUserWaterVO userVO,Model model, String strId,HttpSession session) {
		String loggedName=(String) session.getAttribute("U_NAME");
		Long uf_id = (long) -1;
		Long fk = (long) -1;
		try {
			uf_id = Long.valueOf(strId);
			fk = uf_id;
		} catch (Exception e) {
			log.debug("문자열 변환중 오류: " + uf_id);
		}

		userVO = uWaterService.findById(uf_id);
		if(!userVO.getUf_username().equals(loggedName)) {
			return null;
		}
		List<FishUserWaterPicsVO> picsList = uWPicsService.findByFK(fk);
		log.debug("## userVO in watr update: "+userVO);
		model.addAttribute("userVO", userVO);
		model.addAttribute("picsList", picsList);
		model.addAttribute("MODE", "water");
		return "fishing/userInput";
	}

	@RequestMapping(value = "/waterUpdate", method = RequestMethod.POST)
	public String update(FishUserWaterVO userVO, MultipartHttpServletRequest uploaded_files, Model model,
			String strId) {
		Long uf_id = (long) -1;
		try {
			uf_id = Long.valueOf(strId);
		} catch (Exception e) {
			log.debug("문자열 변환중 오류: " + uf_id);
		}

		userVO.setUf_id(uf_id);
		uWaterService.update(userVO);
		fUploadService.filesUp(uploaded_files, "water",userVO.getUf_id());
		model.addAttribute("uf_id", userVO.getUf_id());
		return "redirect:/fishUserWater/view";
	}

	@RequestMapping(value = "/deletePic", method = RequestMethod.GET)
	public String deletePic(String strUfp_id, String strFk, Model model) {
		Long ufp_id = (long) -1;
		Long fk = (long) -1;
		try {
			ufp_id = Long.valueOf(strUfp_id);
			fk = Long.valueOf(strFk);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("문자열 변환중 오류: " + fk);
			log.debug("문자열 변환중 오류: " + ufp_id);
		}
		int ret = uWPicsService.deleteById(ufp_id);
		model.addAttribute("strId", fk);
		return "redirect:/fishUserWater/waterUpdate";
	}

	// FishUserWaterVO 글 삭제 + sub table 들 cascade delete 구현필요

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(@RequestParam("uf_id") String strUf_id,HttpSession session, Model model) {
		String loggedName=(String) session.getAttribute("U_NAME");
		long uf_id = -1;
		try {
			uf_id = Long.valueOf(strUf_id);
		} catch (Exception e) {
			// TODO: handle exception
			log.debug("view 문자열 변환중 오류: " + uf_id);
		}
		FishUserWaterVO userVO = uWaterService.findById(uf_id);
		List<FishUserWaterPicsVO> picsList = uWPicsService.findByFK(uf_id);
		model.addAttribute("userVO", userVO);
		model.addAttribute("picsList", picsList);
		model.addAttribute("loggedName", loggedName);
		model.addAttribute("MODE", "water");
		return "fishing/userView";
	}

	@RequestMapping(value = "/comments", method = RequestMethod.GET)
	public String comments(@RequestParam("ufc_fk") String strFk, @RequestParam(value = "pageno", defaultValue = "1") String strPageno,
			Model model) {
		int pageno=Integer.valueOf(strPageno);
		long ufc_fk = -1;
		int maxListSize=-1;
		try {
			ufc_fk = Long.valueOf(strFk);
		} catch (Exception e) {
			// TODO: handle exception
			log.debug("cmt 문자열 변환중 오류: " + ufc_fk);
		}
		maxListSize=uWCommentService.countFindByFk(ufc_fk);
		List<FishUserWaterCommentVO> commentList = uWCommentService.findByFk(ufc_fk,(pageno-1),itemLimit);
		log.debug("!!! comtlist: "+commentList);
		model.addAttribute("commentList", commentList);
		
		PageDTO pageDTO=pagiService.makePageNation(maxListSize, pageno, itemLimit);
		model.addAttribute("PAGE", pageDTO);
		return "fishing/userComment";
	}

	@RequestMapping(value = "/comments", method = RequestMethod.POST)
	public String comments(FishUserWaterCommentVO commentVO,@RequestParam(value = "pageno", defaultValue = "1") String strPageno,
			HttpSession session,Model model) {
		int pageno=Integer.valueOf(strPageno);
		int maxListSize=-1;
		int ret = uWCommentService.insert(commentVO,session);
		maxListSize=uWCommentService.countFindByFk(commentVO.getUfc_fk());
		List<FishUserWaterCommentVO> commentList = uWCommentService.findByFk(commentVO.getUfc_fk(),(pageno-1),itemLimit);
		model.addAttribute("commentList", commentList);
		
		PageDTO pageDTO=pagiService.makePageNation(maxListSize, pageno, itemLimit);
		model.addAttribute("PAGE", pageDTO);
		return "fishing/userComment";
	}

	@RequestMapping(value = "/replyForm", method = RequestMethod.GET)
	public String replyForm(@RequestParam("ufc_pid") String strPid, @RequestParam("ufc_fk") String strFK, Model model) {
		long ufc_pid = -1;
		long ufc_fk = -1;
		try {
			ufc_pid = Long.valueOf(strPid);
			ufc_fk = Long.valueOf(strFK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		FishUserWaterCommentVO vo = new FishUserWaterCommentVO();
		vo.setUfc_pid(ufc_pid);
		vo.setUfc_fk(ufc_fk);
		model.addAttribute("vo", vo);
		return "fishing/userReplyForm";
	}
	
	@RequestMapping(value = "/delete",method=RequestMethod.GET)
	public String delete(String strId,HttpSession session) {
		boolean allow=false;
		String loggedName=(String) session.getAttribute("U_NAME");
		long uf_id=Long.valueOf(strId);
		FishUserWaterVO userVO = uWaterService.findById(uf_id);
		if(loggedName.equals(userVO.getUf_username())) allow=true;
		if(loggedName.equals("admin")) allow=true;
		if(allow==false) return null;
		// ---------여기까지가 유저 게시물 삭제 보안 검증
		
		int ret=uWaterService.delete(uf_id,(String)session.getAttribute("U_NAME"));
		return "redirect:/";
	}
}
