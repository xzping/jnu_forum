package com.xzp.forum.async;

import java.util.List;

public interface EventHandler {
	//事件处理
    void doHandle(EventModel model);
    //关注哪一些事件
    List<EventType> getSupportEventTypes();
}
