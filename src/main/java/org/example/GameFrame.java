package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameFrame extends JFrame {
     GamePanel gamePanel;
    int width = 700;
    int height = 700;

    public GameFrame() {

        gamePanel = new GamePanel();
        gamePanel.setLayout(null);
        gamePanel.setBackground(Color.black);

        this.setTitle("Window Kill");
        this.add(gamePanel);
        this.setSize(width, height);
        this.setUndecorated(true);
        this.setLocationByPlatform(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);




    }
}
