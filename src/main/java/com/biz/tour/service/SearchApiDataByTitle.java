package com.biz.tour.service;

import java.util.List;

import com.biz.tour.domain.fishAreaBased.FishAreaBasedVO;

public interface SearchApiDataByTitle {
	public List<FishAreaBasedVO> findAPIbyTitle(List<FishAreaBasedVO> dataList,String strInput);
}
