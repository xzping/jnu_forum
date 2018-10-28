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

	/**
	 * 注册一个用户
	 * @param user
	 * @return int
	 */
	int addUser(User user);

	/**
	 * 根据用户名获得相应用户对象
	 * @param username
	 * @return User
	 */
	User getUserByUsername(@Param("username") String username);

	/**
	 * 根据用户id获得相应用户对象
	 * @param id
	 * @return User
	 */
	User getUserById(@Param("id") Long id);

	/**
	 * 获得用户的分数
	 * @param id
	 * @return Long
	 */
	Long getPoints(@Param("id") Long id);

	/**
	 * 根据用户名获得id
	 * @param username
	 * @return
	 */
	Long getIdByUsername(@Param("username") String username);

	/**
	 * 根据id获得用户名
	 * @param id
	 * @return
	 */
	String getUsernameById(@Param("id") Integer id);
}
