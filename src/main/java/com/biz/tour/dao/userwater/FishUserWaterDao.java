package com.biz.tour.dao.userwater;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.biz.tour.domain.userwater.FishUserWaterPicsVO;
import com.biz.tour.domain.userwater.FishUserWaterVO;

public interface FishUserWaterDao {
	@Select("select * from tbl_userfish_water limit #{pageno},#{itemLimit}")
	@Results( //constraint 조건이 걸린
			value= {
				@Result(property="uf_id", column = "uf_id"),
				@Result(property = "picsList", column = "uf_id", javaType = List.class, 
				many=@Many(select = "findPicsByFK"))
				}
			)
	public List<FishUserWaterVO> findAll(@Param("pageno") int pageno,@Param("itemLimit") int itemLimit);
	
	@Select("select count(*) from tbl_userfish_water")
	public int countFindAll();
	
	@Select("select * from tbl_userfish_water_pics where ufp_fk=#{uf_id}")
	public List<FishUserWaterPicsVO> findPicsByFK(String uf_id);
	
	@Select("select * from tbl_userfish_water where uf_id=#{uf_id}")
	public FishUserWaterVO findById(Long uf_id);
	
	@Select("select * from tbl_userfish_water where uf_title like CONCAT ('%', #{uf_title} ,'%') limit #{pageno},#{itemLimit} ")
	@Results( //constraint 조건이 걸린
			value= {
				@Result(property="uf_id", column = "uf_id"),
				@Result(property = "picsList", column = "uf_id", javaType = List.class, 
				many=@Many(select = "findPicsByFK"))
				}
			)
	public List<FishUserWaterVO> findByTitle(@Param("uf_title") String uf_title,@Param("pageno") int pageno,@Param("itemLimit") int itemLimit); 
	
	@Select("select count(*) from tbl_userfish_water where uf_title like CONCAT ('%', #{uf_title} ,'%')")
	public int countFindByTitle(@Param("uf_title") String uf_title);
	
	@Select("select max(uf_id) from tbl_userfish_water")
	public long getMaxID();
	
	public int insert(FishUserWaterVO userVO);

	public int update(FishUserWaterVO userVO);

	@Delete("delete from tbl_userfish_water where uf_id=#{uf_id}")
	public int delete(long uf_id);

}
