package com.xzp.forum.util;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;

/**
 * jedis工具类
 * 
 * @author xiezhiping
 *
 */
@Service
public class JedisAdapter {
	@Autowired
	StringRedisTemplate redisTemplate;

	/**
	 * get
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * sadd(key, value)往集合中加入一个key-value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public long sadd(String key, String value) {
		// 返回多少个值
		return redisTemplate.opsForSet().add(key, value);
	}

	/**
	 * srem(key, value)从这个集合中移除这个key-value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public long srem(String key, String value) {
		return redisTemplate.opsForSet().remove(key, value);
	}

	/**
	 * sismember(key, value)判断集合中是否有这个key-value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean sismember(String key, String value) {
		return redisTemplate.opsForSet().isMember(key, value);
	}

	/**
	 * scard(key)获取集合成员数量
	 * 
	 * @param key
	 * @return
	 */
	public long scard(String key) {
		return redisTemplate.opsForSet().size(key);
	}

	/**
	 * 获取key的成员内容
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> smember(String key) {
		return redisTemplate.opsForSet().members(key);
	}

	/**
	 * 获取key1和key2的交集
	 * 
	 * @param key1
	 * @param key2
	 * @return
	 */
	public Set<String> sinter(String key1, String key2) {
		return redisTemplate.opsForSet().intersect(key1, key2);
	}

	// public void setex(String key, String value) {
	// // 验证码, 防机器注册，记录上次注册时间，有效期3天
	// Jedis jedis = null;
	// try {
	// jedis = pool.getResource();
	// jedis.setex(key, 10, value);
	// } catch (Exception e) {
	// logger.error("发生异常" + e.getMessage());
	// } finally {
	// if (jedis != null) {
	// jedis.close();
	// }
	// }
	// }

	public long lpush(String key, String value) {
		return redisTemplate.opsForList().leftPush(key, value);
	}

	public List<String> brpop(int timeout, String key) {
		return redisTemplate.opsForList().range(key, 0, -1);
	}

	/**
	 * 保存一个对象，相当于缓存到Redis中
	 * 
	 * @param key
	 * @param obj
	 */
	public void setObject(String key, Object obj) {
		// 保存序列化为JSON串
		set(key, JSON.toJSONString(obj));
	}

	/**
	 * 获取一个对象
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T getObject(String key, Class<T> clazz) {
		String value = get(key);
		if (value != null) {
			return JSON.parseObject(value, clazz);
		}
		return null;
	}

	public boolean zadd(String key, double score, String username) {
		// 返回多少个值
		return redisTemplate.opsForZSet().add(key, username, score);
	}

	public Set<String> zrevrange(String key, long start, long end) {
		// 返回多少个值
		return redisTemplate.opsForZSet().reverseRange(key, start, end);
	}
}
