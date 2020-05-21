package com.biz.tour.domain.marinelife;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MarineLifeAPIVO {
	
	
	
	private String sciKr;//해양생물 국명		100	필수	갈대_(단자엽실물 벼과)	NULL
	private String phylgntClas;//계통분류		TEXT	필수	NULL	NULL
	private String distrInh;//분포/서식		TEXT	필수	강하구 혹은 펄갯벌의 연안육지지역	NULL
	private String chrtr;//특징		TEXT	필수	전체높이 약1~2m , 잎길이 약40~60cm , 잎폭 2~4cm 정도<br>전 해안이나 강 하구에 서식하는 여러해살이풀로 땅 속으로 뿌리줄기가 뻗으며 성장한다. 지상부는 겨울에 모두 죽고 이른 봄에 녹색의 새 잎이 돋아나며 9월에 흑자색의 꽃이 핀다.	NULL
	private String fileNm;//파일명		100	필수	http://ecosea.go .kr/data/P2/ 200801231438120. jpg	NULL
	//private long lat;//위도		20	필수	35˚ 12´ 50˝	NULL
	//private long lon;//경도		20	필수	128˚ 37´ 9˝	NULL
	private double latD;//	위도_도	2	필수	35	NULL
	private double latM;//	위도_분	2	필수	12	NULL
	private double latS;//위도_초		4,2	필수	50	NULL   //
	private double lonD;//경도_도		2	필수	128	NULL
	private double lonM;//경도_분		2	필수	37	NULL
	private double lonS;//경도_초		4,2	필수	9.00	NULL

}
