package com.xzp.forum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

	@Insert({ "INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS,
			") VALUES (#{title},#{content},#{category},#{createdDate},#{code},#{idUser})" })
	int addTopic(Topic topic);

	@Select({ "SELECT COUNT(title) FROM", TABLE_NAME, "WHERE id_user=#{userId}" })
	Long countTopicsByUser_Id(@Param("userId") Long userId);

	@Select({ "SELECT", SELECT_FIELDS, "FROM", TABLE_NAME, "WHERE id=#{id}" })
	Topic findTopicById(@Param("id") Long id);
	
	@Select({ "SELECT", SELECT_FIELDS, "FROM", TABLE_NAME, "WHERE category=#{category} ORDER BY created_date DESC" })
	List<Topic> findTopicsByCategoryOrderByCreatedDateDesc(@Param("category") String category);
	
	@Select({ "SELECT", SELECT_FIELDS, "FROM", TABLE_NAME, "WHERE id_user=#{id} ORDER BY created_date DESC" })
	List<Topic> findTopicsByUser_IdOrderByCreatedDateDesc(@Param("id") Long id);
	 
	@Select({"SELECT",SELECT_FIELDS,"FROM",TABLE_NAME,"ORDER BY created_date DESC"})
	List<Topic> findAll();
}
