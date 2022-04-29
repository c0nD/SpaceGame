package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public int keysHeld = 0;
	int resetAnimationCounter = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = (gp.SCREEN_WIDTH / 2) - (gp.TILE_SIZE / 2);
		screenY = (gp.SCREEN_HEIGHT/ 2) - (gp.TILE_SIZE / 2);
		
		hitBox = new Rectangle(10, 18, 28, 28);
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		setDefaultStats();
		getPlayerImage();
	}
	
	public void setDefaultStats() {
		worldX = gp.TILE_SIZE * 23; // Default X spawn
		worldY = gp.TILE_SIZE * 21; // Default Y spawn
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/Astronaut_B1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/Astronaut_B2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/Astronaut_F1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/Astronaut_F2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/Astronaut_L1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/Astronaut_L2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/Astronaut_R1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/Astronaut_R2.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		// Makes sure it only updates when a key is pressed
		if (keyH.upPressed == true || keyH.downPressed == true 
				|| keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if (keyH.upPressed == true) {
	    		direction = "up";
	    	}
	    	
	    	if (keyH.downPressed == true) {
	    		direction = "down";
	    	}
	    	
	    	if (keyH.rightPressed == true) {
	    		direction = "right";
	    	}
	    	
	    	if (keyH.leftPressed == true) {
	    		direction = "left";
	    	}
	    	
	    	// Check collission
	    	collisionOn = false;
	    	gp.cCheck.checkTile(this);
	    	
	    	// Check obj collision
	    	int objIndex = gp.cCheck.checkObject(this, true);
	    	pickUpObject(objIndex);
	    	
	    	// False -> Player can move
	    	if (collisionOn == false) {
	    		switch(direction) {
	    		case "up":
	    			worldY -= speed;
	    			break;
	    		case "down":
	    			worldY += speed;
	    			break;
	    		case "left":
	    			worldX -= speed;
	    			break;
	    		case "right":
	    			worldX += speed;
	    			break;
	    		}
	    		
	    	}
	    	
	    	spriteCounter++;
	    	if (spriteCounter > 11) { // Can change number to change animation speed
	    		if (spriteState == 1) {
	    			spriteState = 2;
	    		}
	    		else if (spriteState == 2) {
	    			spriteState = 1;
	    		}
	    		spriteCounter = 0;
	    	}
		
		}
		else {
			resetAnimationCounter++;
			if (resetAnimationCounter == 25) { // (in frames)
				spriteState = 1;
				resetAnimationCounter = 0;
			};
		}
	}
	
	public void pickUpObject(int index) {
		if (index != -1) {
			String objectName = gp.obj[index].name;
			
			switch (objectName) {
			case "Key":
				gp.playSoundEffect(1);
				keysHeld++;
				gp.obj[index] = null;
				gp.ui.showMessage("You got a key!");
				break;
			case "Door":
				if (keysHeld > 0) {
					gp.playSoundEffect(3);
					gp.obj[index] = null;
					keysHeld--;
					gp.ui.showMessage("Door opened!");
				}
				else {
					gp.ui.showMessage("Key needed!");
				}
				break;
			case "Boots":
				gp.playSoundEffect(2);
				speed += 1.5;
				gp.obj[index] = null;
				gp.ui.showMessage("Speed up!");
				break;
			case "Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSoundEffect(4);
				break;
			}
		}
	}
	
	// Draws the user to the screen
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if (spriteState == 1) {
				image = up1;
			}
			if (spriteState == 2) {
				image = up2;
			}
			break;
		case "down":
			if (spriteState == 1) {
				image = down1;
			}
			if (spriteState == 2) {
				image = down2;
			}
			break;
		case "left":
			if (spriteState == 1) {
				image = left1;
			}
			if (spriteState == 2) {
				image = left2;
			}
			break;
		case "right":
			if (spriteState == 1) {
				image = right1;
			}
			if (spriteState == 2) {
				image = right2;
			}
			break;
		}
		
		g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
		
	}
}
