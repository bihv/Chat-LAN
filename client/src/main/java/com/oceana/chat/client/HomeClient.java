package com.oceana.chat.client;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class HomeClient {

	private JFrame frame;
	private JTextField textFieldIP;
	private JTextField textFieldPort;
	public static JTextField textFieldNickName;
	
		private String oldMsg = "";
	  private Thread read;
	  private String serverName;
	  private int PORT;
	  private String name;
	  public static BufferedReader input;
	  public static  PrintWriter output;
	  Socket server;
	  public static String[] users;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeClient window = new HomeClient();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HomeClient() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 433, 155);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textFieldIP = new JTextField();
		textFieldIP.setBounds(10, 23, 115, 34);
		frame.getContentPane().add(textFieldIP);
		textFieldIP.setColumns(10);
		
		textFieldPort = new JTextField();
		textFieldPort.setColumns(10);
		textFieldPort.setBounds(146, 23, 115, 34);
		frame.getContentPane().add(textFieldPort);
		
		textFieldNickName = new JTextField();
		textFieldNickName.setColumns(10);
		textFieldNickName.setBounds(284, 23, 115, 34);
		frame.getContentPane().add(textFieldNickName);
		
		JButton btnConnect = new JButton("Connect");
		ChatClient.textArea_Discu.setContentType("text/html");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
			          name = textFieldNickName.getText();
			          String port = textFieldPort.getText();
			          serverName = textFieldIP.getText();
			          PORT = Integer.parseInt(port);

			          appendToPane(ChatClient.textArea_Discu, "<span>Connecting to " + serverName + " on port " + PORT + "...</span>");
			          server = new Socket(serverName, PORT);

			          appendToPane(ChatClient.textArea_Discu, "<span>Connected to " +
			              server.getRemoteSocketAddress()+"</span>");

			          input = new BufferedReader(new InputStreamReader(server.getInputStream()));
			          ChatClient.output = new PrintWriter(server.getOutputStream(), true);

			          // send nickname to server
			          ChatClient.output.println(name);

			          // create new Read Thread
			          read = new Read();
			          read.start();
			        } catch (Exception ex) {
			          appendToPane(ChatClient.textArea_Discu, "<span>Could not connect to Server</span>");
			          JOptionPane.showMessageDialog(frame, ex.getMessage());
			        }
				
				ChatClient chatClient = new ChatClient();
				chatClient.main(null);
				frame.dispose();
			}
		});
		btnConnect.setBounds(10, 68, 389, 37);
		frame.getContentPane().add(btnConnect);
	}
	
	private void appendToPane(JTextPane tp, String msg){
	    HTMLDocument doc = (HTMLDocument)tp.getDocument();
	    HTMLEditorKit editorKit = (HTMLEditorKit)tp.getEditorKit();
	    ChatClient.listUserChat.add(textFieldNickName);
	    try {
	      editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
	      tp.setCaretPosition(doc.getLength());
	    } catch(Exception e){
	      e.printStackTrace();
	    }
	  }
}
