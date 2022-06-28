package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed, leftPressed, rightPressed, downPressed, enterPressed,
	// Debug
	showDebugText;
	
	
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
		
		if (code == KeyEvent.VK_F3) showDebugText = true;
		
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
		gp.gameState = gp.CHARACTER_STATE;
		if (code == KeyEvent.VK_TAB || code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.PLAY_STATE;
		}
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			if (gp.ui.slotRow != 0) {
				gp.ui.slotRow--;
				gp.playSoundEffect(9);
			}
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			if (gp.ui.slotCol != 0) {
				gp.ui.slotCol--;
				gp.playSoundEffect(9);
			}
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			if (gp.ui.slotRow != 3) {
				gp.ui.slotRow++;
				gp.playSoundEffect(9);
			}
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			if (gp.ui.slotCol != 4) {
				gp.ui.slotCol++;
				gp.playSoundEffect(9);
			}
		}
		if (code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
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
