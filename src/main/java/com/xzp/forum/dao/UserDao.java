package com.xzp.forum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xzp.forum.model.User;

@Mapper
public interface UserDao {
	String TABLE_NAME = "user";
	String INSERT_FIELDS = "username,password,introduction,created_date";
	String SELECT_FIELDS = "id,username,password,introduction,created_date";

	@Insert({ "INSERT INTO ", TABLE_NAME, " (", INSERT_FIELDS,
			") VALUES (#{username},#{password},#{introduction},#{createdDate})" })
	int addUser(User user);

	@Select({ "SELECT", SELECT_FIELDS, "FROM ", TABLE_NAME, "WHERE username=#{username}" })
	User getUserByUsername(@Param("username") String username);

	 @Select({ "SELECT", SELECT_FIELDS, "FROM", TABLE_NAME, "WHERE id=#{id}" })
	User getUserById(@Param("id") Long id);

	
//	 @Select({ "SELECT", SELECT_FIELDS, "FROM", TABLE_NAME })
//	List<User> findAll();

	@Select({ "(SELECT SUM(points) FROM (SELECT COUNT(topic.id_user) AS points FROM topic WHERE topic.id_user=#{id}"
			+ " UNION ALL SELECT 2*COUNT(answer.id_user) AS points FROM answer WHERE answer.id_user=#{id} UNION ALL "
			+ "SELECT 3*COUNT(answer.id_user) AS points FROM answer WHERE answer.id_user=#{id} AND answer.useful=TRUE) t)" })
//	 @Select({"SELECT id FROM ",TABLE_NAME,"WHERE id=#{id}"})
	Long getPoints(@Param("id") Long id);
}
