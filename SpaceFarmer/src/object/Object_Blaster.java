package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class Object_Blaster extends Projectile {
	GamePanel gp;
	
	public Object_Blaster(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Blaster";
		speed = 7;
		maxHP = 80;
		hp = maxHP;
		attack = 1;
		useCost = 2;
		alive = false;
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/projectile/Laser_Up1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/projectile/Laser_Up2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/projectile/Laser_Down1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/projectile/Laser_Down2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/projectile/Laser_Left1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/projectile/Laser_Left2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/projectile/Laser_Right1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/projectile/Laser_Right2", gp.TILE_SIZE, gp.TILE_SIZE);
	}

	public boolean hasResource(Entity user) {
		if (user.mana >= useCost) {
			return true;
		}
		return false;
	}
	
	public void subtractResource(Entity user) {
		user.mana -= useCost;
	}
}