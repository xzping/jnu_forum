package com.xzp.forum.async;

public enum EventType {
	COMMENT(0),
	USEFUL(1),
	LOGIN(2);
	
	private int value;
	
	EventType(int value){
		this.value=value;
	}
	
	public int getValue() {
        return value;
    }
}
