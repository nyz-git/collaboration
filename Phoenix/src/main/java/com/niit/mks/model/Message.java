package com.niit.mks.model;

public class Message {
	private int id;
	private String message;
	private String username;
	
	public Message()
	{
		
	}

	public Message(int id, String message, String username) {
		this.id = id;
		this.message = message;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}