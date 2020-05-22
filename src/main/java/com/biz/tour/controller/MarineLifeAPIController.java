package com.biz.tour.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.tour.domain.marinelife.NaverAPIVO;
import com.biz.tour.service.marinelife.MarineLifeAPIService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/marinelifeapi")
@RequiredArgsConstructor
@Slf4j
public class MarineLifeAPIController {
	
private final MarineLifeAPIService animalService;





//해양 생물 view용 페이지 호출할 method
	//@ResponseBody
	@RequestMapping(value="/marinedetailpage", method=RequestMethod.GET)
	public String getMarineDetail() {
		return "/fishing/marine_detail";
	}







//낚시Base의 latD(latitude degree, 위도의 degree), lonD(longitude degree, 경도의 degree) 값과 매칭되는 해양 생물 데이터 SELECT
	// 위도 경도로 해양생물테이블에서 해양 생물 조회 뒤 결과값인 해양생물 이름을 다시 네이버API테이블에 조회해 최종 데이터 추출
	
	@ResponseBody
	@RequestMapping(value="/getXYmarine", method=RequestMethod.GET)
	public List<NaverAPIVO> getXYMarine(@RequestParam("mapX") int mapX, @RequestParam("mapY") int mapY) {
		

		
		
		
		
		 //위도 경도를 넘겨 해양생물 테이블에서 해양 생물 이름 select
		 List<NaverAPIVO> naverAPIFinalList = animalService.getXYMarine(mapX , mapY);
		 

		 
		 return naverAPIFinalList;
	}



	
	
	



	
	/*
	@RequestMapping(value="/animal",method=RequestMethod.GET)
	public String getAnimals() throws ParseException {
		
		
		// 해양생물 map에 일정 정보를 모으지 못했을 경우 api 재호출 위해 필요
		int pageNo = 1;
		
		// 해양생물 map에 일정 정보를 모으지 못했을 경우 api 재호출 위해 필요
		Map<String ,String> selectedAnimalMap = new HashMap<String,String>();
		animalService.getAnimals(pageNo, selectedAnimalMap);
		
		return null;
	}
	*/






/*
	// 해양 생물 테이블 전체 SELECT
	@RequestMapping(value="/getallmarine", method=RequestMethod.GET)
	public String getAllMarine() {
		
		 animalService.getAllMarine();
		 
		 return null;
	}
*/

/*	
	// 해양생물 API의 데이터를 DB에 저장하는 작업
	@RequestMapping(value="/animaltable",method=RequestMethod.GET)
	public String animalTable(){
		
		
		
		int ret = animalService.insertService();
		
		return null;
	}
*/
	
	}
