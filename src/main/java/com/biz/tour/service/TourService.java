package com.biz.tour.service;

import java.util.List;

import com.biz.tour.domain.fishAreaBased.FishAreaBasedVO;
import com.biz.tour.domain.fishDetailCommon.FishDetailCommonVO;

public interface TourService {
	

	// XML을 rest template으로 받아야 하는건 맞음
	public List<FishAreaBasedVO> getFishingAreaBased(String urlLoc, String type, String strPageno);

	public FishDetailCommonVO getFishingDetail(String contentid);

}
