package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

	GamePanel gp;
	
	
	public int hitBoxDefaultX, hitBoxDefaultY;
	String dialogues[] = new String[50];
	public boolean collision = false;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
	attackLeft1, attackLeft2, attackRight1, attackRight2;
	public Rectangle hitBox = new Rectangle(0,0,48,48);
	public Rectangle attackHitBox = new Rectangle(0,0,0,0);
	
	// Game State
	public int worldX, worldY;
	public String direction = "down";
	public int spriteState = 1;
	public boolean collisionOn = false;
	public boolean invincible = false;
	public boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	public boolean hpBarOn = false;
	
	// Counters
	public int actionLockCounter = 0;
	public int invincibleCounter = 0;
	public int spriteCounter = 0;
	public int dyingCounter = 0;
	public int hpBarCounter = 0;
	
	int dialogueIndex = 0;
	public BufferedImage image, image2, image3;
	
	// Character
	public int maxHP;
	public int hp;
	public String name;
	public int speed;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int cash;
	public Entity currentWeapon;
	public Entity currentShield;
	// Maybe add armor later
	
	// Item Attributes
	public int attackValue;
	public int defenseValue;
	public String description = "";
	
	// Type
	public int type; // 0 - player, 1 - npc, 2 - enemy, 
	public final int TYPE_PLAYER = 0;
	public final int TYPE_NPC = 1;
	public final int TYPE_ENEMY = 2;
	public final int TYPE_SABER = 3;
	public final int TYPE_HAMMER = 4;
	public final int TYPE_SHIELD = 5;
	public final int TYPE_CONSUMABLE = 6;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public void speak() {
		if (dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
			gp.ui.currentDialogue = dialogues[dialogueIndex];
			dialogueIndex++;
			
		switch(gp.player.direction) {
		case "up": direction = "down";
			break;
		case "down": direction = "up";
			break;
		case "right": direction = "left";
			break;
		case "left": direction = "right";
			break;
		}
	}
	
	public void setAction() {}
	public void damageReaction() {}
	
	public void update() {
		setAction();
		
		collisionOn = false;
		gp.cCheck.checkTile(this);
		gp.cCheck.checkObject(this, false);
		gp.cCheck.checkEntity(this, gp.npc);
		gp.cCheck.checkEntity(this, gp.enemy);
		boolean contactPlayer = gp.cCheck.checkPlayer(this);
		
		if (this.type == TYPE_ENEMY && contactPlayer) {
			if (!gp.player.invincible) {
				int damage = attack - gp.player.defense;
				if (damage < 0) damage = 0;
				gp.player.hp -= damage;
				gp.player.invincible = true;
			}
		}
		
		// Movement Collision Checker
    	if (!collisionOn) {
    		switch(direction) {
    		case "up": worldY -= speed;
    			break;
    		case "down": worldY += speed;
    			break;
    		case "left": worldX -= speed;
    			break;
    		case "right": worldX += speed;
    			break;
    		}
    		
    	}
    	spriteCounter++;
    	if (spriteCounter > 15) { // Can change number to change animation speed
    		if (spriteState == 1) {
    			spriteState = 2;
    		}
    		else if (spriteState == 2) {
    			spriteState = 1;
    		}
    		spriteCounter = 0;
    	}
    	
    	if (invincible) {
			invincibleCounter++;
			if (invincibleCounter > 35) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
			BufferedImage image = null;
			
			int screenX = worldX - gp.player.worldX + gp.player.screenX; // Returning the screen position from world and centering it
			int screenY = worldY - gp.player.worldY + gp.player.screenY; 
			
			// Optimization to not render stuff that isn't in view of the camera
			if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX && 
				worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
				worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
				worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {
					switch(direction) {
					case "up":
						if (spriteState == 1) image = up1;
						if (spriteState == 2) image = up2;
						break;
					case "down":
						if (spriteState == 1) image = down1;
						if (spriteState == 2) image = down2;
						break;
					case "left":
						if (spriteState == 1) image = left1;
						if (spriteState == 2) image = left2;
						break;
					case "right":
						if (spriteState == 1) image = right1;
						if (spriteState == 2) image = right2;
						break;
					}	
					
					// Health Bar
					if (type == 2 && hpBarOn) {
						double oneScale = (double) gp.TILE_SIZE / maxHP;
						// Fix for if you damage over the max health
						int x = hp;
						if (hp < 0) x = 0;
						double hpBarVal = oneScale * x;
						
						
						g2.setColor(new Color(20,20,20));
						g2.fillRect(screenX-1, screenY-16, gp.TILE_SIZE+2, 12);
						
						g2.setColor(new Color(255,0,30));
						g2.fillRect(screenX, screenY-15, (int) hpBarVal, 10);
						
						hpBarCounter++;
						
						if (hpBarCounter >= 600) {
							hpBarCounter = 0;
							hpBarOn = false;
						}
					}
					
					if (invincible) {
						hpBarOn = true;
						hpBarCounter = 0;
						changeAlpha(g2, 0.5F);
					}
					if (dying) {
						dyingAnimation(g2);
					}
					g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
					changeAlpha(g2, 1F);
		}
	}
	
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		
		int i = 15;
		
		if (dyingCounter <= i) spriteState = 1;
		else if (dyingCounter > i && dyingCounter <= i*2) spriteState = 2;
		else if (dyingCounter > i*3 && dyingCounter <= i*3) spriteState = 1;
		else if (dyingCounter > i*4 && dyingCounter <= i*4) spriteState = 2;
		else if (dyingCounter > i*5 && dyingCounter <= i*5) spriteState = 1;
		else if (dyingCounter > i*6 && dyingCounter <= i*6) spriteState = 2;
		else if (dyingCounter > i*7 && dyingCounter <= i*7) spriteState = 1;
		else if (dyingCounter > i*8 && dyingCounter <= i*8) spriteState = 2;
		else {
			dying = false;
			alive = false;
		}
		
		
	}
	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	
	public BufferedImage setup(String imagePath, int width, int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaledImage(image, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
