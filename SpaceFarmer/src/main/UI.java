package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import entity.Entity;
import entity.NPC_Alien;
import object.Object_Heart;
import object.Object_Key;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font MaruMonica;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	BufferedImage heart_full, heart_half, heart_empty;
	
	Entity titleEntities[] = new Entity[5];
    Entity titleObjects[] = new Entity[5];
	
	public UI(GamePanel gp) {
		this.gp = gp;

		try {
			InputStream is = getClass().getResourceAsStream("/fonts/MaruMonica.ttf"); // Can add any other fonts below.
			MaruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// HUD
		Entity heart = new Object_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_empty = heart.image3;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(MaruMonica);
		// g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		
		if (gp.gameState == gp.TITLE_STATE) {
			drawTitleScreen();
		}
		else if (gp.gameState == gp.PLAY_STATE) {
			drawPlayerHealth();
		}
		else if (gp.gameState == gp.PAUSE_STATE) {
			drawPlayerHealth();
			drawPauseScreen();
		}
		else if (gp.gameState == gp.DIALOGUE_STATE) {
			drawPlayerHealth();
			drawDialogueScreen();
		}
	}
	
	public void drawPlayerHealth() {		
		int x = gp.TILE_SIZE / 2;
		int y =  gp.TILE_SIZE / 2;
		int i = 0;
		
		// Draws blank hearts underneath
		while (i < gp.player.maxHP / 2) {
			g2.drawImage(heart_empty, x, y, null);
			i-=-1;
			x += gp.TILE_SIZE+4;
		}
		
		x = gp.TILE_SIZE / 2;
		y =  gp.TILE_SIZE / 2;
		i = 0;
		// Draws current health above
		while (i < gp.player.hp) {
			g2.drawImage(heart_half, x, y, null);
			i-=-1;
			if (i < gp.player.hp) { // If you have a full heart it gets replaced
				g2.drawImage(heart_full, x, y, null);
			}
			i-=-1;
			x += gp.TILE_SIZE+4;
		}
	}
	
	public void setTitleAssets() {
		titleEntities[0] = new NPC_Alien(gp);
	}
	
	public void drawTitleScreen() {
		
		g2.setColor(new Color(0,0,0));
		g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
		
		// Title Name
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
		String titleName = "Space Game";
		int x = getXCenter(titleName);
		int y = gp.TILE_SIZE * 3;
	
		// Drop shadow
		g2.setColor(Color.GRAY);
		g2.drawString(titleName, x+5, y+5);
		// Main text color
		g2.setColor(Color.WHITE);
		g2.drawString(titleName, x, y);
		
		// Characters
		x = gp.SCREEN_WIDTH / 3;
		y += gp.TILE_SIZE * 2;
		g2.drawImage(gp.player.down1, x, y, gp.TILE_SIZE*2, gp.TILE_SIZE*2, null);
		
		setTitleAssets();
		x += (gp.TILE_SIZE * 3);
		g2.drawImage(titleEntities[0].down2, x, y, gp.TILE_SIZE*2, gp.TILE_SIZE*2, null);
		
		// Menu
		g2.setFont(g2.getFont().deriveFont(48F));
		
		String newGameText = "NEW GAME";
		x = getXCenter(newGameText);
		y += gp.TILE_SIZE * 3.5;
		g2.drawString(newGameText, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x-gp.TILE_SIZE, y);
		}
		
		String loadGameText = "LOAD GAME";
		x = getXCenter(loadGameText);
		y += gp.TILE_SIZE;
		g2.drawString(loadGameText, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x-gp.TILE_SIZE, y);
		}
		
		String quitGameText = "QUIT";
		x = getXCenter(quitGameText);
		y += gp.TILE_SIZE;
		g2.drawString(quitGameText, x, y);
		if (commandNum == 2) {
			g2.drawString(">", x-gp.TILE_SIZE, y);
		}
	}
	
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,75F));
		String text = "PAUSED";
		int x = getXCenter(text);
		int y = gp.SCREEN_HEIGHT/2 - gp.TILE_SIZE;

		
		drawSubWindow(x - gp.TILE_SIZE, y-72, x, (y/2)-24);
		
		g2.drawString(text, x, y);
	}
	
	public void drawDialogueScreen() {
		int x = gp.TILE_SIZE*2;
		int y = gp.TILE_SIZE*8;
		int width = gp.SCREEN_WIDTH - (gp.TILE_SIZE *  4);
		int height = gp.TILE_SIZE * 4;
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,34F));
		x += gp.TILE_SIZE-5;
		y += gp.TILE_SIZE;
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y+=40;
		}
		
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		Color color = new Color(225,225,225,210);
		g2.setColor(color);
		g2.fillRoundRect(x, y, width, height, 32, 32);
		
		color = new Color(0,0,0,210);
		g2.setColor(color);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 23, 23);
		
	}
	
	public int getXCenter(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = (gp.SCREEN_WIDTH/2) - (length/2);
		return x;
	}
}
