package com.xzp.forum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
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

	@Insert({"INSERT INTO ",TABLE_NAME," (",INSERT_FIELDS,") VALUES ( #{content},#{useful},#{createdDate},#{code},#{idUser},#{idTopic})"})
	int addAnswer(Answer answer);
	
	@Update({ "UPDATE", TABLE_NAME, "SET useful=#{bool} WHERE id=#{id}" })
	void setUsefulForAnswer(@Param("bool") Boolean bool, @Param("id") Long id);

	@Delete({ "DELETE FROM", TABLE_NAME, "WHERE id=#{id}" })
	void deleteAnswerById(@Param("id") Long id);

	@Select({ "SELECT COUNT(content) FROM", TABLE_NAME, "WHERE id_user=#{IdUser}" })
	Long countAnswersByUser_Id(@Param("IdUser") Long IdUser);

	@Select({ "SELECT COUNT(content) FROM", TABLE_NAME, "WHERE id_user=#{IdUser} AND useful=#{useful}" })
	Long countAnswersByUser_IdAndUseful(@Param("IdUser") Long IdUser, @Param("useful") boolean useful);

	 @Select({ "SELECT COUNT(content) FROM", TABLE_NAME, "WHERE id_topic=#{idTopic}" })
	 Long countAnswersByTopic_Id(@Param("idTopic") Long idTopic);
	
	 @Select({ "SELECT", SELECT_FIELDS, "FROM", TABLE_NAME, "WHERE id_user=#{id} ORDER BY created_date DESC" })
	 List<Answer> findAnswerByUser_IdOrderByCreatedDateDesc(@Param("id") Long id);
	
	 @Select({ "SELECT", SELECT_FIELDS, "FROM", TABLE_NAME, "WHERE id_user=#{idUser} AND useful=#{useful} ORDER BY created_date DESC" })
	 List<Answer> findAnswerByUser_IdAndUsefulOrderByCreatedDateDesc(@Param("idUser") Long idUser, @Param("useful") boolean useful);
	
	 @Select({ "SELECT", SELECT_FIELDS, "FROM", TABLE_NAME, "WHERE id_topic=#{idTopic}" })
	 List<Answer> findAnswerByTopic_Id(@Param("idTopic") Long idTopic);
	 
}
