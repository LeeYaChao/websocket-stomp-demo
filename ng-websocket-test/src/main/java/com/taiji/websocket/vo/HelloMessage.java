package com.taiji.websocket.vo;

public class HelloMessage {

	private String toUser;

	private String name;

	private String sendMessageBody;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public void setSendMessageBody(String sendMessageBody) {
		this.sendMessageBody = sendMessageBody;
	}

	public String getSendMessageBody() {
		return sendMessageBody;
	}
}
