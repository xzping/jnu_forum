package com.xzp.forum.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.xzp.forum.model.Topic;

/**
 * topic的dao层
 * 
 * @author xiezhiping
 *
 */
@Mapper
public interface TopicDao {
	String TABLE_NAME = "topic";
	String INSERT_FIELDS = "title,content,category,created_date,code,id_user";
	String SELECT_FIELDS = "id,title,content,category,created_date,code,id_user";

	/**
	 * 插入一条话题记录
	 * @param topic
	 * @return int
	 */
	int addTopic(Topic topic);
	
	/**
	 * 根据id删除一条话题记录
	 * @param id
	 */
	void deleteTopicById(@Param("id") Long id);

	/**
	 * 统计用户发的话题数量
	 * @param userId
	 * @return Long
	 */
	Long countTopicsByUser_Id(@Param("userId") Long userId);

	/**
	 * 根据id查找话题
	 * @param id
	 * @return Topic
	 */
	Topic findTopicById(@Param("id") Long id);
	
	/**
	 * 获取目录category下的所有话题
	 * @param category
	 * @return List<Topic>
	 */
	List<Topic> findTopicsByCategoryOrderByCreatedDateDesc(@Param("category") String category);
	
	/**
	 * 获取用户发布的所有话题记录
	 * @param id
	 * @return List<Topic>
	 */
	List<Topic> findTopicsByUser_IdOrderByCreatedDateDesc(@Param("id") Long id);
	 
	/**
	 * 获得所有话题
	 * @return List<Topic>
	 */
	List<Topic> findAll();
	
	/**
	 * 根据topic的id获得用户id
	 * @param id
	 * @return int
	 */
	int getId_userById(@Param("id") Long id);
}
