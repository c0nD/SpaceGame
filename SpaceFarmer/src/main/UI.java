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
import java.util.ArrayList;

import entity.Entity;
import entity.NPC_Alien;
import object.Object_Heart;
import object.Object_Key;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font MaruMonica;
	ArrayList<String> message = new ArrayList<String>();
	ArrayList<Integer> messageCounter = new ArrayList<Integer>();
	public boolean messageOn = false;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	BufferedImage heart_full, heart_half, heart_empty;
	public int slotCol = 0;
	public int slotRow = 0;
	
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
	
	public void addMessage(String text) {
		message.add(text);
		messageCounter.add(0);
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
			drawMessage();
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
		else if (gp.gameState == gp.CHARACTER_STATE) {
			drawCharacterScreen();
			drawInventory();
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
	
	public void drawMessage() {
		int messageX = gp.TILE_SIZE;
		int messageY = gp.TILE_SIZE*4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
		
		for (int i = 0; i < message.size(); i++) {
			if (message.get(i) != null) {
				
				g2.setColor(new Color(10,10,10));
				g2.drawString(message.get(i), messageX+2, messageY+2);
				g2.setColor(Color.WHITE);
				g2.drawString(message.get(i), messageX, messageY);
				
				//arraylist++
				int counter = messageCounter.get(i) + 1;
				messageCounter.set(i, counter);
				messageY += 40;
				
				if (messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
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
	
	public void drawCharacterScreen() {
		// Frame
		final int FRAME_X = gp.TILE_SIZE*2;
		final int FRAME_Y = gp.TILE_SIZE;
		final int FRAME_WIDTH = gp.TILE_SIZE*5;
		final int FRAME_HEIGHT = gp.TILE_SIZE*10;
		
		drawSubWindow(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);
		
		// Text
		g2.setColor(Color.BLACK);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX = FRAME_X + 20;
		int textY = FRAME_Y + gp.TILE_SIZE;
		final int LINE_HEIGHT = 36; // font size
		
		g2.drawString("Level", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("HP", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Strength", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Dexterity", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Attack", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Defense", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("EXP", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("EXP Needed", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Cash", textX, textY);
		textY += LINE_HEIGHT+20;
		g2.drawString("Weapon", textX, textY);
		textY += LINE_HEIGHT+15;
		g2.drawString("Shield", textX, textY);
		textY += LINE_HEIGHT;
		
		// Values
		int tailX = (FRAME_X + FRAME_WIDTH) - 30;
		textY = FRAME_Y + gp.TILE_SIZE; // reset y
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXAlignRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.hp + "/" + gp.player.maxHP);
		textX = getXAlignRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.strength);
		textX = getXAlignRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.dexterity);
		textX = getXAlignRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.attack);
		textX = getXAlignRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.defense);
		textX = getXAlignRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.exp);
		textX = getXAlignRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXAlignRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		value = String.valueOf(gp.player.cash);
		textX = getXAlignRight(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.TILE_SIZE, textY-14, null);
		textY += gp.TILE_SIZE;
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.TILE_SIZE, textY-14, null);

	}
	
	public void drawInventory() {
		int frameX = gp.TILE_SIZE*9;
		int frameY =  gp.TILE_SIZE;
		int frameWidth = gp.TILE_SIZE*6;
		int frameHeight = gp.TILE_SIZE*5;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// Slot
		final int SLOT_X_START = frameX + 20;
		final int SLOT_Y_START = frameY + 20;
		int slotX = SLOT_X_START;
		int slotY = SLOT_Y_START;
		int slotSize = gp.TILE_SIZE + 3;
		
		// Draw Items
		for (int i = 0; i < gp.player.inventory.size(); i++) {
			
			// Equip Cursor
			if (gp.player.inventory.get(i) == gp.player.currentWeapon ||
					gp.player.inventory.get(i) == gp.player.currentShield) {
				g2.setColor(new Color(217, 228, 255));
				g2.fillRoundRect(slotX, slotY, gp.TILE_SIZE, gp.TILE_SIZE, 10, 10);
			}
			
			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
			slotX += slotSize;
			
			if (i == 4 || i == 9 || i == 14) {
				slotX = SLOT_X_START;
				slotY += slotSize;
			}
		}
		
		// Cursor
		int cursorX = SLOT_X_START + (slotSize * slotCol);
		int cursorY = SLOT_Y_START + (slotSize * slotRow);
		int cursorWidth = gp.TILE_SIZE;
		int cursorHeight = gp.TILE_SIZE;
		
		// Draw cursor
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
		// Description frame
		int descFrameX = frameX;
		int descFrameY = frameY + frameHeight;
		int descFrameHeight = gp.TILE_SIZE*3;
		int descFrameWidth = frameWidth;
		// Description text
		int textX = descFrameX + 20;
		int textY = descFrameY + gp.TILE_SIZE;
		g2.setFont(g2.getFont().deriveFont(28F));
		int itemIndex = getItemSlotIndex();
		if (itemIndex < gp.player.inventory.size()) {
			drawSubWindow(descFrameX, descFrameY, descFrameWidth, descFrameHeight);
			for (String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
				g2.drawString(line, textX, textY);
				textY += gp.TILE_SIZE;
			}
			
			
		}
	}
	
	public int getItemSlotIndex() {
		int itemIndex = slotCol + (slotRow*5);
		return itemIndex;
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
	
	public int getXAlignRight(String text, int tailX) {

		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
}
