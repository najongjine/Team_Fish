package com.biz.tour.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.tour.domain.marinelife.MarineLifeAPIVO;
import com.biz.tour.service.marinelife.MarineLifeAPIService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/marinelifeapi")
@RequiredArgsConstructor
@Slf4j
public class MarineLifeAPIController {
	private final MarineLifeAPIService marineService;
	
	@RequestMapping(value = "",method=RequestMethod.GET)
	public String marinelife(String mapx,String mapy,String pageno,String dataLimit,Model model) {
		List<MarineLifeAPIVO> marineLifeList=marineService.getDataByCoordinates(mapx,mapy,pageno,dataLimit);
		
		model.addAttribute("marineLifeList", marineLifeList);
		
		//해양생물 정보를 랜더링할 jsp
		return null;
	}
}
