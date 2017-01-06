package com.hanson.chat.client.UI;

import com.hanson.chat.client.i.IServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by hanson on 2017/1/6.
 */
public class MessageBox extends JPanel {

    private TextArea msgBox = null;
    private TextField msgText = null;
    private TextField serverText = null;
    private TextField nameText = null;

    private Button sendButton = null;
    private Button connectButton = null;
    private Button setNameButton = null;
    private IServer server = null;
    private String name = null;


    public MessageBox(){
        super();
        setLayout(new FlowLayout());

        msgBox = new TextArea();
        add(msgBox);

        Panel sendMsgPanel = new Panel();
        sendMsgPanel.setLayout(new FlowLayout());
        msgText = new TextField(50);
        sendMsgPanel.add(msgText);

        sendButton = new Button("send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = msgText.getText();
                try {
                    server.sendMessage(msg,name);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        sendMsgPanel.add(sendButton);

        Panel connectPanel = new Panel();
        connectPanel.setLayout(new FlowLayout());
        serverText = new TextField(50);
        connectPanel.add(serverText);
        connectButton = new Button("connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = serverText.getText().trim();
                if(text.matches("\\d{2,3}([.]\\d{1,3}){3}:\\d{2,5}")){
                    String[] address = text.split(":");
                    String ip = address[0];
                    int port = Integer.parseInt(address[1]);
                    server.connectServer(ip,port);
                }else{
                    serverText.setText("please enter the correct server address..");
                }
            }
        });
        connectPanel.add(connectButton);

        Panel namePanel = new Panel();
        namePanel.setLayout(new FlowLayout());
        nameText = new TextField(50);
        namePanel.add(nameText);

        setNameButton = new Button("set name");
        setNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText().trim();
                server.setName(name);
                new Thread(server).start();
            }
        });
        namePanel.add(setNameButton);

        add(sendMsgPanel);
        add(namePanel);
        add(connectPanel);
        setVisible(true);
    }

    public void setServer(IServer server){
        this.server = server;
        server.setMsgBox(msgBox);
    }

}
