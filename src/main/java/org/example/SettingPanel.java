package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingPanel extends JPanel implements ActionListener {

    private CardLayout cardLayout;
    private JPanel cardPanel;


    JSlider soundSlider;
    JSlider movementSlider;
    JSlider difficultySlider;


    JButton backButton;


    GameFrame gameFrame;
    MenuFrame menuFrame;

    public SettingPanel(MenuFrame menuFrame){
        this.menuFrame = menuFrame;
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);



        backButton = new JButton();
        backButton.addActionListener(this);
        backButton.setText("Back");
        backButton.setBackground(Color.red);
        backButton.setForeground(Color.black);
        backButton.setBounds(50,400,70,40);

        this.setLayout(null);
        this.add(backButton);
        this.setBackground(Color.black);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
            if (e.getSource() == backButton){
                menuFrame.cardLayout.show(menuFrame.cardPanel,"menuPanel");
            }
    }
}
