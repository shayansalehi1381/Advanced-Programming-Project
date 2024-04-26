package org.example;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Window Minimizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minimizeAllWindowsExcept(frame);
            }
        });
        frame.getContentPane().add(startButton);
        frame.setSize(200, 100);
        frame.setVisible(true);

        // Adding a shutdown hook to restore minimized windows when the program stops
        Runtime.getRuntime().addShutdownHook(new Thread(() -> restoreAllWindows()));
    }

    private static void minimizeAllWindowsExcept(JFrame frame) {
        User32 user32 = User32.INSTANCE;
        WinDef.HWND hwndFrame = new WinDef.HWND(Native.getWindowPointer(frame));

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
}
