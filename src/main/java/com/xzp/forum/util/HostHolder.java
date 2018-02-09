package com.xzp.forum.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.xzp.forum.dao.UserDao;
import com.xzp.forum.model.User;

/**
 * 获取当前登录的用户User
 * 
 * @author xiezhiping
 *
 */
@Component
public class HostHolder {
	
	private User user;
	
	@Autowired
	UserDao userDao;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user=userDao.getUserByUsername(((UserDetails) principal).getUsername());
		this.user = user;
	}
}
