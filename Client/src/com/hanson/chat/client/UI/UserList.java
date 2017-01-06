package com.hanson.chat.client.UI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by hanson on 2017/1/6.
 */
public class UserList extends JPanel{
    public UserList(){
        super();
        add(new JLabel("hanson"));
        setBorder(new TitledBorder("online user"));
        setVisible(true);
    }
}
