package com.hanson.chat.client.core;

import com.hanson.chat.client.i.IMessage;
import com.hanson.chat.client.i.IServer;
import com.hanson.chat.common.e.MsgType;
import com.hanson.chat.common.pojo.Message;
import com.hanson.chat.common.utils.MessageUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanson on 2017/1/5.
 */
public class CoreClient implements IServer{

    private int port;
    private String ip;
    private IMessage resolve = null;
    private List<String> userList = null;

/*    private Selector selector = null;
    private SocketChannel channel = null;
    private BlockingQueue<Message> queue = null;*/

    private Socket socket = null;

    public CoreClient(String ip, int port) {
        this.port = port;
        this.ip = ip;
    }

    public CoreClient() {

        userList = new ArrayList<>();
    }

    public void setIMessager(IMessage resolve){
        this.resolve = resolve;
    }

    @Override
    public void sendMessage(String msg, String name) throws IOException {
        if(socket==null||!socket.isConnected()){
            return;
        }
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
        if(socket == null||!socket.isConnected()){
            return;
        }
        try {
            OutputStream outputStream = socket.getOutputStream();
            ByteBuffer buffer = MessageUtils.getSetNameMessage(name);
            buffer.flip();
            byte[] b = buffer.array();
            outputStream.write(b);

//            resolve.setNameSuccess(true);
            System.out.println("set name success...");
            outputStream.flush();
        } catch (IOException e) {
            resolve.setNameSuccess(false);
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
            resolve.connectSuccess(true);
        } catch (IOException e) {
            resolve.connectSuccess(false);
            e.printStackTrace();
        }
    }

    @Override
    public void disConnect() throws IOException {
        socket.close();
        socket = null;
    }


    @Override
    public void run() {
        InputStream inputStream= null;
        try {
            inputStream = socket.getInputStream();
            byte []b = new byte[1024];
            while(inputStream.read(b) > 0){
                ByteBuffer buffer = MessageUtils.getBuffer();
                buffer.put(b);
                buffer.flip();
                Message message = MessageUtils.decodeMessage(buffer);
//                StringBuffer sb = new StringBuffer();
//                sb.append(message.getHeader().getSender());
//                sb.append(":");
//                sb.append(message.getBody().getStrMsg());
//                sb.append("\n");
//                textArea.append(sb.toString());

                switch(message.getHeader().getType()){
                    case online:
                        resolve.addUser(message.getHeader().getSender());
                        message.getBody().setStrMsg("online");
                        break;
                    case offline:
                        message.getBody().setStrMsg("offline");
                        resolve.deleteUser(message.getHeader().getSender());
                        break;
                    case doubleName:
                        message.getBody().setStrMsg("user name already use");
                        break;
                    case requestUserList:
                        String []userList = message.getBody().getStrMsg().split("|");
                        for(String user:userList){
                            resolve.addUser(user);
                        }
                }
                resolve.addMessage(message);
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
