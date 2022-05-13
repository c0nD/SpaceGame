package enemy;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class Enemy_Squelb extends Entity{
	GamePanel gp;
	
	
	public Enemy_Squelb(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		name = "Squelb";
		speed = 1;
		maxHP = 4;
		hp = maxHP;
		type = 2;
		
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
	}
}
