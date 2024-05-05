package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Sound {

    Clip clip;
    File themeSong;
    File damageSE;
    File enemyInsideSE;
    File gameOverSE;
    File killEnemySE;
    File waveEndSE;


    public Sound(){
         themeSong = new File("src/main/java/org/example/sound/backGround.wav");
         damageSE = new File("src/main/java/org/example/sound/damage.wav");
         enemyInsideSE = new File("src/main/java/org/example/sound/enemyInside.wav");
         gameOverSE = new File("src/main/java/org/example/sound/gameOver.wav");
         killEnemySE = new File("src/main/java/org/example/sound/killEnemy.wav");
         waveEndSE = new File("src/main/java/org/example/sound/waveEnd.wav");

    }


    public void setFile(File file) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (IOException e) {
            System.err.println("Error loading sound file: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Print full stack trace for debugging
        }
    }

    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }

}
