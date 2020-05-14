package com.biz.tour.domain.usersea;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FishUserSeaVO {
	private long uf_id;// bigint AUTO_INCREMENT	PRIMARY KEY,
	private String uf_username;// nVARCHAR(50),
	private String uf_title;// nVARCHAR(50),
	private String uf_date;// nVARCHAR(150),
	private String uf_addr1;// nVARCHAR(50),
	private String uf_addr2;
	private String uf_text;// nVARCHAR(2000)
	
	private List<FishUserSeaPicsVO> picsList;
	private String mainPic;
}
