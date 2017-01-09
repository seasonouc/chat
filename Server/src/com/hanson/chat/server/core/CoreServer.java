package com.hanson.chat.server.core;


import com.hanson.chat.common.pojo.Message;
import com.hanson.chat.common.pojo.MsgHeader;
import com.hanson.chat.common.utils.MessageUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

/**
 * Created by hanson on 2017/1/5.
 */
public class CoreServer implements Runnable {

    private int port;

    private Map<String, SocketChannel> channelMap = new HashMap<>();

    private ServerSocketChannel server = null;

    private Selector selector = null;

    private Set<String> userList = null;

    public CoreServer(int port) {
        this.port = port;
        userList = new HashSet<>();

    }

    public void init() throws IOException {
        selector = Selector.open();
        server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(port));
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("server has started ....");
    }

    public void run() {
        try {
            ServerSocketChannel serverSocketChannel = null;
            SocketChannel client = null;
            ServerSocket socket = null;
            while (selector.select() > 0) {
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    if (key.isReadable()) {
                        client = (SocketChannel) key.channel();
                        ByteBuffer buffer = MessageUtils.getBuffer();
                        try {
                            client.read(buffer);
                        } catch (Exception e) {
                            String name = (String) key.attachment();
                            System.out.println(name+" is offline");
                            channelMap.remove(name);
                            userList.remove(name);
                            key.cancel();
                            Message message = MessageUtils.getOfflineMessage(name);
                            sendMessage(message);
                            continue;
                        }
                        buffer.flip();
                        Message message = MessageUtils.decodeMessage(buffer);
                        MsgHeader header = message.getHeader();

                        String name = header.getSender();
                        switch (header.getType()) {
                            case setName:
                                String conMsg;
                                if (channelMap.containsKey(name)) {
                                    conMsg = "user name already exist...";
                                    message = MessageUtils.getDoubleNameMessage(name);
                                    buffer.clear();
                                    message.writeTo(buffer);
                                    client.write(buffer);
                                } else {
                                    conMsg = name+":connect server success...";
                                    channelMap.put(name, client);
                                    key.attach(name);
                                    userList.add(name);
                                    message = MessageUtils.getOnlineMessage(name);
                                    sendMessage(message);
                                }
                                System.out.println(conMsg);
                                break;
                            case sendMessage:
//                                name = (String)key.attachment();
//                                client = channelMap.get(name);
//                                buffer = MessageUtils.getBuffer();
//                                client.read(buffer);
//                                Message msg = Message.readFrom(buffer);
                                sendMessage(message);
                                break;
                            case requestUserList:

                                break;
                        }
                    } else if (key.isAcceptable()) {
                        serverSocketChannel = (ServerSocketChannel) key.channel();
                        client = serverSocketChannel.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        System.out.println("a client connected...." + client.getRemoteAddress().toString());
                    }
                }
                keys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Message msg) throws IOException {
//        String receiver = msg.getHeader().getReceiver();
        ByteBuffer buffer = MessageUtils.encodeMessage(msg);
//        if("all".equals(receiver)){
        for (SocketChannel channel : channelMap.values()) {
            buffer.flip();
            channel.write(buffer);
        }
//        }else{
//            SocketChannel channel = channelMap.get(receiver);
//            if(channel != null){
//                channel.write(buffer);
//            }
//        }
    }

    public static void main(String args[]) {

        CoreServer server = new CoreServer(8087);
        try {
            server.init();
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
