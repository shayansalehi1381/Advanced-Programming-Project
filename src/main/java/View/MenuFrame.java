package View;

import com.sun.jna.platform.win32.User32;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {
     CardLayout cardLayout;
     JPanel cardPanel;
    JPanel menuPanel;

    SettingPanel settingPanel;
    GameOverPanel gameOverPanel;

    GameFrame gameFrame;




    public MenuFrame(){
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        menuPanel = new MenuPanel(this); // Assuming your menu panel is named MenuPanel
        settingPanel = new SettingPanel(this);
        gameOverPanel = new GameOverPanel();

        cardPanel.add(menuPanel, "menuPanel"); // Add menuPanel to cardPanel
        cardPanel.add(settingPanel,"settingsPanel"); // Add settingPanel to cardPanel
        cardPanel.add(gameOverPanel,"gameOverPanel"); // Add gameOverPanel to cardPanel

        // Add the cardPanel to the MenuFrame
        getContentPane().add(cardPanel, BorderLayout.CENTER);

        this.setTitle("Window Kill");
     //   getContentPane().add(cardPanel, BorderLayout.CENTER); //add the card panel to the frame!
        this.setSize(300,500);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adding a shutdown hook to restore minimized windows when the program stops
        Runtime.getRuntime().addShutdownHook(new Thread(() -> restoreAllWindows()));
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

}
