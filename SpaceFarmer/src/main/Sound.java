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
		soundURL[5] = getClass().getResource("/sound/Saber_Hit.wav");
		soundURL[6] = getClass().getResource("/sound/Squelb_Hit.wav");
		soundURL[7] = getClass().getResource("/sound/Damage.wav");
		soundURL[8] = getClass().getResource("/sound/Level_Up.wav");
		soundURL[9] = getClass().getResource("/sound/Cursor.wav");
		soundURL[10] = getClass().getResource("/sound/No_Damage_Hit.wav");

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
