package com.biz.tour.controller.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.biz.tour.domain.chat.MessageVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

/*
 * 어떤 동작이 수행되는 과정에서 자동으로 호출되는 method들
 * lifecycle method를 잘 구현하므로써 별도 어떤 동작에 해당하는 보잡한 코드를 만들기 않아도 충분한 효과를 발휘할수 있다.
 * 
 * after connection established
 * 서버와 클라이언트가 서로 socket으로 연결되었을때 호출되는 method
 * session을 별도로 저장하거나 하는 일들을 코딩.
 * 
 * handletextmsg
 * 클라이언트에서 메시지를 보내면 메시지를 수신하고 연산코드를 수행후 결과를 다시 클라에게 보내는 코딩
 * nodejs등 다른 서버에서는 메시지별로 method를 독립적으로 작성 하기도 함.
 * 
 * afterconnection closed
 * 클라 연결이 정상, 비정상으러 종료 되었을때.
 */
@Slf4j
@Component
public class ChatSocketComponent extends TextWebSocketHandler{
	List<WebSocketSession> sessionList;
	Map<String,MessageVO> messageMap;
	
	public ChatSocketComponent() {
		sessionList=new ArrayList<WebSocketSession>();
		messageMap=new HashMap<String, MessageVO>();
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionEstablished(session);
		/*
		 * 최초 어떤 사용자가 접속하면 사용자에대한 메시지 정보를 담을 변수 초기화
		 * 
		 * 세션 id를 key값으로 하는 비어있는 사용자 정보 map에 저장
		 */
		sessionList.add(session);
		MessageVO mVO=new MessageVO();
		messageMap.put(session.getId(), mVO);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		super.handleTextMessage(session, message);
		Gson gson=new Gson();
		// jackson bind의 클래스인 bjectMapper를 사용하여 VO 클래스를 Json형 문자열로 바로 변환시키기.
		ObjectMapper objMapper=new ObjectMapper();
		Map<String,String> map=new HashMap<String, String>();
		
		//여기서 부터는 임의의 command protocol을 선언
		//전달받은 메시지에 command가 포함되어 있는가를 구분.
		String user[]=message.getPayload().split(":");
		
		//userName:값 || getUserList:값 식의 메시지가 오면
		if(user.length>1) {
			// command가 USERNAME이면
			// 채팅 어플에 접속했을때 최초 사용자 이름을 입력하면
			// 사용자 이름과 session을 정보에 저장후 다시 클라이언트에게 알려주는 부분.
			if(user[0].equalsIgnoreCase("USERNAME")) {
				MessageVO mVO=messageMap.get(session.getId());
				mVO.setUserName(user[1]);
				map.put("msg", "userName");
				map.put("userName", mVO.getUserName());
				ObjectMapper obj=new ObjectMapper();
				
				String userName=objMapper.writeValueAsString(map);
				this.sendMeMessage(session, userName);
				return;
			}
			//command가 GetUserList 이면
			//현재 접속자 정보를 모두 클라이언트로 보내기
			else if(user[0].equalsIgnoreCase("GETUSERLIST")) {
				//여러개의 json 리스트. { { 1 },{ 2 } }. Map을 Json처럼 쓸수 있음
				String userList=objMapper.writeValueAsString(messageMap);
				map.put("msg","userList");
				// 상위 json 밑에 json list를 추가
				// {msg:userlist, userlist:{ {1},{2} } }
				map.put("userList", userList);
				String userListMap=objMapper.writeValueAsString(map);
				this.sendAllMessage(session, userListMap);
				return;
			}
		}
		//채팅이 진행되는 과정에서 메시지 전파
		MessageVO messageVO=gson.fromJson(message.getPayload(), MessageVO.class);
		//String sendMessage=String.format("%s 로 부터 : %s", messageVO.getUserName(),messageVO.getMessage());
		//TextMessage sendTextMessage=new TextMessage(sendMessage);
		
		String jsonTextMessage=objMapper.writeValueAsString(messageVO);
		
		if(messageVO.getToUser().equalsIgnoreCase("ALL")) {
			//나를 제외한 전체에게 메시지 보내기
			this.sendNotMeMessage(session, jsonTextMessage);
		}
		else {
			//전체가 아니면 전송된 session id값을 sessionList에서 조회하여 일치하는 값이 있으면
			//해당 접속자에게만 메시지 보내기
			for(WebSocketSession ws:sessionList) {
				if(ws.getId().equals(messageVO.getToUser())) {
					sendMeMessage(ws, jsonTextMessage);
					break;
				}
			}
		}
		
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionClosed(session, status);
		sessionList.remove(session);
		messageMap.remove(session.getId());
	}
	
	//요청한 접속자에게만 메시지 보내기
	private void sendMeMessage(WebSocketSession session,String textMessage) throws IOException {
		TextMessage sendMessage=new TextMessage(textMessage);
		session.sendMessage(sendMessage);
	}
	
	//무조건 전체 접속자에게 메시지 보내기
	private void sendAllMessage(WebSocketSession session,String textMessage) throws IOException {
		TextMessage sendMessage=new TextMessage(textMessage);
		for(WebSocketSession ws:sessionList) {
			ws.sendMessage(sendMessage);
		}
	}
	
	//현재 접속자를 제외한 나머지 접속자에게 메시지 보내기
	private void sendNotMeMessage(WebSocketSession session,String textMessage) throws IOException {
		TextMessage sendMessage=new TextMessage(textMessage);
		for(WebSocketSession ws:sessionList) {
			//자신이 보낸 메시지는 자신에게 보낼 필요는 없다.
			if(!ws.getId().equals(session.getId())) {
				ws.sendMessage(sendMessage);
			}
		}
	}
}
