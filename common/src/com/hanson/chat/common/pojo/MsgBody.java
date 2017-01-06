package com.hanson.chat.common.pojo;

import com.hanson.chat.common.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * Created by hanson on 2017/1/5.
 */
public class MsgBody {
    public String getStrMsg() {
        return strMsg;
    }

    public void setStrMsg(String strMsg) {
        this.strMsg = strMsg;
    }

    public String strMsg;

    public void writeTo(ByteBuffer buffer) throws UnsupportedEncodingException {
        Utils.writeUTF(buffer,strMsg);
    }

    public static MsgBody readFrom(ByteBuffer buffer) throws UnsupportedEncodingException {
        MsgBody body = new MsgBody();
        body.setStrMsg(Utils.readUTF(buffer));
        return body;
    }
}
