package com.hanson.chat.client.UI;

import com.hanson.chat.client.i.IMessage;
import com.hanson.chat.client.i.IServer;
import com.hanson.chat.common.pojo.Message;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hanson on 2017/1/6.
 */
public class ClientUI extends JFrame implements IMessage{

    private MessageBox messagePanel = null;
    private UserList userList = null;
    public ClientUI(){
        super("client");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setLayout(new BorderLayout());

        userList = new UserList();
        messagePanel = new MessageBox();

        JSplitPane centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userList,
                messagePanel);
        add(centerSplit,"Center");

        setVisible(true);
    }
    public void setServer(IServer server){
        messagePanel.setServer(server);
    }

    public static void main(String args[]){
        new ClientUI();
    }

    @Override
    public void addMessage(Message msg) {
         messagePanel.addMessage(msg);
    }

    @Override
    public void addUser(String user) {
        userList.addUser(user);
    }

    @Override
    public void deleteUser(String user) {
        userList.deleteUser(user);
    }

    @Override
    public void connectSuccess(boolean success) {
        messagePanel.connectSuccess(success);
    }

    @Override
    public void setNameSuccess(boolean success) {
        messagePanel.setNameSuccess(success);
    }
}
