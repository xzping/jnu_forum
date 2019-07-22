package com.xzp.forum.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//import redis.clients.jedis.BinaryClient;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.Tuple;

/**
 * demo工具类
 * 
 * @author xiezhiping
 *
 */
public class DemoUtil {
	public static void print(int index,Object obj) {
		System.out.println(String.format("%d,%s", index,obj.toString()));
	}
	
//	public static void main(String[] args) {
//		Jedis jedis=new Jedis();
//		jedis.flushAll();
//		
//		//String操作
//		jedis.set("hello", "world");
//		print(1,jedis.get("hello"));
//		jedis.set("pv", "100");
//		jedis.incr("pv");
//		print(2,jedis.get("pv"));
//		jedis.incrBy("pv", 5);
//		print(3,jedis.get("pv"));
//		
//		//list列表操作
//		String listName="listA";
//		for(int i=0;i<10;i++) {
//			jedis.lpush(listName, "a"+String.valueOf(i));
//		}
//		print(4, jedis.lrange(listName, 0, 4));
//		print(5, jedis.llen(listName));
//		print(6, jedis.lpop(listName));
//		print(7, jedis.llen(listName));
//		print(8, jedis.lindex(listName, 4));
//		print(9, jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "a4", "xx"));
//		print(10, jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE, "a4", "yy"));
//		print(11, jedis.lrange(listName, 0, 12));
//		
//		//Hash操作
//		String userKey="userXX";
//		jedis.hset(userKey, "name", "Jim");
//		jedis.hset(userKey, "age", "20");
//		jedis.hset(userKey, "phone", "15818181818");
//		print(12, jedis.hget(userKey, "name"));
//		print(13, jedis.hgetAll(userKey));
//		jedis.hdel(userKey, "phone");
//		print(14, jedis.hgetAll(userKey));
//		print(15,jedis.hkeys(userKey));
//		print(16,jedis.hvals(userKey));
//		print(17,jedis.hexists(userKey, "email"));
//		print(18,jedis.hexists(userKey, "name"));
//		jedis.hsetnx(userKey, "school", "jnu");
//		jedis.hsetnx(userKey, "name", "xzp");
//		print(19, jedis.hgetAll(userKey));
//		
//		//set操作
//		String likeKey1="likeKey1";
//		String likeKey2="likeKey2";
//		for(int i=0;i<10;i++) {
//			jedis.sadd(likeKey1, String.valueOf(i));
//			jedis.sadd(likeKey2, String.valueOf(2*i));
//		}
//		print(20, jedis.smembers(likeKey1));
//		print(21, jedis.smembers(likeKey2));
//		print(22, jedis.sinter(likeKey1,likeKey2));
//		print(23, jedis.sunion(likeKey1,likeKey2));
//		print(24, jedis.sdiff(likeKey1,likeKey2));
//		print(25, jedis.sismember(likeKey1, "5"));
//		jedis.srem(likeKey1, "5");
//		print(26, jedis.smembers(likeKey1));
//		print(27, jedis.sismember(likeKey1, "5"));
//		print(28, jedis.scard(likeKey1));
//		jedis.smove(likeKey2, likeKey1, "14");
//		print(29, jedis.scard(likeKey1));
//		print(30, jedis.smembers(likeKey1));
//		print(30, jedis.smembers(likeKey2));
//		
//		// Sorted Set优先队列
//		String rankKey="rankKey";
//		//jedis.zadd(rankKey, score, username);
//		jedis.zadd(rankKey, 15, "Jim");
//		jedis.zadd(rankKey, 13, "Tom");
//		jedis.zadd(rankKey, 20, "Cat");
//		jedis.zadd(rankKey, 10, "Jack");
//		jedis.zadd(rankKey, 9, "Jerry");
//		jedis.zadd(rankKey, 25, "Lorry");
//		jedis.zadd(rankKey, 22, "Terry");
//		print(31, jedis.zcard(rankKey));
//		print(32, jedis.zcount(rankKey, 16, 20));
//		print(33, jedis.zscore(rankKey, "Tom"));
//		jedis.zincrby(rankKey, 2, "Jim");
//		print(34, jedis.zscore(rankKey, "Jim"));
//		//排名
//		print(35, jedis.zrange(rankKey, 0, 3));//从小到大
//		print(36, jedis.zrevrange(rankKey, 0, 3));//从大到小
//		for(Tuple tuple : jedis.zrangeByScoreWithScores(rankKey, "0", "100")) {
//			print(37, tuple.getElement()+":"+tuple.getScore());
//		}
//		print(38, jedis.zrank(rankKey, "Cat"));
//		print(38, jedis.zrevrank(rankKey, "Cat"));
//		
//		//Jedis连接池
//		JedisPool pool=new JedisPool();
//		for(int i=0;i<100;i++) {
//			Jedis j=pool.getResource();
//			j.get("a");
//			System.out.println("POOL"+i);
//			j.close();//此处必须将使用后的Jedis资源关闭释放回去，否则资源会被耗尽
//		}
//	}
	
//	public static void main(String[] args) {
//		DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String date=sdf.format(new Date());
//		long currentTime=new Date().getTime();
//		System.out.println(date);
//		System.out.println(System.currentTimeMillis()-currentTime);
//	}
}
