package com.xzp.forum.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xzp.forum.util.JedisAdapter;
import com.xzp.forum.util.RedisKeyUtil;

@Service
public class EventProducer {
	
	//通过redis来实现事件的队列
	@Autowired
	JedisAdapter jedisAdapter;
	
	//把事件放到队列里面
    public boolean fireEvent(EventModel model) {
        try {
            String json = JSONObject.toJSONString(model);
            String key = RedisKeyUtil.getEventQueueKey();
            //把事件信息放到队列中
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
