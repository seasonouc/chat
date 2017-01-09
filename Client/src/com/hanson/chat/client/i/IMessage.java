package com.hanson.chat.client.i;

import com.hanson.chat.common.pojo.Message;

/**
 * Created by hanson on 2017/1/9.
 */
public interface IMessage {
    public void addMessage(Message message);
    public void addUser(String user);
    public void deleteUser(String user);
    public void connectSuccess(boolean success);
    public void setNameSuccess(boolean success);
}
