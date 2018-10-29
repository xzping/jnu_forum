package com.xzp.forum.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xzp.forum.model.Message;

@Mapper
public interface MessageDao {
	String TABLE_NAME = "message";
	String INSERT_FIELDS = "from_id,to_id,content,created_date,id_topic,has_read";
	String SELECT_FIELDS = "id,from_id,to_id,content,created_date,id_topic,has_read";

	/**
	 * 增加一条消息
	 * 
	 * @param message
	 * @return int
	 */
	int addMessage(Message message);

	/**
	 * 根据用户的id[to_id]获得消息列表
	 * 
	 * @param toId
	 * @return List<Message>
	 */
	List<Message> getMessageByToId(@Param("toId") Long toId);

	/**
	 * 根据topic_id删除对应的消息
	 * 
	 * @param topic_id
	 */
	void deleteMessageByTopicId(@Param("topic_id") Long topic_id);

	/**
	 * 根据用户的id[to_id]计算消息数量
	 * 
	 * @param toId
	 * @return int
	 */
	int countMessageByToId(@Param("toId") Long toId);

	/**
	 * 根据用户id[to_id]
	 * 
	 * @param toId
	 * @return List<Message>
	 */
	List<Message> getUnReadMessageByToId(@Param("toId") Long toId);

	/**
	 * 改变已读的状态
	 * 
	 * @param id
	 */
	void changeReadStatement(@Param("id") Long id);
}
