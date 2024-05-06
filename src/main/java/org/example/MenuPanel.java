package org.example;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel implements ActionListener {



    MenuFrame menuFrame;

    JButton startButton;
    JButton exitButton;
    JButton settingButton;
    JButton skillTreeButton;
    JButton tutorialButton;

    public MenuPanel(MenuFrame menuFrame){
        this.menuFrame = menuFrame;
        startButton = new JButton("Start");
        startButton.setBounds(60,40,150,40);
        startButton.setForeground(Color.black);
        startButton.setBackground(Color.red);
        startButton.addActionListener(this);



        settingButton = new JButton("Settings");
        settingButton.setBounds(60,100,150,40);
        settingButton.setForeground(Color.black);
        settingButton.setBackground(Color.red);
        settingButton.addActionListener(this);


        tutorialButton = new JButton("Tutorial");
        tutorialButton.setBounds(60,160,150,40);
        tutorialButton.setForeground(Color.black);
        tutorialButton.setBackground(Color.red);
        tutorialButton.addActionListener(this);


        skillTreeButton = new JButton("Skill Tree");
        skillTreeButton.setBounds(60,220,150,40);
        skillTreeButton.setForeground(Color.black);
        skillTreeButton.setBackground(Color.red);
        skillTreeButton.addActionListener(this);



        exitButton = new JButton("Exit");
        exitButton.setBounds(60,280,150,40);
        exitButton.setForeground(Color.black);
        exitButton.setBackground(Color.red);
        exitButton.addActionListener(this);










        setLayout(null);
        setBackground(Color.black);
        add(startButton);
        add(settingButton);
        add(tutorialButton);
        add(skillTreeButton);
        add(exitButton);
    }




    private void minimizeAllWindowsExcept() {
        User32 user32 = User32.INSTANCE;
        WinDef.HWND hwndFrame = new WinDef.HWND(Native.getWindowPointer(this.menuFrame));

        user32.EnumWindows((h, p) -> {
            if (h != null && !h.equals(hwndFrame) && user32.IsWindowVisible(h)) {
                user32.ShowWindow(h, User32.SW_MINIMIZE);
            }
            return true;
        }, null);
    }







    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton){

               minimizeAllWindowsExcept();

            menuFrame.gameFrame = new GameFrame();
        }

        if (e.getSource() == tutorialButton){

        }

        if (e.getSource() == settingButton){
            menuFrame.cardLayout.show(menuFrame.cardPanel,"settingsPanel");
        }

        if (e.getSource() == skillTreeButton){

        }

        if (e.getSource() == exitButton){
            System.exit(0);
        }
    }
}
