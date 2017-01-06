package com.hanson.chat.client.core;

import com.hanson.chat.client.i.IServer;
import com.hanson.chat.common.pojo.Message;
import com.hanson.chat.common.utils.MessageUtils;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by hanson on 2017/1/5.
 */
public class CoreClient implements IServer{

    private int port;
    private String ip;
    private TextArea textArea = null;

/*    private Selector selector = null;
    private SocketChannel channel = null;
    private BlockingQueue<Message> queue = null;*/

    private Socket socket = null;

    public CoreClient(String ip, int port) {
        this.port = port;
        this.ip = ip;
    }

    public CoreClient() {

    }

    @Override
    public void sendMessage(String msg, String name) throws IOException {
        Message message = MessageUtils.getChatMessage(name,msg);
        ByteBuffer buffer = MessageUtils.getBuffer();
        message.writeTo(buffer);

        OutputStream outputStream = socket.getOutputStream();
        byte[] b = buffer.array();
        outputStream.write(b);
        outputStream.flush();
    }

    @Override
    public void setName(String name) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            ByteBuffer buffer = MessageUtils.getSetNameMessage(name);
            buffer.flip();
            byte[] b = buffer.array();
            outputStream.write(b);
            outputStream.flush();
            System.out.println("set name success...");
        } catch (IOException e) {
            System.out.println("set name failed..."+e.getCause());
        }

    }

    @Override
    public void connectServer(String ip, int port) {
        socket = new Socket();
        InetSocketAddress address = new InetSocketAddress(ip, port);
        try {
            socket.setKeepAlive(true);
            socket.connect(address,1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disConnect() throws IOException {
        socket.close();
        socket = null;
    }

    @Override
    public void setMsgBox(TextArea textArea) {
        this.textArea = textArea;
    }

    public void run() {
        InputStream inputStream= null;
        try {
            inputStream = socket.getInputStream();
            byte []b = new byte[1024];
            while(inputStream.read(b) > 0){
                ByteBuffer buffer = MessageUtils.getBuffer();
                buffer.put(b);
                Message message = MessageUtils.decodeMessage(buffer);
                textArea.append(message.getBody().getStrMsg());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {

        CoreClient client = new CoreClient();
        client.connectServer("127.0.0.1",8087);
        client.setName("hanson");
        client.setName("hanson");
    }
}
