package entity;

import main.GamePanel;

public class Projectile extends Entity{

	Entity user;
	
	public Projectile(GamePanel gp) {
		super(gp);
	}
	
	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.hp = this.maxHP;
	}
	
	public void update() {
		if (user == gp.player) {
			int enemyIndex = gp.cCheck.checkEntity(this, gp.enemy);
			if (enemyIndex != -1) {
				gp.player.damageEnemy(enemyIndex, attack);
				alive = false;
			}
		} else {
			
		}
		
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
		
		hp--;
		if (hp <= 0) {
			alive = false;
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
}
