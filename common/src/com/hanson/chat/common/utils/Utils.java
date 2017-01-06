package com.hanson.chat.common.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by hanson on 2017/1/5.
 */
public class Utils {
    public static void writeUTF(ByteBuffer buffer,String str) throws UnsupportedEncodingException {
        byte[] ios = str.getBytes("utf-8");
        buffer.putInt(ios.length);
        buffer.put(ios);
    }

    public static String readUTF(ByteBuffer buffer) throws UnsupportedEncodingException {
        int len = buffer.getInt();
        byte[] ios = new byte[len];
        buffer.get(ios,0,len);
        return new String(ios,0,len,"utf-8");
    }
}
