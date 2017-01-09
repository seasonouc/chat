package com.hanson.chat.common.pojo;

import com.hanson.chat.common.e.MsgType;
import com.hanson.chat.common.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by hanson on 2017/1/5.
 */
public class MsgHeader {
    private String sender;
    private String receiver;
    private String ip;
    private MsgType type;
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setType(MsgType type){
        this.type = type;
    }
    public MsgType getType(){
        return type;
    }
    public void writeTo(ByteBuffer buffer) throws UnsupportedEncodingException {
        buffer.putInt(type.ordinal());
        Utils.writeUTF(buffer,sender);
        Utils.writeUTF(buffer,receiver);
        Utils.writeUTF(buffer,ip);
    }

    public static MsgHeader readFrom(ByteBuffer buffer) throws UnsupportedEncodingException {
        MsgHeader header  = new MsgHeader();
        header.setType(MsgType.values()[buffer.getInt()]);
        header.setSender(Utils.readUTF(buffer));
        header.setReceiver(Utils.readUTF(buffer));
        header.setIp(Utils.readUTF(buffer));
        return header;
    }
}
