package com.xzp.forum.util;

import java.util.Date;

public class TopicSortUtil {
	public static Double topicSort(Integer answerCount, Date createdDate) {
		Double value = Math.pow(answerCount, 0.5)
				/ Math.round(Math.pow((System.currentTimeMillis() - createdDate.getTime()), 0.5) / (Math.pow(3.6, 5)));
		return value;
	}
}
