package enemy;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.Object_Heart;
import object.Object_Mana_Orb;
import object.Object_Moon_Coin;
import object.Object_Squelb_Projectile;

public class Enemy_Squelb extends Entity{
	GamePanel gp;
	
	
	public Enemy_Squelb(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		name = "Squelb";
		speed = 1;
		maxHP = 4;
		hp = maxHP;
		type = TYPE_ENEMY;
		attack = 2;
		defense = 0;
		exp = 10;
		projectile = new Object_Squelb_Projectile(gp);
		
		hitBox.x = 3; // 1 pixel on each size
		hitBox.y = 15; // 5 pixels from the top down
		hitBox.width = gp.TILE_SIZE - (2*hitBox.x);
		hitBox.height = gp.TILE_SIZE - hitBox.y;		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/enemy/Squelb_D1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/enemy/Squelb_D2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/enemy/Squelb_D1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/enemy/Squelb_D2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/enemy/Squelb_D1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/enemy/Squelb_D2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/enemy/Squelb_D1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/enemy/Squelb_D2", gp.TILE_SIZE, gp.TILE_SIZE);
	}

	public void setAction() {
		actionLockCounter++;
		
		if (actionLockCounter == 90) {
			Random random = new Random();
			int i = random.nextInt(100)+1; // 1-100
			
			if (i <= 25) {
				direction = "up";
			}
			else if (i > 25 && i <= 50) {
				direction = "down";
			}
			else if (i > 50 && i <= 75) {
				direction = "left";
			}
			else {
				direction = "right";
			}
			actionLockCounter = 0;
		}
		
		int i = new Random().nextInt(175)+1;
		if (i > 99 && !projectile.alive && shotAvailableCounter == 30) {
			projectile.set(worldX, worldY, direction, true, this);
			gp.projectileList.add(projectile);
			shotAvailableCounter = 0;
		}
	}
	
	public void checkDrop() {
		// Rolling for a drop
		int randNum = new Random().nextInt(100) + 1;
		
		// Setting drop
		if (randNum < 50) {
			dropItem(new Object_Moon_Coin(gp));
		}
		else if (randNum >= 50 && randNum < 75) {
			dropItem(new Object_Heart(gp));
		}
		else if (randNum >= 75 && randNum < 100) {
			dropItem(new Object_Mana_Orb(gp));
		}
		
	}
	
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction;
	}
}
