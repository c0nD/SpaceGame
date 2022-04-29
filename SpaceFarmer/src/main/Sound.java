package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/Amynedd - 16-Bit Adventure - 02 An Opening.wav");
		soundURL[1] = getClass().getResource("/sound/Coin.wav");
		soundURL[2] = getClass().getResource("/sound/Power_Up.wav");
		soundURL[3] = getClass().getResource("/sound/Unlock.wav");
		soundURL[4] = getClass().getResource("/sound/Fanfare.wav");
	}
	
	// Default way to open an audio file
	public void setFile(int i) {
		try {
			AudioInputStream AIS = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(AIS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
}
