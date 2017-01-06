package com.hanson.chat.client.main;

import com.hanson.chat.client.UI.ClientUI;
import com.hanson.chat.client.core.CoreClient;

/**
 * Created by hanson on 2017/1/6.
 */
public class Main {
    public static void main(String args[]){
        CoreClient coreClient = new CoreClient();
        ClientUI clientUI = new ClientUI();
        clientUI.setServer(coreClient);
    }
}
