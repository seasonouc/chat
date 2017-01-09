package com.hanson.chat.server.core;

import com.hanson.chat.common.e.MsgType;
import com.hanson.chat.common.pojo.Message;
import com.hanson.chat.common.utils.MessageUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by hanson on 2017/1/6.
 */
public class MessageResolver implements Runnable {

    private BlockingQueue<Message> queue  = null;
    private Map<String,SocketChannel> channelMap = null;

    public MessageResolver(BlockingQueue<Message> queue, Map<String,SocketChannel> channelMap){
        this.queue = queue;
        this.channelMap = channelMap;
    }

    @Override
    public void run(){
        while(true){
            try {
                Message msg = queue.take();
                ByteBuffer buffer = MessageUtils.getBuffer();
                msg.writeTo(buffer);
                MsgType msgType = msg.getHeader().getType();
                buffer.flip();
                switch (msgType) {
                    case toAll:
                        for(SocketChannel channel:channelMap.values()){
                            channel.write(buffer);
                        }
                        break;
                    case toSome:
                        String receiver = msg.getHeader().getReceiver();
                        SocketChannel channel = channelMap.get(receiver);
                        if(channel != null&&channel.isConnected()){
                            channel.write(buffer);
                        }
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
