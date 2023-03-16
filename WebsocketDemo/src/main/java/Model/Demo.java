package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/demo/{id}")
public class Demo {
	private static Map<String,List<Session>> map = new HashMap<String,List<Session>>();

	@OnOpen
	public void handleOpen(Session session,@PathParam("id") String id) {
		System.out.println(session);
		System.out.println(id);
		if(map.containsKey(id)) {
			System.out.println("Room id : " + id);
			List<Session> list = map.get(id);
			if(list.contains(session)) {
				System.out.println("Valid session");
			}
			else {
				list.add(session);
			}
		}
		else {
			List<Session> li = new ArrayList<Session>();
			li.add(session);
			map.put(id, li);
		}
	}
	
	@OnClose
	public void handleClose(Session session) {
		map.forEach((n,m)-> {
			if(m.contains(session)) {
				m.remove(session);
			}
		});
	}
	
	@OnMessage
	public void sendMessage(String message,Session session,@PathParam("id") String id) {
		System.out.println(message);
		List<Session> li = map.get(id);
		for(Session ses : li) {
			try {
				ses.getBasicRemote().sendText(message);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}
}
