package com.hanson.chat.client.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hanson on 2017/1/6.
 */
public class UserList extends JPanel{
    private JTextArea userListText = null;
    public UserList(){
        super();
        setLayout(new FlowLayout());
        userListText = new JTextArea(25,7);
        userListText.setEditable(false);
        add(new JLabel("online user"));
//        setBorder(new TitledBorder("online user"));
        add(new JScrollPane(userListText));
        setVisible(true);
    }

    public void addUser(String userName) {
//        add(new JLabel(userName));
        userListText.append(userName+"\n");
    }
    public void deleteUser(String userName){
        String string  = userListText.getText().trim();
        userListText.setText(string.replace(userName,""));
    }
}
