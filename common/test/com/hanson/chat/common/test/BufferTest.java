package com.hanson.chat.common.test;

import java.nio.ByteBuffer;

/**
 * Created by hanson on 2017/1/6.
 */
public class BufferTest {
    public static void main(String args[]){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putInt(1);
        buffer.putInt(2);
        buffer.flip();
        System.out.println(buffer.position()+" "+buffer.position());
        System.out.println(buffer.getInt()+" "+buffer.getInt());
    }
}
