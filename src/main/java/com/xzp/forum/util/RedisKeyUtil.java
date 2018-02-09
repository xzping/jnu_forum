package com.xzp.forum.util;

public class RedisKeyUtil {
	private static String SPLIT = ":";
	private static String BIZ_USEFUL = "USEFUL";
	private static String BIZ_UNUSEFUL = "UNUSEFUL";
	private static String BIZ_COMMENT = "COMMENT";
	private static String BIZ_EVENT = "EVENT";

	// 返回事件的队列key
	public static String getEventQueueKey() {
		return BIZ_EVENT;
	}

	/**
     * entityId和entityType的组合可以表示话题是否有用（可复用）
     * 
     * @param entityId
     * @param entityType
     * @return
     */
	public static String getUsefulKey(int entityId, int entityType) {
		return BIZ_USEFUL + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
	}
	
	public static String getUnusefulKey(int entityId, int entityType) {
		return BIZ_UNUSEFUL + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
	}
}
