import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class SoundUtil {
    private static Clip clip;

    public static Clip playSound(String soundFile, boolean loop) {
        return playSoundWithVolume(soundFile, loop, 1.0f);  
    }

    //control volume
    public static Clip playSoundWithVolume(String soundFile, boolean loop, float volume) {
        try {
            File soundPath = new File(soundFile);
            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);

                FloatControl volumeControl = null;
                try {
                    volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float min = volumeControl.getMinimum();
                    float max = volumeControl.getMaximum();
                     
                    if (volume < 0.0f) {
                        volume = 0.0f;
                    } else if (volume > 1.0f) {
                        volume = 1.0f;
                    }
                    
                    float newVolume = min + (max - min) * volume;
                    volumeControl.setValue(newVolume);
                } catch (IllegalArgumentException e) {
                    
                }
                
                if (loop) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                clip.start();
            } else {
                System.out.println("Cannot find sound file: " + soundFile);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return clip;
    }

    public static void stopSound(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}

