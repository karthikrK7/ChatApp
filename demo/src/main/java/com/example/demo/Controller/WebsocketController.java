package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.UserService;
import com.example.demo.model.MessagingVO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@RestController
public class WebsocketController {
	
	private final SimpMessagingTemplate template;
	
	@Autowired
	UserService userService;

	@Autowired
	WebsocketController(SimpMessagingTemplate template) {
		this.template = template;
	}
	
	@MessageMapping("/send/message")
	public void sendMessage(@Payload MessagingVO message) {
		// {"text":null,"sender_id":0,"receiver_id":0,"chat_id":0,"msg_Type":0,"groupChatId":null,"singleChatId":null}
		userService.sendMsg(message);
		JsonParser jsonParser = new JsonParser();
		JsonElement Elem = jsonParser.parse(userService.getConversation(message.getChat_id()));
		JsonArray JsonArray = Elem.getAsJsonObject().get("Conversation").getAsJsonObject().get("chats")
				.getAsJsonArray();
		JsonElement lastElem = JsonArray.get(JsonArray.size() - 1);
		this.template.convertAndSend("/message", lastElem.toString());
	}
	
	@MessageMapping("/send/typing")
	public void sendMessage(@Payload String typing) {
		this.template.convertAndSend("/typing", typing.toString());
	}
	@MessageMapping("/send/videochatoffer")
	public void videoCalling(@Payload String typing) {
		this.template.convertAndSend("/videochatoffer", typing.toString());
	}
	@MessageMapping("/send/videochatanswer")
	public void videoanswering(@Payload String typing) {
		this.template.convertAndSend("/videochatanswer", typing.toString());
	}
}
