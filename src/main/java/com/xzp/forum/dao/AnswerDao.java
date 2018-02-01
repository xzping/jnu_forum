package com.xzp.forum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xzp.forum.model.Answer;

/**
 * answer的dao层
 * 
 * @author xiezhiping
 *
 */
@Mapper
public interface AnswerDao {
	String TABLE_NAME = "answer";
	String INSERT_FIELDS = "content,useful,created_date,code,id_user,id_topic";
	String SELECT_FIELDS = "id,content,useful,created_date,code,id_user,id_topic";

	// @Update({ "UPADATE", TABLE_NAME, "SET userful=#{bool} WHERE id=#{id}" })
	// void setUsefulForAnswer(@Param("bool") Boolean bool, @Param("id") Long id);
	//
	// @Delete({ "DELECT FROM", TABLE_NAME, "WHERE id=#{id}" })
	// void deleteAnswerById(@Param("id") Long id);

	@Select({ "SELECT COUNT(content) FROM", TABLE_NAME, "WHERE id_user=#{userId}" })
	Long countAnswersByUser_Id(@Param("userId") Long userId);

	@Select({ "SELECT COUNT(content) FROM", TABLE_NAME, "WHERE id_user=#{userId} AND useful=#{useful}" })
	Long countAnswersByUser_IdAndUseful(@Param("userId") Long userId, @Param("useful") boolean useful);

	// @Select({ "SELECT COUNT(content) FROM", TABLE_NAME, "WHERE
	// idTopic=#{topic_id}" })
	// Long countAnswersByTopic_Id(@Param("topic_id") Long topic_id);
	//
	// @Select({ "SELECT", SELECT_FIELDS, "FROM", TABLE_NAME, "WHERE user_id=#{id}
	// ORDER BY createdDate DESC" })
	// List<Answer> findAnswerByUser_IdOrderByCreatedDateDesc(@Param("id") Long id);
	//
	// @Select({ "SELECT", SELECT_FIELDS, "FROM", TABLE_NAME,
	// "WHERE user_id=#{user_id} AND userful=#{userful} ORDER BY createdDate DESC"
	// })
	// List<Answer>
	// findAnswerByUser_IdAndUsefulOrderByCreatedDateDesc(@Param("user_id") Long
	// user_id,
	// @Param("useful") boolean useful);
	//
	// @Select({ "SELECT", SELECT_FIELDS, "FROM", TABLE_NAME, "WHERE
	// topic_id={topic_id}" })
	// List<Answer> findAnswerByTopic_Id(@Param("topic_id") Long topic_id);
}
