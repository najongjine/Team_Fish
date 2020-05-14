package com.biz.tour.service.marinelife;

import java.util.List;

import com.biz.tour.domain.marinelife.MarineLifeAPIVO;

public interface MarineLifeAPIService {

	List<MarineLifeAPIVO> getDataByCoordinates(String mapx, String mapy, String pageno, String dataLimit);

}
