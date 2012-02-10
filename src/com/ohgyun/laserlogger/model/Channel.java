package com.ohgyun.laserlogger.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ohgyun.laserlogger.pusher.Pusher;

/**
 * 채널을 검증하고, 유효한 채널일 경우 이벤트를 보낸다.
 * - 뷰어 채널: xxxx@viewer
 * - 로거 채널: xxxx@logger
 * - 이벤트명: log_event
 */
public class Channel {
	
	private static String VALID_CHANNEL = "test_channel social_comment";
	
	private static String SUFFIX_VIEWER = "@viewer";
	
	private static String SUFFIX_LOGGER = "@logger";
	
	private static String EVENT_NAME = "log_event";
	
	private String channel;
	
	public Channel(String channel) {
		this.channel = channel;
	}

	public boolean trigger(String data) {
//		System.out.printf("channel:%s, data:%s\n", this.channel, data);
		if (data != null && isValid()) {
			Pusher.triggerPush(this.channel, EVENT_NAME, data);
			return true;
		}
		return false;
	}
	
	private boolean isValid() {
		if (channel == null) {
			return false;
		}
		
		if (channel.indexOf(SUFFIX_VIEWER) == -1
				&& channel.indexOf(SUFFIX_LOGGER) == -1) {
			return false;
		}
		
		String originChannel = channel.substring(0, channel.indexOf("@"));
		Pattern p = Pattern.compile("\\b" + originChannel + "\\b");
		Matcher m = p.matcher(VALID_CHANNEL);
		return m.find();
	}
}
