package org.example;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame implements ActionListener{
    JPanel menuPanel;
    JButton startButton;
    JButton exitButton;
    JButton settingButton;
    JButton skillTreeButton;
    JButton tutorialButton;

    public MenuFrame(){
        startButton = new JButton("Start");
        startButton.setBounds(100,80,70,40);
        startButton.setForeground(Color.black);
        startButton.setBackground(Color.red);
        startButton.addActionListener(this);



        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(Color.black);
        menuPanel.add(startButton);


        this.setTitle("Window Kill");
        this.add(menuPanel);
        this.setSize(300,300);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adding a shutdown hook to restore minimized windows when the program stops
     //   Runtime.getRuntime().addShutdownHook(new Thread(() -> restoreAllWindows()));
    }














    private void minimizeAllWindowsExcept() {
        User32 user32 = User32.INSTANCE;
        WinDef.HWND hwndFrame = new WinDef.HWND(Native.getWindowPointer(this));

        user32.EnumWindows((h, p) -> {
            if (h != null && !h.equals(hwndFrame) && user32.IsWindowVisible(h)) {
                user32.ShowWindow(h, User32.SW_MINIMIZE);
            }
            return true;
        }, null);
    }
    private static void restoreAllWindows() {
        User32 user32 = User32.INSTANCE;
        user32.EnumWindows((h, p) -> {
            if (h != null && user32.IsWindowVisible(h)) {
                user32.ShowWindow(h, User32.SW_RESTORE);
            }
            return true;
        }, null);
    }











    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton){

         //   minimizeAllWindowsExcept();
            this.dispose();
            GameFrame gameFrame = new GameFrame();
        }
    }
}
