package com.biz.tour.domain.fishAreaBased;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement(name="response")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FishAB_RestResponse {
	private String header; // {resultCode:String,resultMsg:String}
	private String numOfRows; // can be parse to int
	private String pageNo;// can be parse to int
	private String totalCount;// can be parse to int
	private FishAB_RestBody body;
	
}
