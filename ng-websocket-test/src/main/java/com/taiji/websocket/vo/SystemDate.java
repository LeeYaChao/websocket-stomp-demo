package com.taiji.websocket.vo;

import lombok.Getter;
import lombok.Setter;

public class SystemDate {

	private String content;

	public SystemDate(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public static class Message {
		@Getter
		@Setter
		private String body;
	}
}
