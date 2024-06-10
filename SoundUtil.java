import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundUtil {
    private static Clip clip;

    public static Clip playSound(String soundFile, boolean loop) {
        try {
            File soundPath = new File(soundFile);
            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
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

