package com.taiji.websocket.job;

import com.taiji.websocket.vo.SystemDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by scl on 2016/3/2.
 */
@Component
public class SystemDateJob {
	public static Logger logger = LoggerFactory.getLogger(SystemDateJob.class);

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String TOPIC_SYSTEM_DATE = "/topic/system-date";
	private static final String TOPIC_SYSTEM_TEST = "/queue/system-test";

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private SimpUserRegistry userRegistry;


	public void systemDate() {
		try {
			SystemDate.Message message = new SystemDate.Message();
			message.setBody(sdf.format(new Date()));
			messagingTemplate.convertAndSend(TOPIC_SYSTEM_DATE, message);
		} catch (Exception e) {
			logger.error("system date send exception" + e.getMessage(), e);
		}
	}

	public void systemTest() {
		try {
//			Set<SimpUser> users = userRegistry.getUsers();
		    SystemDate.Message message = new SystemDate.Message();
			message.setBody(sdf.format(new Date()) + " Test1234");
//			String sessionId = WebsocketClientDataCache.get("K01-C01-C");
//			if(sessionId != null)
//			{
//				SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
//				accessor.setHeader(SimpMessageHeaderAccessor.SESSION_ID_HEADER, sessionId);
//
//				messagingTemplate.convertAndSendToUser("clientId", TOPIC_SYSTEM_TEST, message, accessor.getMessageHeaders());
//			}

			SimpUser user = userRegistry.getUser("1234");

			if(user != null)
			{
//				SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
//				accessor.setUser(new User("clientId"));
//				accessor.setSessionId("clientId");
				messagingTemplate.convertAndSendToUser("1234", TOPIC_SYSTEM_TEST, message);
			}
		} catch (Exception e) {
			logger.error("system date send exception" + e.getMessage(), e);
		}
	}
}
