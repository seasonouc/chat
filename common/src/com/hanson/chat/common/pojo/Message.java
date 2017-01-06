package com.hanson.chat.common.pojo;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by hanson on 2017/1/5.
 */
public class Message {
    private MsgBody body;
    private MsgHeader header;

    public MsgBody getBody() {
        return body;
    }

    public void setBody(MsgBody body) {
        this.body = body;
    }

    public MsgHeader getHeader() {
        return header;
    }

    public void setHeader(MsgHeader header) {
        this.header = header;
    }
    public void writeTo(ByteBuffer buffer) throws UnsupportedEncodingException {
        header.writeTo(buffer);
        body.writeTo(buffer);
    }
    public static Message readFrom(ByteBuffer buffer) throws UnsupportedEncodingException {
        MsgHeader header = MsgHeader.readFrom(buffer);
        MsgBody body = MsgBody.readFrom(buffer);
        Message msg = new Message();
        msg.setBody(body);
        msg.setHeader(header);
        return msg;
    }
}
