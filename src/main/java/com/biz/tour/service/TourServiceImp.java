package com.biz.tour.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.biz.tour.config.TourAPIConfig;
import com.biz.tour.domain.fishAreaBased.FishAB_RestResponse;
import com.biz.tour.domain.fishAreaBased.FishAreaBasedVO;
import com.biz.tour.domain.fishDetailCommon.FishDC_RestResponse;
import com.biz.tour.domain.fishDetailCommon.FishDetailCommonVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TourServiceImp implements TourService{
	private final String fishTourAreabasedURL = "http://api.visitkorea.or.kr/openapi/service/rest/EngService/areaBasedList";
	private final String fishDetailCommonURL = "http://api.visitkorea.or.kr/openapi/service/rest/EngService/detailCommon";

	// 지역기반 조회 query 용
	// type은 sea | water
	private String getAreaBasedQuery(String type,String strPageno) {
		String queryString = "";
		queryString = fishTourAreabasedURL;
		queryString += "?serviceKey="+TourAPIConfig.TOURAPI_KEY;
		queryString += "&MobileOS=ETC";
		queryString += "&MobileApp=Fisher";
		queryString += "&pageNo="+strPageno;
		queryString += "&numOfRows=10";

		// 여기서부턴 국문과 영문 분류코드들이 다 다름
		queryString += "&contentTypeId=75";// 영문 서비스 분류 타입
		// queryString += "&cat1=A03";
		// queryString += "&cat2=A0303";
		if (type.equalsIgnoreCase("water")) {
			queryString += "&cat3=A03030500";
		} else if (type.equalsIgnoreCase("sea")) {
			queryString += "&cat3=A03030600"; // 바다낚시
		}
		// queryString +="&_type=json";

		return queryString;
	}// end

	// 상세보기 query 용
	private String getDetailQuery(String contentid) {
		String queryString = "";
		queryString = fishDetailCommonURL;
		queryString += "?serviceKey="+TourAPIConfig.TOURAPI_KEY;
		queryString += "&MobileOS=ETC";
		queryString += "&MobileApp=Fisher";

		// 여기서부턴 국문과 영문 분류코드들이 다 다름
		//queryString += "&contentTypeId=75";// 영문 서비스 분류 타입
		queryString+="&contentId="+contentid;
		queryString+="&defaultYN=Y";
		queryString+="&firstImageYN=Y";
		queryString+="&areacodeYN=Y";
		queryString+="&catcodeYN=Y";
		queryString+="&addrinfoYN=Y";
		queryString+="&mapinfoYN=Y";
		queryString+="&overviewYN=Y";

		return queryString;
	}// end

	// XML을 rest template으로 받아야 하는건 맞음
	public List<FishAreaBasedVO> getFishingAreaBased(String urlLoc, String type, String strPageno) {
		String strQuery = getAreaBasedQuery(type,strPageno);
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Collections.singletonList(org.springframework.http.MediaType.APPLICATION_XML));
		HttpEntity<String> entity = new HttpEntity<String>(header);
		RestTemplate restTemp = new RestTemplate();
		URI restURI = null;

		ResponseEntity<String> strResult = null;
		ResponseEntity<FishAB_RestResponse> result = null;
		try {
			restURI = new URI(strQuery);
			// strResult = restTemp.exchange(restURI, HttpMethod.GET, entity, String.class);
			result = restTemp.exchange(restURI, HttpMethod.GET, entity, FishAB_RestResponse.class);
			// String strRVO = strResult.getBody();
			FishAB_RestResponse rVO = result.getBody();
			List<FishAreaBasedVO> fishingList = rVO.getBody().getItems().getItem();
			// return strRVO;
			return fishingList;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public FishDetailCommonVO getFishingDetail(String contentid) {
		String strQuery = getDetailQuery(contentid);
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Collections.singletonList(org.springframework.http.MediaType.APPLICATION_XML));
		HttpEntity<String> entity = new HttpEntity<String>(header);
		RestTemplate restTemp = new RestTemplate();
		URI restURI = null;

		ResponseEntity<String> strResult = null;
		ResponseEntity<FishDC_RestResponse> result = null;
		try {
			restURI = new URI(strQuery);
			 //strResult = restTemp.exchange(restURI, HttpMethod.GET, entity, String.class);
			result = restTemp.exchange(restURI, HttpMethod.GET, entity, FishDC_RestResponse.class);
			//String strRVO = strResult.getBody();
			FishDC_RestResponse rVO = result.getBody();
			FishDetailCommonVO fishVO = rVO.getBody().getItems().getItem();
			 //return strRVO;
			return fishVO;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
