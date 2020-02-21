package com.oceana.chat.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


class Read extends Thread {
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
              String[] userList = HomeClient.users;
              userList = new String[ListUser.size()];
              for (int i = 0; i < ListUser.size(); i++) {
            	  
                //appendToPane(jtextListUsers, "@" + user);
            	  userList[i] = "<HTML>@" + ListUser.get(i);
                
              }
              ChatClient.listUserChat.setListData(userList);
            }else{
             // appendToPane(jtextFilDiscu, message);
            }
          }
        }
        catch (IOException ex) {
          System.err.println("Failed to parse incoming message");
        }
      }
    }
  }
