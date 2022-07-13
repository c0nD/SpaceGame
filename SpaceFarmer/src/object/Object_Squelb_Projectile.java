package object;

import entity.Projectile;
import main.GamePanel;

public class Object_Squelb_Projectile extends Projectile {
	GamePanel gp;
	
	public Object_Squelb_Projectile(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Squelb Projectile";
		speed = 6;
		maxHP = 80;
		hp = maxHP;
		attack = 2;
		useCost = 2;
		alive = false;
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/projectile/Squelb_Projectile", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/projectile/Squelb_Projectile", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/projectile/Squelb_Projectile", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/projectile/Squelb_Projectile", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/projectile/Squelb_Projectile", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/projectile/Squelb_Projectile", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/projectile/Squelb_Projectile", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/projectile/Squelb_Projectile", gp.TILE_SIZE, gp.TILE_SIZE);
	}

}
