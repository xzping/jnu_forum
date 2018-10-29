package com.xzp.forum.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

	/**
	 * addAnswer
	 * 
	 * @param answer
	 * @return
	 */
	int addAnswer(Answer answer);

	/**
	 * setUsefulForAnswer
	 * 
	 * @param bool
	 * @param id
	 */
	void setUsefulForAnswer(@Param("bool") Boolean bool, @Param("id") Long id);

	/**
	 * deleteAnswerById
	 * 
	 * @param id
	 */
	void deleteAnswerById(@Param("id") Long id);

	/**
	 * deleteAnswerByTopic_id
	 * 
	 * @param topic_id
	 */
	void deleteAnswerByTopic_id(@Param("topic_id") Long topic_id);

	/**
	 * countAnswersByUser_Id
	 * 
	 * @param IdUser
	 * @return Long
	 */
	Long countAnswersByUser_Id(@Param("IdUser") Long IdUser);

	/**
	 * countAnswersByUser_IdAndUseful
	 * 
	 * @param IdUser
	 * @param useful
	 * @return Long
	 */
	Long countAnswersByUser_IdAndUseful(@Param("IdUser") Long IdUser, @Param("useful") boolean useful);

	/**
	 * countAnswersByTopic_Id
	 * 
	 * @param idTopic
	 * @return Long
	 */
	Long countAnswersByTopic_Id(@Param("idTopic") Long idTopic);

	/**
	 * findAnswerByUser_IdOrderByCreatedDateDesc
	 * 
	 * @param id
	 * @return List<Answer>
	 */
	List<Answer> findAnswerByUser_IdOrderByCreatedDateDesc(@Param("id") Long id);

	/**
	 * findAnswerByUser_IdAndUsefulOrderByCreatedDateDesc
	 * 
	 * @param idUser
	 * @param useful
	 * @return List<Answer>
	 */
	List<Answer> findAnswerByUser_IdAndUsefulOrderByCreatedDateDesc(@Param("idUser") Long idUser,
			@Param("useful") boolean useful);

	/**
	 * findAnswerByTopic_Id
	 * 
	 * @param idTopic
	 * @return List<Answer>
	 */
	List<Answer> findAnswerByTopic_Id(@Param("idTopic") Long idTopic);

	/**
	 * getIdUserById
	 * 
	 * @param id
	 * @return Long
	 */
	Long getIdUserById(@Param("id") Long id);
}
