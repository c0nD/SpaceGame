package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.Object_Key;
import object.Object_Saber_Default;
import object.Object_Shield_Default;

public class Player extends Entity {

	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	int resetAnimationCounter = 0;
	public boolean attackCanceled = false;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int MAX_INVENTORY_SIZE = 20;

	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp); // Entity constructor
		
		this.keyH = keyH;
		
		screenX = (gp.SCREEN_WIDTH / 2) - (gp.TILE_SIZE / 2);
		screenY = (gp.SCREEN_HEIGHT/ 2) - (gp.TILE_SIZE / 2);
		
		hitBox = new Rectangle(10, 18, 26, 26);
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
//		attackHitBox.width = 36;
//		attackHitBox.height = 36;
		
		setDefaultStats();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
	}
	
	public void setDefaultStats() {
		worldX = gp.TILE_SIZE * 24; // Default X spawn
		worldY = gp.TILE_SIZE * 23; // Default Y spawn

		speed = 4;
		direction = "down";
		
		// Player stats
		level = 1;
		exp = 0;
		maxHP = 6;
		hp = maxHP;
		strength = 1;
		dexterity = 1;
		nextLevelExp = 5;
		cash = 0;
		currentWeapon = new Object_Saber_Default(gp);
		currentShield = new Object_Shield_Default(gp);
		attack = getAttack();
		defense = getDefense();
	}
	
	public void setItems() {
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new Object_Key(gp));
		inventory.add(new Object_Key(gp));
		

	}
	
	public int getAttack() {
		attackHitBox = currentWeapon.attackHitBox;
		return strength * currentWeapon.attackValue;
	}
	
	public int getDefense() {
		return dexterity * currentShield.defenseValue;
	}
	
	public void getPlayerImage() {
		up1 = setup("/player/Astronaut_B1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/player/Astronaut_B2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/player/Astronaut_F1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/player/Astronaut_F2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/player/Astronaut_L1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/player/Astronaut_L2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/player/Astronaut_R1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/player/Astronaut_R2", gp.TILE_SIZE, gp.TILE_SIZE);
	}
	
	public void getPlayerAttackImage() {
		if (currentWeapon.type == TYPE_SABER) {
			attackUp1 = setup("/player/Astronaut_Attack_B1", gp.TILE_SIZE, gp.TILE_SIZE*2);
			attackUp2 = setup("/player/Astronaut_Attack_B2", gp.TILE_SIZE, gp.TILE_SIZE*2);
			attackDown1 = setup("/player/Astronaut_Attack_F1", gp.TILE_SIZE, gp.TILE_SIZE*2);
			attackDown2 = setup("/player/Astronaut_Attack_F2", gp.TILE_SIZE, gp.TILE_SIZE*2);
			attackLeft1 = setup("/player/Astronaut_Attack_L1", gp.TILE_SIZE*2, gp.TILE_SIZE);
			attackLeft2 = setup("/player/Astronaut_Attack_L2", gp.TILE_SIZE*2, gp.TILE_SIZE);
			attackRight1 = setup("/player/Astronaut_Attack_R1", gp.TILE_SIZE*2, gp.TILE_SIZE);
			attackRight2 = setup("/player/Astronaut_Attack_R2", gp.TILE_SIZE*2, gp.TILE_SIZE);
		} else if (currentWeapon.type == TYPE_HAMMER) {
			attackUp1 = setup("/player/Astronaut_Hammer_B1", gp.TILE_SIZE, gp.TILE_SIZE*2);
			attackUp2 = setup("/player/Astronaut_Hammer_B2", gp.TILE_SIZE, gp.TILE_SIZE*2);
			attackDown1 = setup("/player/Astronaut_Hammer_F1", gp.TILE_SIZE, gp.TILE_SIZE*2);
			attackDown2 = setup("/player/Astronaut_Hammer_F2", gp.TILE_SIZE, gp.TILE_SIZE*2);
			attackLeft1 = setup("/player/Astronaut_Hammer_L1", gp.TILE_SIZE*2, gp.TILE_SIZE);
			attackLeft2 = setup("/player/Astronaut_Hammer_L2", gp.TILE_SIZE*2, gp.TILE_SIZE);
			attackRight1 = setup("/player/Astronaut_Hammer_R1", gp.TILE_SIZE*2, gp.TILE_SIZE);
			attackRight2 = setup("/player/Astronaut_Hammer_R2", gp.TILE_SIZE*2, gp.TILE_SIZE);
		}
	}
	
	public void update() {
		if (attacking) {
			attack();
		}
		// Makes sure it only updates when a key is pressed
		else if (keyH.upPressed||keyH.downPressed|| keyH.leftPressed||keyH.rightPressed|| keyH.enterPressed) {
			
			if (keyH.upPressed)  direction = "up";
	    	if (keyH.downPressed)  direction = "down";
	    	if (keyH.rightPressed) direction = "right";
	    	if (keyH.leftPressed)  direction = "left";
	    	
	    	// Check collission
	    	collisionOn = false;
	    	gp.cCheck.checkTile(this);
	    	
	    	// Check obj collision
	    	int objIndex = gp.cCheck.checkObject(this, true);
	    	pickUpObject(objIndex);
	    	
	    	// Check NPC collision
	    	int npcIndex = gp.cCheck.checkEntity(this, gp.npc);
	    	interactNPC(npcIndex);
	    	
	    	// Check enemy collision
	    	int enemyIndex = gp.cCheck.checkEntity(this,  gp.enemy);
	    	contactEnemy(enemyIndex);
	    	
	    	// Check event
	    	gp.eHandler.checkEvent();
	    	
	    	// False -> Player can move
	    	if (!collisionOn && !keyH.enterPressed) {
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
	    	
	    	if (keyH.enterPressed && !attackCanceled) {
	    		gp.playSoundEffect(5);
	    		attacking = true;
	    		spriteCounter = 0;
	    	}
	    	attackCanceled = false;
	    	
	    	gp.keyH.enterPressed = false;
	    	
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
			}
		}
		// I-frame counter
		if (invincible) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	public void attack() {
		spriteCounter++;
		
		if (spriteCounter <= 5) {
			spriteState = 1;
		}
		if (spriteCounter > 5 && spriteCounter <= 25) { 
			spriteState = 2;
			// Saving current player states
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int hitBoxWidth = hitBox.width;
			int hitBoxHeight = hitBox.height;
			// Adjusting states for attack detection/area
			switch (direction) {
			case "up": worldY -= attackHitBox.height; 
			break;
			case "down": worldY += attackHitBox.height; 
			break;
			case "left": worldX -= attackHitBox.width; 
			break;
			case "right": worldX += attackHitBox.width; 
			break;
			}
			// updating hitbox
			hitBox.width = attackHitBox.width;
			hitBox.height = attackHitBox.height;
			// checking collision with an enemy
			int enemyIndex = gp.cCheck.checkEntity(this,  gp.enemy);
			damageEnemy(enemyIndex);
			
			// Restoring player state after checking collision
			worldX = currentWorldX;
			worldY = currentWorldY;
			hitBox.width = hitBoxWidth;
			hitBox.height = hitBoxHeight;
		}
		if (spriteCounter > 25){
			spriteState = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	
	public void contactEnemy(int index) {
		if (index != -1) {
			int damage = gp.enemy[index].attack - defense;
			if (damage < 0) damage = 0;
			if (!invincible && damage > 0) {
				gp.playSoundEffect(7);
				hp -= damage;
				invincible = true;
			}

		}
	}
	
	public void damageEnemy(int index) {
		if (index != -1) {
			if (!gp.enemy[index].invincible) {
				enemySound(index);
				int damage = attack - gp.enemy[index].defense;
				if (damage < 0) damage = 0;
				gp.enemy[index].hp -= damage;
					
				gp.enemy[index].invincible = true;
				gp.enemy[index].damageReaction();
				
				if (gp.enemy[index].hp > 0) {
					gp.ui.addMessage(damage + " damage!");
				}
				if (gp.enemy[index].hp <= 0) {
//					// Formula for calculating XP so it scales
//					int xpAdded = gp.enemy[index].exp * (int)(1.2 / 1 + (level - gp.enemy[index].level) + 0.2);
					
					gp.enemy[index].dying = true;
					gp.ui.addMessage("Killed " + gp.enemy[index].name + "!");
					gp.ui.addMessage("Exp +" + (int) (gp.enemy[index].exp));
					exp += gp.enemy[index].exp;
					checkLevelUp();
				}
			}
		}
	}
	
	public void checkLevelUp() {
		if (exp >= nextLevelExp) {
			level++;
			exp -= nextLevelExp; // so it carries over
			nextLevelExp *= 2;
			maxHP += 2;
			hp = maxHP;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();			
			gp.playSoundEffect(8);
			gp.gameState = gp.DIALOGUE_STATE;
			gp.ui.currentDialogue = "You are level " + level + "!\n" + "You feel envigorated!";
			
			invincible = false;
		}
	}
	
	public void selectItem() {
		int itemIndex = gp.ui.getItemSlotIndex();
		
		if (itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			
			if (selectedItem.type == TYPE_SABER || selectedItem.type == TYPE_HAMMER) {
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			if (selectedItem.type == TYPE_SHIELD) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			if (selectedItem.type == TYPE_CONSUMABLE) {
				selectedItem.use(this);
				inventory.remove(itemIndex);
			}
		}
	}
	
	public void pickUpObject(int index) {
		if (index != -1) {
			String text;
			
			if (inventory.size() != MAX_INVENTORY_SIZE) {
				inventory.add(gp.obj[index]);
				gp.playSoundEffect(1);
				text = "Got a " + gp.obj[index].name + "!";
			}
			else {
				text = "Inventory is full!";
			}
			gp.ui.addMessage(text);
			gp.obj[index] = null;
		}
	}
	
	public void interactNPC(int index) {
		if (gp.keyH.enterPressed) {
			if (index != -1) {
				attackCanceled = true;
				gp.gameState = gp.DIALOGUE_STATE;
				gp.npc[index].speak();
			}
		}
		
	}
	
	public void enemySound(int index) {
		if (index != -1) {
			if (gp.enemy[index].name == "Squelb") { // Can expand for more enemies in the future w different sounds
				gp.playSoundEffect(6);
			}
		}
	}
	
	// Draws the user to the screen
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
		case "up":
			if (attacking) {
				tempScreenY = screenY - gp.TILE_SIZE;
				if (spriteState == 1) image = attackUp1;
				if (spriteState == 2) image = attackUp2;
			}
			else {
				if (spriteState == 1) image = up1;
				if (spriteState == 2) image = up2;
			}
			break;
		case "down":
			if (attacking) {
				if (spriteState == 1) image = attackDown1;
				if (spriteState == 2) image = attackDown2;
			}
			else {
				if (spriteState == 1) image = down1;
				if (spriteState == 2) image = down2;
			}
			break;
		case "left":
			if (attacking) {
				tempScreenX = screenX - gp.TILE_SIZE;
				if (spriteState == 1) image = attackLeft1;
				if (spriteState == 2) image = attackLeft2;
			}
			else {
				if (spriteState == 1) image = left1;
				if (spriteState == 2) image = left2;
			}
			break;
		case "right":
			if (attacking) {
				if (spriteState == 1) image = attackRight1;
				if (spriteState == 2) image = attackRight2;
			}
			else {
				if (spriteState == 1) image = right1;
				if (spriteState == 2) image = right2;
			}
			break;
		}
		

		if (invincible) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
		}
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		// Reset
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
	}
}
