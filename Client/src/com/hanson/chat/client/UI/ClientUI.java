package com.hanson.chat.client.UI;

import com.hanson.chat.client.i.IServer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hanson on 2017/1/6.
 */
public class ClientUI extends JFrame{

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
}
