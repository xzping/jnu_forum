package com.xzp.forum.util;

import org.springframework.stereotype.Component;

import com.xzp.forum.model.User;

@Component
public class LocalHost {
	// 服务器不是一个人在用，通过线程本地变量来存储当前用户自己的信息，多线程之间互相不冲突。
	private static ThreadLocal<User> users = new ThreadLocal<User>();

	public User getUser() {
		return users.get();
	}

	public void setUser(User user) {
		users.set(user);
	}

	public void clear() {
		users.remove();
	}
}
