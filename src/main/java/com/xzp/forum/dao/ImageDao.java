package com.xzp.forum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xzp.forum.model.Image;

@Mapper
public interface ImageDao {
	String TABLE_NAME = "image";
	String INSERT_FIELDS = "imgUrl,id_user";
	String SELECT_FIELDS = "id,imgUrl,id_user";
	
	@Insert({"INSERT INTO ",TABLE_NAME,"(",INSERT_FIELDS,") VALUES (#{imgUrl},#{idUser})"})
	int addImg(Image img);
	
	@Select({"SELECT imgUrl FROM",TABLE_NAME,"WHERE id_user=#{userId} LIMIT 4"})
	List<String> getImgByUserId(@Param("userId") Long userId);
	
	@Select({"SELECT imgUrl FROM",TABLE_NAME,"WHERE id_user=#{userId}"})
	List<String> getAllImgByUserId(@Param("userId") Long userId);
}
