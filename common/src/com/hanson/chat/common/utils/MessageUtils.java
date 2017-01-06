package com.hanson.chat.common.utils;

import com.hanson.chat.common.e.ClientMsgType;
import com.hanson.chat.common.pojo.Message;
import com.hanson.chat.common.pojo.MsgBody;
import com.hanson.chat.common.pojo.MsgHeader;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by hanson on 2017/1/5.
 */
public class MessageUtils {
    public static ByteBuffer encodeMessage(Message msg) throws UnsupportedEncodingException {
        ByteBuffer buffer = getBuffer();
        msg.writeTo(buffer);
        return buffer;
    }

    public static Message decodeMessage(ByteBuffer buffer) throws UnsupportedEncodingException {
        Message msg = Message.readFrom(buffer);
        return msg;
    }

    public static ByteBuffer getBuffer(){
        ByteBuffer buffer = ByteBuffer.allocate(Constant.MAX_SIZE);
        return buffer;
    }
    public static Message getChatMessage(String name,String msg){
        MsgHeader header = new MsgHeader();
        header.setType(ClientMsgType.sendMessage);
        header.setReceiver("all");
        header.setSender(name);
        header.setIp("");

        MsgBody body = new MsgBody();
        body.setStrMsg(msg);

        Message message = new Message();
        message.setBody(body);
        message.setHeader(header);
        return message;
    }
    public static ByteBuffer getSetNameMessage(String name) throws UnsupportedEncodingException {
        MsgHeader header = new MsgHeader();
        header.setType(ClientMsgType.setName);
        header.setReceiver("server");
        header.setSender(name);
        header.setIp("");

        MsgBody body = new MsgBody();
        body.setStrMsg("");

        Message message = new Message();
        message.setBody(body);
        message.setHeader(header);

        ByteBuffer buffer = getBuffer();
        message.writeTo(buffer);
        return buffer;
    }
}
