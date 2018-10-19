package com.xzp.forum.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.xzp.forum.model.User;

/**
 * user的dao层
 * 
 * @author xiezhiping
 *
 */
@Mapper
public interface UserDao {
	String TABLE_NAME = "user";
	String INSERT_FIELDS = "username,password,introduction,created_date";
	String SELECT_FIELDS = "id,username,password,introduction,created_date";

	int addUser(User user);

	User getUserByUsername(@Param("username") String username);

	User getUserById(@Param("id") Long id);

	Long getPoints(@Param("id") Long id);

	Long getIdByUsername(@Param("username") String username);

	String getUsernameById(@Param("id") Integer id);
}
