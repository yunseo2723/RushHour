package View.etc;
import java.io.File;
import javax.sound.sampled.*;

public class BackgroundMusic {
    public Clip clip;

    public BackgroundMusic(String musicPath) {
        try {
            File musicFile = new File(musicPath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null && !clip.isRunning()) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.setFramePosition(0);
        }
    }
    public Clip getClip() {
        return clip;
    }
}

