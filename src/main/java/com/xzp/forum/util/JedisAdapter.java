package com.xzp.forum.util;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

/**
 * jedis工具类
 * 
 * @author xiezhiping
 *
 */
@Service
public class JedisAdapter implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
	private JedisPool pool = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		pool = new JedisPool("localhost", 6379);
	}

	/**
	 * get
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.get(key);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * sadd(key, value)往集合中加入一个key-value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public long sadd(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			// 返回多少个值
			return jedis.sadd(key, value);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return 0;
		} finally {
			// redis连接池要及时的关闭，否则连接池的redis会被用光
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * srem(key, value)从这个集合中移除这个key-value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public long srem(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.srem(key, value);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return 0;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * sismember(key, value)判断集合中是否有这个key-value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean sismember(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sismember(key, value);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return false;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * scard(key)获取集合成员数量
	 * 
	 * @param key
	 * @return
	 */
	public long scard(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.scard(key);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return 0;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	/**
	 * 获取key的成员内容
	 * @param key
	 * @return
	 */
	public Set<String> smember(String key)
	{
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.smembers(key);
		} catch (Exception e) {
			logger.error("exception,e:{}",e);
			return null;
		} finally {
			if (jedis != null){
				jedis.close();
			}
		}
		
	}
	
	/**
	 * 获取key1和key2的交集
	 * @param key1
	 * @param key2
	 * @return
	 */
	public Set<String> sinter(String key1, String key2) {
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.sinter(key1, key2);
		} catch (Exception e) {
			logger.error("exception,e:{}",e);
			return null;
		} finally {
			if (jedis != null){
				jedis.close();
			}
		}
	}

	public void setex(String key, String value) {
		// 验证码, 防机器注册，记录上次注册时间，有效期3天
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.setex(key, 10, value);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long lpush(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lpush(key, value);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return 0;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public List<String> brpop(int timeout, String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.brpop(timeout, key);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
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

	public long zadd(String key, double score, String username) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			// 返回多少个值
			return jedis.zadd(key, score, username);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return 0;
		} finally {
			// redis连接池要及时的关闭，否则连接池的redis会被用光
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public Set<String> zrevrange(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			// 返回多少个值
			return jedis.zrevrange(key, start, end);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
			return null;
		} finally {
			// redis连接池要及时的关闭，否则连接池的redis会被用光
			if (jedis != null) {
				jedis.close();
			}
		}
	}
}
