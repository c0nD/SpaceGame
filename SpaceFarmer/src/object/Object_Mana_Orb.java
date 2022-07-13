package object;

import entity.Entity;
import main.GamePanel;

public class Object_Mana_Orb extends Entity{
	GamePanel gp;
	
	public Object_Mana_Orb(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Mana Orb";
		image = setup("/objects/Mana_Full", gp.TILE_SIZE, gp.TILE_SIZE);
		image2 = setup("/objects/Mana_Empty", gp.TILE_SIZE, gp.TILE_SIZE);
	}
	
	public boolean hasResource(Entity user) {
		if (user.ammo >= useCost) {
			return true;
		}
		return false;
	}
	
	public void subtractResource(Entity user) {
		user.ammo -= useCost;
	}
}
