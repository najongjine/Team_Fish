package com.biz.tour.service.marinelife;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.biz.tour.dao.marinelife.MarineAnimalDao;
import com.biz.tour.dao.marinelife.NaverAPIDao;
import com.biz.tour.domain.marinelife.MarineLifeAPIVO;
import com.biz.tour.domain.marinelife.NaverAPIVO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class MarineLifeAPIServiceImp implements MarineLifeAPIService {

	private final MarineAnimalDao maDao;
	private final NaverAPIDao nAPIDao;

	@Override
	public List<MarineLifeAPIVO> getDataByCoordinates(String mapx, String mapy, String pageno, String dataLimit) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
	
	
	
	
	
	
	
	//위도 경도를 넘겨 해양생물 테이블에서 해양 생물 이름 select
	@Override
	public List<NaverAPIVO> getXYMarine(int mapX, int mapY) {
		// TODO Auto-generated method stub

		List<MarineLifeAPIVO> marineList = maDao.getXYMarine(mapX, mapY);
		
		log.debug("좌표 검색으로 SELECT: " + marineList.toString());
		
		
		// 중복 제거용
		Map<String, String> marineMap = new HashMap<String, String>();
		
		List<String> marineNameList = new ArrayList<String>();
		// mapX == latD
		
		
		// 중복 제거용2
		Map<String, String> nameDelete = new HashMap<String, String>();
		
		
		List<String> marineFinalList = new ArrayList<String>();
		
		


		// 최초 SELECT NULL일 시
		if (marineList.size() < 1 || marineList == null || marineList.get(0) == null ) {

			log.debug("NULL 진입");

			// 좌표값에 변동주면서 해양생물 이름 추출
			for (int i = mapX - 1; i < mapX + 2; i++) {

				for (int j = mapY - 1; j < mapY + 2; j++) {

					List<MarineLifeAPIVO> _marineList = maDao.getXYMarine(i, j);
					marineList.addAll(_marineList);

				}
			}

			
			// API 데이터 부실로 인한 null 제거 및 이름 중복 해결 위한 map처리 작업
			for (int i = 0; i < marineList.size(); i++) {

				if (marineList.get(i) == null) {
					marineList.remove(i);
					
				}else {
					marineMap.put(marineList.get(i).getSciKr(), marineList.get(i).getSciKr());
				}
			}
			
			
			
			marineNameList = new ArrayList<String>(marineMap.keySet());
			
			log.debug("중복값 제거?: " + marineNameList.toString());
			
			
			// 최초 SELECT NULL 아닐 시
		} else {

			for (int i = 0; i < marineList.size(); i++) {

				if (marineList.get(i) == null) {
					marineList.remove(i);
				}
				
				
				
				
				marineNameList.add(marineList.get(i).getSciKr().trim().replace(" ", ""));
				

			}

		}
		
		log.debug("##MARINENAMELIST: "  + marineNameList.toString());
		log.debug("##MARINENAMELISTSIZE: "  + marineNameList.size());

		log.debug("##MARINEMAP: " + marineMap.toString());
		log.debug("##MARINEMAPSIZE: " + marineMap.size());

		log.debug("##MARINELIST: " + marineList.toString());
		log.debug("##MARINELISTSIZE: " + marineList.size());
		

		
		String marineName;
		
		
		
		
		
		
		
		
		
		// 네이버API테이블 조회 위해 해양생물 이름에 붙어 있는 언더바 및 대괄호 제거 작업
		for(int i = 0; i < marineNameList.size(); i++) {
		
		if( marineNameList.get(i).toString().contains("_")) {
			
			log.debug("_언더바에 걸린 문자열: " + marineNameList.get(i).toString());
			int charIndex = marineNameList.get(i).toString().indexOf("_");
			marineName =	marineNameList.get(i).toString().substring(0, charIndex).trim().replace(" ", "") ;
			log.debug("_언더바 제거 후 문자열: " + marineName);
			//marineFinalList.add(marineName);
			
			nameDelete.put(marineName, marineName);
		}
		else if( marineNameList.get(i).toString().contains("(")) {
			log.debug("(대괄호에 걸린 문자열: " + marineNameList.get(1).toString());
			int charIndex = marineNameList.get(i).toString().indexOf("(");
			marineName = marineNameList.get(i).toString().substring(0, charIndex).trim().replace(" ", "") ;
			log.debug("(대괄호 제거 후 문자열: " + marineName);
			//marineFinalList.add(marineName);
			nameDelete.put(marineName, marineName);
		}else {
			//marineFinalList.add(marineNameList.get(i).trim().replace(" ", ""));
			
			nameDelete.put(marineNameList.get(i).trim().replace(" ", ""), marineNameList.get(i).trim().replace(" ", ""));
		}
		
		
		
		
		}
		
		
		
		
		log.debug("중복제거 확인 맵: " +nameDelete.toString());
		log.debug("중복제거 확인 맵 사이즈: " +nameDelete.size());

		
		marineFinalList = new ArrayList<String>(nameDelete.keySet());
		
		
		log.debug("마린네임리스트: " + marineFinalList.toString());
		log.debug("마린네임리스트: " + marineFinalList.size());
		
		 //해양생물 이름 리스트를 넘겨 최종 네이버API 테이블에서 데이터 추출
		 List<NaverAPIVO> naverAPIFinalList = this.finalGetAPIList(marineFinalList);
		
		return naverAPIFinalList;

	}
	
	
	
	

	
	
	// 해양생물 이름 리스트를 넘겨 최종 네이버API 테이블에서 데이터 추출
	private List<NaverAPIVO> finalGetAPIList(List<String> marineNameList) {
		// TODO Auto-generated method stub
		
		List<NaverAPIVO> naverAPIFinalList = new ArrayList<NaverAPIVO>();
		
		
		for(int i = 0; i < marineNameList.size(); i++) {
			
			
			NaverAPIVO naverAPIVO = nAPIDao.selectByTitle(marineNameList.get(i));
			//log.debug("셀렉트 조회값: " + naverAPIVO.toString());
			
			if(naverAPIVO != null) {
			naverAPIFinalList.add(naverAPIVO);
			}

		}
		
		
		log.debug("파이널리스트: " + naverAPIFinalList.toString());
		log.debug("파이널리스트사이즈: " + naverAPIFinalList.size());
		
		return naverAPIFinalList;
	}



	
	
	
	/*
	 * 
	 *  --------------------------------------------이 아래로는 API 호출~ 테이블 저장용 코드들 ------------------------------------------------------------------------------
	 */
	
	
	
	
	//  해양생물 테이블의 모든 이름 group by해서 naverapi 검색 후 모두 naverapi테이블에 저장
	public void saveFullNaverAPI() {
		
		
		List<String> marineTitleList = maDao.selectAllTitle();
		
		
		
		
		
		for(int i = 0; i < marineTitleList.size(); i++) {
			if(marineTitleList.get(i) == null) {
				marineTitleList.remove(i);
			}
		}
		
		log.debug("리스트사이즈:" + marineTitleList.size());
		this.callNaverAPI(marineTitleList);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	


	private List<NaverAPIVO> callNaverAPI(List<String> marineNameList){
		// TODO Auto-generated method stub
		
		
		final String NAVER_CLIENTID = "tli3yz8GPo52_aOHiTot";
		final String NAVER_CLIENTSECRET = "x5Clo0yY24";
		
		//String queryURL = "https://openapi.naver.com/v1/search/encyc.json";
		

		
		
		//StringBuffer resString = new StringBuffer();
		
		JsonElement responseJsonElement;
		JsonObject responseJsonObj;
		JsonObject responseGetItem;
		JsonArray responseJsonArr;
		Gson gSon;
		Type listType;
		
		
		//naverAPI 저장용 list
		List<NaverAPIVO> naverAPIResultList = new ArrayList<NaverAPIVO>();
		
		
		
		// 최종 return용 list
		List<NaverAPIVO> naverAPIFinalList = new ArrayList<NaverAPIVO>();
		
			
			
		
		log.debug("네이버API진입 후 MARINENAMELISTSIZE: " + marineNameList.size());
			
			
		for(int i = 0; i < marineNameList.size(); i++) {
			
			
			
			
			

			
			String queryString;
			
			// 언더바와 대괄호가 제거되
			String queryName = null;
			
			URL url;
			HttpURLConnection connection;
			
			BufferedReader br;
			InputStreamReader isr;
			
			// 네이버 API에서 불러온 생물 이름에서 html 태그 제거<b></b>
			String naverAPITitle = null;
			

			try {
				
				


				log.debug("NAVER API 진입: " + marineNameList.get(i).toString());
				queryName = "";
				
				
				// 문자열에 특수기호 제거
				if( marineNameList.get(i).toString().contains("_")) {
					
					log.debug("_언더바에 걸린 문자열: " + marineNameList.get(i).toString());
					int charIndex = marineNameList.get(i).toString().indexOf("_");
					queryName = marineNameList.get(i).toString().substring(0, charIndex).trim().replace(" ", "");
					log.debug("_언더바 제거 후 문자열: " + queryName);
				}
				else if( marineNameList.get(i).toString().contains("(")) {
					log.debug("(대괄호에 걸린 문자열: " + marineNameList.get(1).toString());
					int charIndex = marineNameList.get(i).toString().indexOf("(");
					queryName = marineNameList.get(i).toString().substring(0, charIndex).trim().replace(" ", "");
					log.debug("(대괄호 제거 후 문자열: " + queryName);
				}else {
					queryName = marineNameList.get(i);
				}
				
				
				
				
				
				NaverAPIVO naverAPIVO = nAPIDao.selectByTitle(queryName);
				
				
				
				
				
				if(naverAPIVO != null) {
					
					log.debug("테이블에 조회 결과 있음! 바로 list에 ADD!");
					naverAPIFinalList.add(naverAPIVO);
					
					
				}else {
				
					log.debug("테이블에 조회 결과 없으므로 네이버 api호출");
				
				
					log.debug("QUERYNAME: " + queryName);
					
					queryString = URLEncoder.encode( queryName,"UTF-8");	
					log.debug("QUERYSTRING: " + queryString);
					String queryURL = "https://openapi.naver.com/v1/search/encyc.json" + "?query=" + queryString + "&display=" + 1 + "&start=" + 1;
					log.debug("QUERYURL: "  + queryURL);
					url = new URL(queryURL);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestProperty("X-Naver-Client-Id", NAVER_CLIENTID);
					connection.setRequestProperty("X-Naver-Client-Secret", NAVER_CLIENTSECRET);
					
					int responseCode = connection.getResponseCode();
					
					if(responseCode == 200) {
						isr = new InputStreamReader(connection.getInputStream());
						br = new BufferedReader(isr);
					}else {
						isr = new InputStreamReader(connection.getErrorStream());
						br = new BufferedReader(isr);
					}
					String reader = null;
					StringBuffer resString = new StringBuffer();
					
					while(true) {
						reader = br.readLine();
						if(reader == null) {
							break;
						}
						
						
						resString.append(reader);
						
					}
					
					
					br.close();
					System.out.println("HTTP 응답 코드: " + responseCode);
					System.out.println("HTTP BODY: " + resString.toString());
					
					
					
					
					
					
					
					responseJsonElement = JsonParser.parseString(resString.toString());
	
					responseJsonObj = responseJsonElement.getAsJsonObject();
					log.debug("과연: " + responseJsonObj.toString());
	
	
					//responseGetItem = (JsonObject) responseJsonObj.get("items");
					responseJsonArr = (JsonArray) responseJsonObj.getAsJsonArray("items");
	
					gSon = new Gson();
	
					// json 결과물이 한 개씩이라 list 쓸 필요가 없지만 코드 수정 귄찮아서 
					listType = new TypeToken<ArrayList<NaverAPIVO>>() {
					}.getType();
	
					naverAPIResultList = gSon.fromJson(responseJsonArr, listType);
	
					
					
					log.debug("NAVERAPIRESULTLIST: " + naverAPIResultList.toString());
					
					
					
					// 네이버 API에서 불러온 생물 이름에서 html 태그 제거<b></b>
					if(naverAPIResultList.size() > 0) {
					naverAPIResultList.get(0).setTitle(naverAPIResultList.get(0).getTitle().replace("<b>", "").replace("</b>", ""));
					
					
					
					
					
					// marineNameList 와 naverAPIResultList.getTitle 이름 비교 후 naverAPI 테이블에 삽입 작업
						if (queryName.equals(naverAPIResultList.get(0).getTitle())) {
							log.debug("같은 이름의 QUERYNAME: " + queryName);
							log.debug("같은 이름의 NAVERAPIRESULTLIST: " + naverAPIResultList.get(0).getTitle());
							
							
							// 이름 같으면 모든 정보를 insert
							naverAPIResultList.get(0).setFullInfo(true);
							nAPIDao.insertFull(naverAPIResultList);
							naverAPIFinalList.addAll(naverAPIResultList);
						}else {
							log.debug("다른 이름의 QUERYNAME: " + queryName);
							log.debug("다른 이름의 NAVERAPIRESULTLIST: " + naverAPIResultList.get(0).getTitle());
							
							naverAPIResultList.get(0).setTitle(queryName);
							
							// 이름이 같지 않으면 title(생물 이름)만 삽입
							naverAPIResultList.get(0).setFullInfo(false);
							nAPIDao.insertTitle(naverAPIResultList.get(0).getTitle(), naverAPIResultList.get(0).isFullInfo());
							naverAPIFinalList.addAll(naverAPIResultList);
						}
					
					
					}
				
			}
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			// API 호출 속도제한 피하기 위한 조치
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 
			
			
			
		}
		
		
		
		return naverAPIFinalList;
		
		
		
		
		
		
	}





	public int insertService() {
		// TODO Auto-generated method stub

		String queryURL = "http://apis.data.go.kr/B551979/marineOrganismInhabitInfoService/getHabitatGisList";
		String serviceKey = "2D5rJ2dlm%2BXKIkVprSSgKI0HU08V%2FYBLqD8l%2Furac2yM3d8LozeI%2BZJmfDX9%2BsAZY7abFzCGzXhRWQL%2BcQdgSA%3D%3D";
		Gson gSon;
		Type listType;
		List<MarineLifeAPIVO> marinList;
		JsonElement responseJsonElement;
		JsonObject responseJsonObj;
		JsonObject responseGetItem;
		JsonArray responseJsonArr;
		URL url;
		HttpURLConnection connection;
		BufferedReader br;
		String inputLine;
		StringBuffer response;
		// StringBuffer response = null;

		try {

			// for문 돌릴 시 두 번째에 JSON 파싱 에러남..... 급하니 그냥 수동으로 페이지 교체하며 저장
			// for(int pageNo = 1; pageNo < 11; pageNo++) {

			queryURL = queryURL + "?ServiceKey=" + serviceKey + "&pageNo=" + 1 + "&numOfRows=" + 2000 + "&_type=json";

			url = new URL(queryURL);
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-type", "application/json");

			System.out.println("Response code: " + connection.getResponseCode());

			int responseCode = connection.getResponseCode();
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			response = new StringBuffer();

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			br.close();

			System.out.println("HTTP 응답 코드: " + responseCode);
			System.out.println("HTTP BODY: " + response.toString());

			responseJsonElement = JsonParser.parseString(response.toString());

			responseJsonObj = responseJsonElement.getAsJsonObject();
			log.debug("과연: " + responseJsonObj.toString());

			responseGetItem = (JsonObject) responseJsonObj.get("response");
			responseGetItem = (JsonObject) responseGetItem.get("body");
			responseGetItem = (JsonObject) responseGetItem.get("items");
			responseJsonArr = (JsonArray) responseGetItem.getAsJsonArray("item");

			gSon = new Gson();

			listType = new TypeToken<ArrayList<MarineLifeAPIVO>>() {
			}.getType();

			marinList = gSon.fromJson(responseJsonArr, listType);

			maDao.insertAnimal(marinList);

			// }

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public void getAllMarine() {
		// TODO Auto-generated method stub

		List<MarineLifeAPIVO> marineList = maDao.getAllMarine();

		log.debug("MARINELIST: " + marineList.toString());

	}

	

}
