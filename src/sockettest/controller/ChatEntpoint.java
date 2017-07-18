package sockettest.controller;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import sockettest.model.User;

@ServerEndpoint("/chat")
public class ChatEntpoint {
	private static HashMap<String, User> users = new HashMap<>();
	private Session s;
	private static String uid;

	@OnOpen
	public void start(Session s) {
//		http
		String str = s.getQueryString();
		System.out.println("str={" + str + "}");
		User u = new User();
		u.setuName(str);
//		User u = (User) ss.getAttribute("user");
		this.s = s;
		uid = u.getUid();
		u.setCe(this);
		users.put(uid, u);
		String msg = u.getUid() + "加入";
		System.out.println(u + "jiaru");
		broadcast(msg);
	}

	@OnClose
	public void end() {
		String msg = users.get(uid).getUid() + "退出";
		broadcast(msg);
		users.remove(uid);
	}   

	@OnMessage
	public void mess(String mess) {
		broadcast(users.get(uid).getUid() + ":" + filter(mess));
	}

	@OnError
	public void onError(Throwable t) throws Throwable {
		System.out.println("WebSocket服务端错误 " + t);
	}

	private void broadcast(String msg) {
		// Iterator i = users.entrySet().iterator();
		for (Entry<String, User> u : users.entrySet()) {
			User user = u.getValue();
			
			ChatEntpoint client = user.getCe();
			try {

				synchronized (client) {
					System.out.println("发送给:" );
					System.out.println(user);
					client.s.getBasicRemote().sendText(msg);
				}
			} catch (Exception e) {
				System.out.println("出错");

				broadcast(user.getUid() + " 断开连接");
				users.remove(uid);
			}
		}
	}

	// 定义一个工具方法，用于对字符串中的HTML字符标签进行转义
	private static String filter(String message) {
		if (message == null)
			return null;
		char content[] = new char[message.length()];
		message.getChars(0, message.length(), content, 0);
		StringBuilder result = new StringBuilder(content.length + 50);
		for (int i = 0; i < content.length; i++) {
			// 控制对尖括号等特殊字符进行转义
			switch (content[i]) {
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '"':
				result.append("&quot;");
				break;
			default:
				result.append(content[i]);
			}
		}
		return (result.toString());
	}
}
