package com.hanson.chat.client.i;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by hanson on 2017/1/6.
 */
public interface IServer extends Runnable{
    public void sendMessage(String msg,String name) throws IOException;
    public void setName(String name);
    public void connectServer(String ip,int port);
    public void disConnect() throws IOException;
}
