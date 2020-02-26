package com.oceana.chat.server_client;

import java.util.HashMap;

public class MessageUser {

	public static HashMap<String, HashMap<String, String>> message_User = new HashMap<String, HashMap<String, String>>();

	public static HashMap<String, HashMap<String, String>> getMessage_User() {
		return message_User;
	}

	public static void setMessage_User(HashMap<String, HashMap<String, String>> message_User) {
		MessageUser.message_User = message_User;
	}
	
	
}
