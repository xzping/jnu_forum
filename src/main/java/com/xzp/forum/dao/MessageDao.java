package com.xzp.forum.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xzp.forum.model.Message;

@Mapper
public interface MessageDao {
	String TABLE_NAME = "message";
	String INSERT_FIELDS = "from_id,to_id,content,created_date,id_topic,has_read";
	String SELECT_FIELDS = "id,from_id,to_id,content,created_date,id_topic,has_read";

	@Insert({ "INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS,
			") VALUES (#{fromId},#{toId},#{content},#{createdDate},#{idTopic},#{hasRead})" })
	int addMessage(Message message);
	
	@Select({"SELECT",SELECT_FIELDS,"FROM",TABLE_NAME,"WHERE to_id=#{toId}"})
	List<Message> getMessageByToId(@Param("toId") Long toId);
	
	@Delete({"DELETE FROM",TABLE_NAME,"WHERE id_topic=#{topic_id}"})
	void deleteMessageByTopicId(@Param("topic_id") Long topic_id);
	
	@Select({"SELECT COUNT(id) FROM",TABLE_NAME,"WHERE to_id=#{toId} AND has_read=0"})
	int countMessageByToId(@Param("toId") Long toId);
	
	@Select({"SELECT",SELECT_FIELDS,"FROM",TABLE_NAME,"WHERE to_id=#{toId} AND has_read=0"})
	List<Message> getUnReadMessageByToId(@Param("toId") Long toId);
	
	@Update({"UPDATE",TABLE_NAME,"SET has_read=1 WHERE id=#{id}"})
	void changeReadStatement(@Param("id") Long id);
}
