package sockettest.model;

import java.util.UUID;

import sockettest.controller.ChatEntpoint;

public class User {
	private String uid;
	private int id;
	private String uName;
	private ChatEntpoint ce;
	
	public User() {
		uid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public ChatEntpoint getCe() {
		return ce;
	}

	public void setCe(ChatEntpoint ce) {
		this.ce = ce;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"uid\":\"");
		builder.append(uid);
		builder.append("\", \"id\":\"");
		builder.append(id);
		builder.append("\", \"uName\":\"");
		builder.append(uName);
		builder.append("\"}");
		return builder.toString();
	}
}
