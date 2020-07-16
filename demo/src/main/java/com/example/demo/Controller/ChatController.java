package com.example.demo.Controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//import com.example.demo.Constants.ScreenShare;
import com.example.demo.Service.UserService;
import com.example.demo.model.Constants;
import com.example.demo.model.FileUpload;
import com.example.demo.model.GroupMaster;
import com.example.demo.model.MessagingVO;
import com.example.demo.model.User;
import com.example.demo.model.chatListDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("webapi")
public class ChatController {

	private final SimpMessagingTemplate template;

	@Autowired
	UserService userService;

	@Autowired
	ChatController(SimpMessagingTemplate template) {
		this.template = template;
	}

	@MessageMapping("/send/message")
	public void sendMessage(@Payload MessagingVO message) {
		this.template.convertAndSend("/message", message);
		// {"text":null,"sender_id":0,"receiver_id":0,"chat_id":0,"msg_Type":0,"groupChatId":null,"singleChatId":null}

		/*
		 * MessagingVO messageVo = new MessagingVO(); try { messageVo = new
		 * ObjectMapper().readValue(message, MessagingVO.class); } catch
		 * (JsonParseException e) { e.printStackTrace(); } catch (JsonMappingException
		 * e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
		 */
		userService.sendMsg(message);
	}

	@RequestMapping(value = "/send/message", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Constants sendMsg(@RequestParam(name = "files", required = false) MultipartFile[] files,
			@RequestParam("content") String content) throws FileNotFoundException {
		System.out.println(content);
		this.template.convertAndSend("/message", content);
		MessagingVO messageVo = new MessagingVO();
		try {
			messageVo = new ObjectMapper().readValue(content, MessagingVO.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userService.sendMsg(messageVo);// , files
	}

	@RequestMapping(value = "/getparticipants", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getParticipants() {
		return userService.getParticipants();
	}

	// input: {"grpId":0,"groupName":null,"participants":[{"uid":0},{"uid":0]}
	@RequestMapping(value = "/createGroup", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String createGroup(@RequestBody GroupMaster grpmast) {
		return userService.createGroup(grpmast);

	}

	@RequestMapping(value = "/getChatList", method = RequestMethod.GET)
	@ResponseBody
	public List<chatListDTO> getChatList() {
		return userService.getChatList();
	}

	@RequestMapping(value = "/deleteGroup", method = RequestMethod.DELETE)
	@ResponseBody
	public Constants deleteChat(@RequestParam("chatId") int chatId) {

		return userService.deleteChat(chatId);
	}

	@RequestMapping(value = "/getConversation", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String getConversation(@RequestParam("chatId") int chatId) {
		return userService.getConversation(chatId);
	}

	@RequestMapping(value = "/checkOut", method = RequestMethod.POST)
	public ResponseEntity<byte[]> downloadFile(@RequestParam("objectId") String objectId) throws IOException {
		FileUpload file = userService.downloadFile(objectId);

		return ResponseEntity.ok().contentLength(file.getFileContent().length).header(HttpHeaders.CONTENT_TYPE)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
				.body(file.getFileContent());
	}

	@RequestMapping(value = "/screenSharing", method = RequestMethod.GET)
	public ResponseEntity<byte[]> screenSharing() throws IOException {

		System.out.println("Welcome to Screen Sharer");
		System.out.println("To set up server, type server [port]");
		System.out.println("To Set up client, type client [server-addr] [port]");

		// new ScreenShare().interactive();
		// input stream
		InputStream in = new ByteArrayInputStream("Techie Delight".getBytes(StandardCharsets.UTF_8));
		byte[] bytes = toByteArray(in);
		return ResponseEntity.ok().contentLength(bytes.length).header(HttpHeaders.CONTENT_TYPE)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=video").body(bytes);
	}

	public static byte[] toByteArray(InputStream in) throws IOException {

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len;

		// read bytes from the input stream and store them in buffer
		while ((len = in.read(buffer)) != -1) {
			// write bytes from the buffer into output stream
			os.write(buffer, 0, len);
		}

		return os.toByteArray();
	}

	public static void main(String[] args) {
		MessagingVO messageVo = new MessagingVO();
		try {
			System.out.println(new ObjectMapper().writeValueAsString(messageVo));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
