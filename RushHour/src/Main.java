import Controller.Frame.CarMovementFrame;
import View.etc.BackgroundMusic;

public class Main {
    public static void main(String[] args) {
    	
        BackgroundMusic backgroundMusic = new BackgroundMusic("Music/Back.wav");
        CarMovementFrame frame = new CarMovementFrame(backgroundMusic);
        frame.run();
        
        backgroundMusic.play();
    }
}