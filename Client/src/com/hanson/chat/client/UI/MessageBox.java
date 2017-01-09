package com.hanson.chat.client.UI;

import com.hanson.chat.client.i.IServer;
import com.hanson.chat.common.pojo.Message;
import com.hanson.chat.common.pojo.MsgBody;
import com.hanson.chat.common.pojo.MsgHeader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by hanson on 2017/1/6.
 */
public class MessageBox extends JPanel{

    private JTextArea msgBox = null;
    private JTextArea msgText = null;
    private JTextField serverText = null;
    private JTextField nameText = null;

    private JButton sendButton = null;
    private JButton connectButton = null;
    private JButton setNameButton = null;
    private IServer server = null;
    private String name = null;


    public MessageBox(){
        super();
        setLayout(new FlowLayout());

        msgBox = new JTextArea(20,40);
        msgBox.setEditable(false);
        add(new JScrollPane(msgBox));
//        add(msgBox);

        JPanel sendMsgPanel = new JPanel();
        sendMsgPanel.setLayout(new FlowLayout());
        msgText = new JTextArea(5,33);
        sendMsgPanel.add(new JScrollPane(msgText));

        sendButton = new JButton("send");
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
        serverText = new JTextField("127.0.0.1:8087",16);
        connectPanel.add(serverText);
        connectButton = new JButton("connect");
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
        nameText = new JTextField(16);
        namePanel.add(nameText);

        setNameButton = new JButton("set name");
        setNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = nameText.getText().trim();
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
    }
    public void addMessage(Message message){
        MsgHeader header = message.getHeader();
        MsgBody body = message.getBody();

        StringBuffer msgShow = new StringBuffer();
        msgShow.append(header.getSender());
        msgShow.append(":");
        msgShow.append(body.getStrMsg());
        msgShow.append("\n");
        msgBox.append(msgShow.toString());
    }
    public void connectSuccess(boolean success){
        if(success){
            msgBox.append("connect server success....\n");
        }else{
            msgBox.append("connect server failed....\n");
        }
    }
    public void setNameSuccess(boolean success){
        if(success){
            msgBox.append("set name succees,now you can chat\n");
        }else{
            msgBox.append("set name failed,name already use\n");
        }

    }


}
