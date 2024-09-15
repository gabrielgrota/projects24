package pomodoro;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Pomodoro {
	// teste
	// private static final int TEST_TIME = 3; // Test after 10 seconds
	
	// constants
	private static final int WORK_TIME = 25 * 60; // 25 min
	// private static final int BREAK_TIME = 5 * 60; // 5 min
	
	private static final String SOUND_FILE_PATH = "C:\\Users\\Gabriel\\eclipse-workspace\\pomodoro-cmd\\sounds\\echo.wav";
	private static final int REPEAT_COUNT = 3;
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in); // create a scanner object
		String answer;

        System.out.println("This is a pomodoro timer.");
        
        do {
        	System.out.println("Do you want to continue? (y/n)");
        	answer = s.nextLine();    
        	
        	if (answer.equals("y")) {
        		System.out.println("Timer started...");
        		
        		// start timer
        		startTimer();
        	} else if (answer.equals("n")) {
        		System.out.println("Okay, see you later.");
        		
        		// close the terminal
        		System.exit(0);
        	} else {
        		System.out.println("You typed: " + answer + ". Try again.");
        	}
        } while (!answer.equals("y") && !answer.equals("n"));
        
        s.close();
	}

	private static void startTimer() {
		int timeRemaining = WORK_TIME;
		
		while (timeRemaining > 0) {
			printTime(timeRemaining);
			try {
				TimeUnit.SECONDS.sleep(1); // waits 1 second
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timeRemaining--;
		}
		printTime(timeRemaining);
		System.out.println("\nWork time finished! Do a break. Get outside. See the sun. Or the moon.");
		playSound(SOUND_FILE_PATH, REPEAT_COUNT);
	}
	
	private static void printTime(int timeInSeconds) {
		int minutes = timeInSeconds / 60;
		int seconds = timeInSeconds % 60;
		System.out.printf("\r%02d:%02d", minutes, seconds);
	}
	
	private static void playSound(String soundFilePath, int repeatCount) {
		try {
			File soundFile = new File(soundFilePath);
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(soundFile));
			for (int i = 0; i < repeatCount; i++) {
				clip.setFramePosition(0);
				clip.start();
				Thread.sleep(clip.getMicrosecondLength() / 1000); // wait for the sound to end
				clip.stop();
			}
		} catch (UnsupportedAudioFileException | IOException | InterruptedException | javax.sound.sampled.LineUnavailableException e) {
            e.printStackTrace();
        }
	}
}