package com.xzp.forum.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xzp.forum.model.Message;

@Mapper
public interface MessageDao {
	String TABLE_NAME = "message";
	String INSERT_FIELDS = "from_id,to_id,content,created_date";
	String SELECT_FIELDS = "id,from_id,to_id,content,created_date";

	@Insert({ "INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS,
			") VALUES (#{fromId},#{toId},#{content},#{createdDate})" })
	int addMessage(Message message);
	
	@Select({"SELECT",SELECT_FIELDS,"FROM",TABLE_NAME,"WHERE to_id=#{toId}"})
	List<Message> getMessageByToId(@Param("toId") Long toId);
	
}
