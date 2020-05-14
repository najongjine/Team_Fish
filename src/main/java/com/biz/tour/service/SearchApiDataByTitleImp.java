package com.biz.tour.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.tour.domain.fishAreaBased.FishAreaBasedVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchApiDataByTitleImp implements SearchApiDataByTitle{
	public List<FishAreaBasedVO> findAPIbyTitle(List<FishAreaBasedVO> dataList,String strInput) {
		strInput=strInput.trim();
		List<FishAreaBasedVO> newList=new ArrayList<FishAreaBasedVO>();
		for(FishAreaBasedVO vo:dataList) {
			if(vo.getTitle().toLowerCase().contains(strInput.toLowerCase())) {
				newList.add(vo);
			}
		}
		return newList;
	}
	
}
