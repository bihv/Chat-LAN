package com.oceana.chat.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;


class Read extends Thread {
	public static String[] userList;
    public void run() {
      String message;
      while(!Thread.currentThread().isInterrupted()){
        try {
          message = HomeClient.input.readLine();
          if(message != null){
            if (message.charAt(0) == '[') {
              message = message.substring(1, message.length()-1);
              ArrayList<String> ListUser = new ArrayList<String>(
                  Arrays.asList(message.split(", "))
                  );
              ChatClient.listUserChat.setListData(new String[] {});
              userList = HomeClient.users;
              userList = new String[ListUser.size()];
              for (int i = 0; i < ListUser.size(); i++) {
            	  
                //appendToPane(jtextListUsers, "@" + user);
            	  userList[i] = "<HTML>@" + ListUser.get(i);
                
              }
              ChatClient.listUserChat.setListData(userList);
            }else{
              appendToPane(ChatClient.textArea_Discu, message);
            }
          }
        }
        catch (IOException ex) {
          System.err.println("Failed to parse incoming message");
        }
      }
    }
    
    // send html to pane
    private void appendToPane(JTextPane tp, String msg){
      HTMLDocument doc = (HTMLDocument)tp.getDocument();
      HTMLEditorKit editorKit = (HTMLEditorKit)tp.getEditorKit();
      try {
        editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
        tp.setCaretPosition(doc.getLength());
      } catch(Exception e){
        e.printStackTrace();
      }
    }
  }
