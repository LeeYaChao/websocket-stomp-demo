package com.taiji.websocket.controller;


import com.taiji.websocket.vo.Greeting;
import com.taiji.websocket.vo.HelloMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

	public static Logger logger = LoggerFactory.getLogger(HelloController.class);
	@Autowired
	private SimpUserRegistry userRegistry;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;



	@MessageMapping("/hello")
	public void greeting(HelloMessage message) throws Exception {
		Greeting greeting = new Greeting();
		greeting.setContent(message.getSendMessageBody());
		for (SimpUser user : userRegistry.getUsers()) {
			messagingTemplate.convertAndSendToUser(user.getName(), "/queue/reply", greeting);
		}
	}

	@RequestMapping("/")
	public String index() {
		return "index";
	}


	@RequestMapping(value = "/templateTest")
	public void templateTest() {
		logger.info("当前在线人数:" + userRegistry.getUsers());
		int i = 1;
		for (SimpUser user : userRegistry.getUsers()) {
			logger.info("用户" + i++ + "---" + user.getName());
		}
	}

}
