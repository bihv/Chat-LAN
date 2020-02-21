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
	private List<User> clients;
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

class UserHandler implements Runnable {

	private Server server;
	private User user;

	public UserHandler(Server server, User user) {
		this.server = server;
		this.user = user;
		this.server.broadcastAllUsers();
	}

	public void run() {
		String message;

		// when there is a new message, broadcast to all
		Scanner sc = new Scanner(this.user.getInputStream());
		while (sc.hasNextLine()) {
			message = sc.nextLine();

			// smiley
			message = message.replace(":)",
					"<img src='http://4.bp.blogspot.com/-ZgtYQpXq0Yo/UZEDl_PJLhI/AAAAAAAADnk/2pgkDG-nlGs/s1600/facebook-smiley-face-for-comments.png'>");
			message = message.replace(":D",
					"<img src='http://2.bp.blogspot.com/-OsnLCK0vg6Y/UZD8pZha0NI/AAAAAAAADnY/sViYKsYof-w/s1600/big-smile-emoticon-for-facebook.png'>");
			message = message.replace(":d",
					"<img src='http://2.bp.blogspot.com/-OsnLCK0vg6Y/UZD8pZha0NI/AAAAAAAADnY/sViYKsYof-w/s1600/big-smile-emoticon-for-facebook.png'>");
			message = message.replace(":(",
					"<img src='http://2.bp.blogspot.com/-rnfZUujszZI/UZEFYJ269-I/AAAAAAAADnw/BbB-v_QWo1w/s1600/facebook-frown-emoticon.png'>");
			message = message.replace("-_-",
					"<img src='http://3.bp.blogspot.com/-wn2wPLAukW8/U1vy7Ol5aEI/AAAAAAAAGq0/f7C6-otIDY0/s1600/squinting-emoticon.png'>");
			message = message.replace(";)",
					"<img src='http://1.bp.blogspot.com/-lX5leyrnSb4/Tv5TjIVEKfI/AAAAAAAAAi0/GR6QxObL5kM/s400/wink%2Bemoticon.png'>");
			message = message.replace(":P",
					"<img src='http://4.bp.blogspot.com/-bTF2qiAqvi0/UZCuIO7xbOI/AAAAAAAADnI/GVx0hhhmM40/s1600/facebook-tongue-out-emoticon.png'>");
			message = message.replace(":p",
					"<img src='http://4.bp.blogspot.com/-bTF2qiAqvi0/UZCuIO7xbOI/AAAAAAAADnI/GVx0hhhmM40/s1600/facebook-tongue-out-emoticon.png'>");
			message = message.replace(":o",
					"<img src='http://1.bp.blogspot.com/-MB8OSM9zcmM/TvitChHcRRI/AAAAAAAAAiE/kdA6RbnbzFU/s400/surprised%2Bemoticon.png'>");
			message = message.replace(":O",
					"<img src='http://1.bp.blogspot.com/-MB8OSM9zcmM/TvitChHcRRI/AAAAAAAAAiE/kdA6RbnbzFU/s400/surprised%2Bemoticon.png'>");

			// Gestion des messages private
			if (message.charAt(0) == '@') {
				if (message.contains(" ")) {
					System.out.println("private msg : " + message);
					int firstSpace = message.indexOf(" ");
					String userPrivate = message.substring(1, firstSpace);
					server.sendMessageToUser(message.substring(firstSpace + 1, message.length()), user, userPrivate);
				}

				// Gestion du changement
			} else if (message.charAt(0) == '#') {
				user.changeColor(message);
				// update color for all other users
				this.server.broadcastAllUsers();
			} else {
				// update user list
				server.broadcastMessages(message, user);
			}
		}
		// end of Thread
		server.removeUser(user);
		this.server.broadcastAllUsers();
		sc.close();
	}
}
