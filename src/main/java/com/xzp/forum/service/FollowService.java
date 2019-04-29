package com.xzp.forum.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xzp.forum.dao.UserDao;
import com.xzp.forum.model.User;
import com.xzp.forum.util.JedisAdapter;

@Service
public class FollowService {
	
	private static final Logger logger = LoggerFactory.getLogger(FollowService.class);
	
	@Autowired
	private JedisAdapter jedisAdapter;
	
	@Autowired
	private UserDao userDao;
	
	public void addFollow(String userName, Long userId, Long otherUserId)
	{
		// key:当前用户username_id，value:关注该用户的用户id
		String key = userName + "_"+ otherUserId;
		logger.warn("follow function jedis key : {}",key);
		jedisAdapter.sadd(key, String.valueOf(userId));
	}
	
	/**
	 * 用户userId的关注人数
	 * @param userName
	 * @param userId
	 * @return
	 */
	public long getFollowNum(String userName, Long userId)
	{
		String key = userName + "_"+ userId;
		long followNum = jedisAdapter.scard(key);
		return followNum;
	}
	
	/**
	 * 获取用户userId的粉丝
	 * @param userId
	 * @return
	 */
	public Set<User> getFollowUser(Long userId)
	{
		User user = userDao.getUserById(userId);
		String followKey = user.getUsername() + "_" + userId;
		Set<String> fansSet = jedisAdapter.smember(followKey);
		Set<User> fansUser = new HashSet<>();
		for (String fans : fansSet)
		{
			User fan = userDao.getUserById(Long.parseLong(fans));
			fansUser.add(fan);
		}
		return fansUser;
	}
}
