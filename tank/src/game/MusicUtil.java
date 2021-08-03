package game;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.io.File;
import java.net.MalformedURLException;

public class MusicUtil {
    private static AudioClip start;
    private static AudioClip bomb;

    static{
        try {
            start = Applet.newAudioClip(new File("music.mp3").toURL());
            bomb = Applet.newAudioClip(new File("").toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void playStart(){
        System.out.println("开始表演");
        start.play();
    }
    public static void playBomb(){
        bomb.play();
    }

}
