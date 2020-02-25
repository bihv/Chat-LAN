package com.oceana.chat.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.oceana.chat.server_client.*;

public class Server {

	private static int port;
	public static List<User> clients;
	private ServerSocket server;

	public static void main(String[] args) throws IOException {
		System.out.println("Please enter your port: ");
		InputStream stream = System.in;
		Scanner scanner = new Scanner(stream);
		
		port = scanner.nextInt();
		scanner.close();
		new Server(port).run();
	}

	public Server(int port1) {
		port = port1;
		this.clients = new ArrayList<User>();
	}

	public void run() throws IOException {
		server = new ServerSocket(port) {
			protected void finalize() throws IOException {
				this.close();
			}
		};
		System.out.println("Port " + port + " is now open.");

		while (true) {
			// accepts a new client
			Socket client = server.accept();

			// get nickname of newUser
			String nickname = (new Scanner(client.getInputStream())).nextLine();
			nickname = nickname.replace(",", ""); // ',' use for serialisation
			nickname = nickname.replace(" ", "_");
			System.out.println(
					"New Client: \"" + nickname + "\"\n\t     Host:" + client.getInetAddress().getHostAddress());

			// create new User
			User newUser = new User(client, nickname);

			// add newUser message to list
			this.clients.add(newUser);

			// Welcome msg
			newUser.getOutStream()
					.println("<img src='https://www.kizoa.fr/img/e8nZC.gif' height='42' width='42'>" + "<b>Welcome</b> "
							+ newUser.toString()
							+ "<img src='https://www.kizoa.fr/img/e8nZC.gif' height='42' width='42'>");

			// create a new thread for newUser incoming messages handling
			new Thread(new UserHandler(this, newUser)).start();
		}
	}

	// delete a user from the list
	public void removeUser(User user) {
		this.clients.remove(user);
	}

	// send incoming msg to all Users
	public void broadcastMessages(String msg, User userSender) {
		for (User client : this.clients) {
			client.getOutStream().println(userSender.toString() + "<span>: " + msg + "</span>");
		}
	}

	// send list of clients to all Users
	public void broadcastAllUsers() {
		for (User client : this.clients) {
			client.getOutStream().println(this.clients);
		}
	}

	// send message to a User (String)
	public void sendMessageToUser(String msg, User userSender, String user) {
		boolean find = false;
		for (User client : this.clients) {
			if (client.getNickname().equals(user) && client != userSender) {
				find = true;
				userSender.getOutStream().println(userSender.toString() + " -> " + client.toString() + ": " + msg);
				client.getOutStream()
						.println("(<b>Private</b>)" + userSender.toString() + "<span>: " + msg + "</span>");
			}
		}
		if (!find) {
			userSender.getOutStream().println(userSender.toString() + " -> (<b>no one!</b>): " + msg);
		}
	}
}

