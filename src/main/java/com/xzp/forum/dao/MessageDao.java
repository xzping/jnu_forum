package com.xzp.forum.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xzp.forum.model.Message;

@Mapper
public interface MessageDao {
	String TABLE_NAME = "message";
	String INSERT_FIELDS = "from_id,to_id,content,created_date,conversation_id";
	String SELECT_FIELDS = "id,from_id,to_id,content,created_date,conversation_id";

	@Insert({ "INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS,
			") VALUES (#{fromId},#{toId},#{content},#{createdDate},#{conversationId})" })
	int addMessage(Message message);
	
	@Select({"SELECT content FROM",TABLE_NAME})
	String getContent();
}
