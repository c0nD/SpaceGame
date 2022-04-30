package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.Object_Key;

public class UI {
	GamePanel gp;
	Font font, font_END;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter;
	public boolean gameFinished = false;
	double playTime;
	DecimalFormat df = new DecimalFormat("#0.0");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		font = new Font("System", Font.PLAIN, 40);
		font_END = new Font("System", Font.BOLD, 75);
		Object_Key key = new Object_Key(gp);
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		if (gameFinished == true) {
			g2.setFont(font);
			g2.setColor(Color.white);
			
			String text = "You've found the treasure!";
			int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			int x = gp.SCREEN_WIDTH/2 - textLength/2;
			int y = gp.SCREEN_HEIGHT/2 - (gp.TILE_SIZE * 3);
			g2.drawString(text, x, y);
			
			text = "Your time is: " + df.format(playTime) + "s!";
			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.SCREEN_WIDTH/2 - textLength/2;
			y = gp.SCREEN_HEIGHT/2 + (gp.TILE_SIZE * 3);
			g2.drawString(text, x, y);
			
			g2.setFont(font_END);
			g2.setColor(Color.yellow);
			text = "Congratulations!";
			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.SCREEN_WIDTH/2 - textLength/2;
			y = gp.SCREEN_HEIGHT/2 + (gp.TILE_SIZE * 2);
			g2.drawString(text, x, y);
			
			gp.gameThread = null;
		}
		else {
			g2.setFont(font);
			g2.setColor(Color.WHITE);
			g2.drawImage(keyImage,  gp.TILE_SIZE/2, gp.TILE_SIZE/2, gp.TILE_SIZE, gp.TILE_SIZE, null);
			g2.drawString("x "+gp.player.keysHeld, 74, 65);
			
			// Time
			playTime += (double) 1/60;
			g2.drawString("Time:"+ df.format(playTime), gp.TILE_SIZE*12, 65);
			
			// Message
			if (messageOn == true) {
				g2.setFont(g2.getFont().deriveFont(30F));
				g2.drawString(message,  gp.TILE_SIZE/2, gp.TILE_SIZE * 11);
				
				messageCounter++;
				
				if (messageCounter > 120) { // In frames
					messageCounter = 0;
					messageOn = false;
				}
			}
		}
	}
}
