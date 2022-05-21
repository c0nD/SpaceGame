package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed, leftPressed, rightPressed, downPressed, enterPressed;
	
	GamePanel gp;
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();	
		if (gp.gameState == gp.TITLE_STATE) {
			titleState(code);
		}
		else if (gp.gameState == gp.PLAY_STATE) {
			playState(code);
		}
		else if (gp.gameState == gp.PAUSE_STATE) {
			pauseState(code);
		}
		else if (gp.gameState == gp.DIALOGUE_STATE) {
			dialogueState(code);
		}
		else if (gp.gameState == gp.CHARACTER_STATE) {
			characterState(code);
		}
	}
	
	public void titleState(int code) {
		if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
			if (gp.ui.commandNum != 0) gp.ui.commandNum--;
		}
		else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
			if (gp.ui.commandNum != 2) gp.ui.commandNum++;
		}
		
		if (code == KeyEvent.VK_ENTER) {
			// New Game
			if (gp.ui.commandNum == 0) {
				gp.gameState = gp.PLAY_STATE;
				gp.playMusic(0);
			}
			// Load Game
			else if (gp.ui.commandNum == 1) {
				// add l8r
			}
			else if (gp.ui.commandNum == 2) {
				System.exit(0);
			}
		}
	}
	
	public void playState(int code) {
		if (code == KeyEvent.VK_W) upPressed = true;
		if (code == KeyEvent.VK_A) leftPressed = true;	
		if (code == KeyEvent.VK_S) downPressed = true;
		if (code == KeyEvent.VK_D) rightPressed = true;
		if (code == KeyEvent.VK_ENTER) enterPressed = true;
		
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.PAUSE_STATE;
		}
		if (code == KeyEvent.VK_TAB) {
			gp.gameState = gp.CHARACTER_STATE;
		}
	}
	
	public void pauseState(int code) {
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.PLAY_STATE;
		}
	}
	
	public void dialogueState(int code) {
		if (code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.PLAY_STATE;
		}
	}
	
	public void characterState(int code) {
		if (code == KeyEvent.VK_TAB) {
			gp.gameState = gp.CHARACTER_STATE;
			if (code == KeyEvent.VK_TAB) {
				gp.gameState = gp.PLAY_STATE;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W) upPressed = false;
		if (code == KeyEvent.VK_A) leftPressed = false;	
		if (code == KeyEvent.VK_S) downPressed = false;	
		if (code == KeyEvent.VK_D) rightPressed = false;
	}
}
