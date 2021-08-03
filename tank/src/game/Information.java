package game;

import java.awt.*;

public class Information {
    private Information(){}

    private static Information instance;

    public static Information getInstance(){
        if(instance == null){
            instance = new Information();
        }
        return instance;
    }

    private Image about = null;
    private Image help = null;


    public Image getAbout() {
        return about;
    }

    public void setAbout(Image about) {
        this.about = about;
    }

    public Image getHelp() {
        return help;
    }

    public void setHelp(Image help) {
        this.help = help;
    }
}
