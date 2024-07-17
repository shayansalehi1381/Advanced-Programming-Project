package View;

import Factory.WaveConfiguration;
import Model.Epsilon;
import Controller.Sound;
import Controller.Wave;
import Factory.WaveEnemyFactory;
import Factory.WaveConfiguration;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class SettingPanel extends JPanel implements ActionListener , ChangeListener {

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





        // Sound Slider
        soundSlider = new JSlider(0, 2,1);
        soundSlider.setForeground(Color.red);
        soundSlider.setBackground(Color.white);
        soundSlider.setMajorTickSpacing(1);
        soundSlider.setPaintTicks(true);
        soundSlider.setPaintLabels(true);
        soundSlider.setBounds(50, 50, 200, 70);
        soundSlider.setBorder(BorderFactory.createTitledBorder("Sound"));
        add(soundSlider);

        // Movement Slider
        movementSlider = new JSlider(1, 3, 1);
        movementSlider.setForeground(Color.red);
        movementSlider.setBackground(Color.white);
        movementSlider.setMajorTickSpacing(1);
        movementSlider.setPaintTicks(true);
        movementSlider.setPaintLabels(true);
        movementSlider.setBounds(50, 150, 200, 70);
        movementSlider.setBorder(BorderFactory.createTitledBorder("Movement"));
        add(movementSlider);

        // Difficulty Slider
        difficultySlider = new JSlider(0, 2, 1);
        difficultySlider.setForeground(Color.red);
        difficultySlider.setBackground(Color.white);
        difficultySlider.setMajorTickSpacing(1);
        difficultySlider.setPaintTicks(true);
        difficultySlider.setPaintLabels(true);
        difficultySlider.setBounds(50, 250, 200, 70);
        difficultySlider.setBorder(BorderFactory.createTitledBorder("Difficulty"));

        // Set labels for the difficulty slider
        difficultySlider.setLabelTable(new Hashtable<Integer, JLabel>() {{
            put(0, new JLabel("Easy"));
            put(1, new JLabel("Normal"));
            put(2, new JLabel("Hard"));
        }});
        add(difficultySlider);



        // Add ChangeListener to sliders
        soundSlider.addChangeListener(this);
        movementSlider.addChangeListener(this);
        difficultySlider.addChangeListener(this);





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

    @Override
    public void stateChanged(ChangeEvent e) {
            if (e.getSource() == soundSlider){

                int value = soundSlider.getValue();
                if (value == 0){
                    Sound.VOLUME = -80.0f;
                }
                else if (value == 2){
                    Sound.VOLUME = 6.0f;
                }
                else {
                    Sound.VOLUME = 0.0f;
                }
                for (int i = 0; i < Sound.sounds.size(); i++) {
                    Sound sound = Sound.sounds.get(i);
                    sound.fc.setValue(Sound.VOLUME);
                }
            }

            if (e.getSource() == movementSlider){

                int value = movementSlider.getValue();
                if (value == 3){
                    Epsilon.Movement = 3;
                }
                else if (value == 2){
                    Epsilon.Movement = 2;
                }
                else {
                    Epsilon.Movement = 1;
                }
            }

            if (e.getSource() == difficultySlider){
                int value = difficultySlider.getValue();
                if (value == 0){
                    Wave.GameDifficulty =1;
                }
                else if (value == 2){
                    Wave.GameDifficulty =2;
                }
                else if (value == 3){
                    Wave.GameDifficulty =3;
                }
            }
    }
}
