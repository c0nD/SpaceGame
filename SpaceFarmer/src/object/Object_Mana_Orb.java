package object;

import entity.Entity;
import main.GamePanel;

public class Object_Mana_Orb extends Entity{
	GamePanel gp;
	
	public Object_Mana_Orb(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = TYPE_PICKUP;
		name = "Mana Orb";
		value = 1;
		down1 = setup("/objects/Mana_Full", gp.TILE_SIZE, gp.TILE_SIZE);
		image = setup("/objects/Mana_Full", gp.TILE_SIZE, gp.TILE_SIZE);
		image2 = setup("/objects/Mana_Empty", gp.TILE_SIZE, gp.TILE_SIZE);
	}
	
	public void use(Entity entity) {
		gp.playSoundEffect(2);
		gp.ui.addMessage("Mana +" + value);
		entity.mana += value;
	}
}
