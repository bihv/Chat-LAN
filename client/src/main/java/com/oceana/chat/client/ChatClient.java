package com.oceana.chat.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.Panel;
import java.awt.TextArea;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class ChatClient {

	private JFrame frmOceanaChat;
	public static final JList listUserChat = new JList();
	public static JList<HashMap<String, DefaultListModel<String>>> listRoomChat = new JList(); 
	public static JTextPane textArea_Discu = new JTextPane();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatClient window = new ChatClient();
					window.frmOceanaChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatClient() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOceanaChat = new JFrame();
		frmOceanaChat.setIconImage(Toolkit.getDefaultToolkit().getImage("../client/resource/Yahoo-Messenger-icon.png"));
		frmOceanaChat.setTitle("Oceana Chat");
		frmOceanaChat.setBounds(100, 100, 901, 558);
		frmOceanaChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOceanaChat.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(665, 10, 209, 394);
		frmOceanaChat.getContentPane().add(tabbedPane);
		
		Panel panel_1 = new Panel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		
		
		panel_1.add(listRoomChat, BorderLayout.CENTER);
		
		Button buttonNewRoom = new Button("New room");
		buttonNewRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RoomChat chat = new RoomChat();
				chat.main(null);
			}
		});
		panel_1.add(buttonNewRoom, BorderLayout.SOUTH);
		
		tabbedPane.addTab("Chat", null, listUserChat, null);
		
		Panel panel = new Panel();
		panel.setBounds(10, 410, 649, 99);
		frmOceanaChat.getContentPane().add(panel);
		panel.setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(0, 0, 563, 99);
		panel.add(textArea);
		
		Button button_Send = new Button("Send");
		button_Send.setBounds(569, 67, 70, 22);
		panel.add(button_Send);
		
		Button button_SendFile = new Button("Send File");
		button_SendFile.setBounds(569, 39, 70, 22);
		panel.add(button_SendFile);
		
		Button button_SelectIcon = new Button("Select Icon");
		button_SelectIcon.setBounds(569, 10, 70, 22);
		panel.add(button_SelectIcon);
		
		Button button_Disconnect = new Button("Disconnect");
		button_Disconnect.setBounds(675, 410, 199, 99);
		frmOceanaChat.getContentPane().add(button_Disconnect);
		textArea_Discu.setContentType("text/html");
		
		
		textArea_Discu.setBounds(10, 10, 645, 394);
		frmOceanaChat.getContentPane().add(textArea_Discu);
	}
}
