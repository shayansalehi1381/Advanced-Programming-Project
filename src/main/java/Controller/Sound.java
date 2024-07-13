package Controller;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Sound {

    public Clip clip;
    public File themeSong;
    public File damageSE;
    public File enemyInsideSE;
    public File gameOverSE;
    public File killEnemySE;
    public File waveEndSE;

    float previousVolume = 0;
    float currentVolume = 0;
    public FloatControl fc;
    boolean mute = false;
    public static float VOLUME;
    public static ArrayList<Sound> sounds = new ArrayList<>();


    public Sound(){
         themeSong = new File("src/main/java/Assests/sound/backGround.wav");
         damageSE = new File("src/main/java/Assests/sound/damage.wav");
         enemyInsideSE = new File("src/main/java/Assests/sound/enemyInside.wav");
         gameOverSE = new File("src/main/java/Assests/sound/gameOver.wav");
         killEnemySE = new File("src/main/java/Assests/sound/killEnemy.wav");
         waveEndSE = new File("src/main/java/Assests/sound/waveEnd.wav");
         sounds.add(this);

    }


    public void setFile(File file) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
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

    public void volumeUP(){
        currentVolume += 1.0f;
        if(currentVolume > 6.0f){
            currentVolume = 6.0f;
        }
        fc.setValue(currentVolume);
    }
    public void volumeDown(){
        currentVolume -= 1.0f;
        if (currentVolume < -80.0f){
            currentVolume = -80.0f;
        }
        fc.setValue(currentVolume);
    }

    public void volumeMute(){
        if (mute == false){
            previousVolume = currentVolume;
            currentVolume = -80.0f;
            fc.setValue(currentVolume);
            mute = true;
        }

        else if (mute){
            currentVolume = previousVolume;
            fc.setValue(currentVolume);
            mute = false;
        }
    }

}
