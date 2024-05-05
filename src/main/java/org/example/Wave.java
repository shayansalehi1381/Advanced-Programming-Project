package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Wave {

    GameFrame gameFrame;
    int triangles ;
    int squares;
    public Wave(GameFrame frame) {
        gameFrame = frame;
        if (GamePanel.Wave == 1){
            triangles = 1;
            squares = 2;
            for (int i = 0; i < triangles; i++) {
                TrigorathEnemy trigorathEnemy = new TrigorathEnemy(gameFrame);
            }

            for (int j = 0; j < squares; j++) {
               SquareEnemy squareEnemy = new SquareEnemy(gameFrame);
            }

        }


        if (GamePanel.Wave == 2){
            triangles = 2;
            squares = 2;
            for (int i = 0; i < triangles; i++) {
                TrigorathEnemy trigorathEnemy = new TrigorathEnemy(gameFrame);
            }

           for (int j = 0; j < squares; j++) {
                SquareEnemy squareEnemy = new SquareEnemy(gameFrame);
            }

        }


        if (GamePanel.Wave == 3){
            triangles = 3;
            squares = 2;
            for (int i = 0; i < triangles; i++) {
                TrigorathEnemy trigorathEnemy = new TrigorathEnemy(gameFrame);
            }

            for (int j = 0; j < squares; j++) {
                SquareEnemy squareEnemy = new SquareEnemy(gameFrame);
            }

        }


    }
}
