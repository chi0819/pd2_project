import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//Create a class that play sounds
class SoundUtil {

    private static Clip clip;

    public static Clip playSound(String soundFile, boolean loop) {

        try {
            File soundPath = new File(soundFile);

            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath); //Obtains an audio input stream from the provided input stream
                clip = AudioSystem.getClip(); //Get available sound playback clips
                clip.open(audioInput);

                if (loop) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY); //Looping should continue indefinitely
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