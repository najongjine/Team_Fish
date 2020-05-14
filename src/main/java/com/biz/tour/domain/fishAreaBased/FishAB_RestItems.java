package com.biz.tour.domain.fishAreaBased;

import java.util.List;

import com.biz.tour.domain.fishAreaBased.FishAreaBasedVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class FishAB_RestItems {
	private List<FishAreaBasedVO> item;
}
