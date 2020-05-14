package com.biz.tour.dao.usersea;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.biz.tour.domain.usersea.FishUserSeaPicsVO;
import com.biz.tour.domain.usersea.FishUserSeaVO;

public interface FishUserSeaDao {
	@Select("select * from tbl_userfish_sea limit #{pageno},#{itemLimit}")
	@Results( //constraint 조건이 걸린
			value= {
				@Result(property="uf_id", column = "uf_id"),
				@Result(property = "picsList", column = "uf_id", javaType = List.class, 
				many=@Many(select = "findPicsByFK"))
				}
			)
	public List<FishUserSeaVO> findAll(@Param("pageno") int pageno,@Param("itemLimit") int itemLimit);
	
	@Select("select count(*) from tbl_userfish_sea")
	public int countFindAll();
	
	@Select("select * from tbl_userfish_sea_pics where ufp_fk=#{uf_id}")
	public List<FishUserSeaPicsVO> findPicsByFK(long uf_id);
	
	@Select("select * from tbl_userfish_sea where uf_id=#{uf_id}")
	public FishUserSeaVO findById(Long uf_id);
	
	@Select("select * from tbl_userfish_sea where uf_title like CONCAT ('%', #{uf_title} ,'%') limit #{pageno},#{itemLimit} ")
	@Results( //constraint 조건이 걸린
			value= {
				@Result(property="uf_id", column = "uf_id"),
				@Result(property = "picsList", column = "uf_id", javaType = List.class, 
				many=@Many(select = "findPicsByFK"))
				}
			)
	public List<FishUserSeaVO> findByTitle(@Param("uf_title") String uf_title,@Param("pageno") int pageno,@Param("itemLimit") int itemLimit);
	
	@Select("select count(*) from tbl_userfish_sea where uf_title like CONCAT ('%', #{uf_title} ,'%')")
	public int countFindByTitle(@Param("uf_title") String uf_title);
	
	@Select("select max(uf_id) from tbl_userfish_sea")
	public long getMaxID();
	
	public int insert(FishUserSeaVO userVO);

	public int update(FishUserSeaVO userVO);
	
	@Delete("delete from tbl_userfish_sea where uf_id=#{uf_id}")
	public int delete(long uf_id);

}
