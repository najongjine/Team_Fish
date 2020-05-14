package com.biz.tour.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.biz.tour.domain.APISearchVO;
import com.biz.tour.domain.fishAreaBased.FishAreaBasedVO;
import com.biz.tour.domain.fishDetailCommon.FishDetailCommonVO;
import com.biz.tour.domain.util.PageDTO;
import com.biz.tour.service.SearchApiDataByTitle;
import com.biz.tour.service.TourService;
import com.biz.tour.service.util.PagiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@SessionAttributes({"apiSearchVO","fishAreaBasedVO","fishDetailVO"})
@RequestMapping(value = "/fish")
public class FishAPIController {
	private final TourService tourService;
	private final PagiService pagiService;
	private final SearchApiDataByTitle searchApiservice;
	
	@ModelAttribute("apiSearchVO")
	public APISearchVO makeApiSearchVO() {
		return new APISearchVO();
	}
	
	@ModelAttribute("fishAreaBasedVO")
	public FishAreaBasedVO makeFishAreaBasedVO() {
		FishAreaBasedVO fishAreaBasedVO=new FishAreaBasedVO();
		return fishAreaBasedVO;
	}
	
	@ModelAttribute("fishDetailVO")
	public FishDetailCommonVO makeFishDetailVO() {
		FishDetailCommonVO fishDetailVO=new FishDetailCommonVO();
		return fishDetailVO;
	}
	
	@RequestMapping(value = "/water",method=RequestMethod.GET)
	public String waterFishing(@RequestParam(value = "pageno",defaultValue = "1") String strPageno,
			APISearchVO apiSearchVO,Model model) {
		int pageno=Integer.valueOf(strPageno);
		List<FishAreaBasedVO> fishList=tourService.getFishingAreaBased("areaBased","water",strPageno);
		if(apiSearchVO.getSearchOption().equalsIgnoreCase("titleSearch")) {
			fishList=searchApiservice.findAPIbyTitle(fishList, apiSearchVO.getInputStr());
		}
		PageDTO pageDTO=pagiService.makePageNation(fishList.size(), pageno, 10);
		model.addAttribute("MODE", "water");
		model.addAttribute("fishList", fishList);
		model.addAttribute("PAGE", pageDTO);
		return "fishing/fishingList";
	}
	
	@RequestMapping(value = "/sea",method=RequestMethod.GET)
	public String SeaFishing(@RequestParam(value = "pageno",defaultValue = "1") String strPageno,
			APISearchVO apiSearchVO,Model model) {
		int pageno=Integer.valueOf(strPageno);
		List<FishAreaBasedVO> fishList=tourService.getFishingAreaBased("areaBased","sea",strPageno);
		
		if(apiSearchVO.getSearchOption().equalsIgnoreCase("titleSearch")) {
			fishList=searchApiservice.findAPIbyTitle(fishList, apiSearchVO.getInputStr());
		}
		PageDTO pageDTO=pagiService.makePageNation(fishList.size(), pageno, 10);
		model.addAttribute("MODE", "sea");
		model.addAttribute("fishList", fishList);
		model.addAttribute("PAGE", pageDTO);
		return "fishing/fishingList";
	}
	
	@RequestMapping(value = "/detail",method=RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public String detail(String contentid, Model model) {
		FishDetailCommonVO fishVO=tourService.getFishingDetail(contentid);
		model.addAttribute("fishVO", fishVO);
		return "fishing/fishDetail";
	}
	
}
