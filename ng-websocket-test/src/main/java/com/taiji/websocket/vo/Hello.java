package com.taiji.websocket.vo;

public class Hello {

    private String content;

    public Hello(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public static class HelloMessage {

        public HelloMessage(){

        }

		private String name;

		public String getName() {
			return name;
		}

        public void setName(String name) {
            this.name = name;
        }
    }
}
