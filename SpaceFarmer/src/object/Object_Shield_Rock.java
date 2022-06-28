package object;

import entity.Entity;
import main.GamePanel;

public class Object_Shield_Rock extends Entity{

	public Object_Shield_Rock(GamePanel gp) {
		super(gp);
		
		type = TYPE_SHIELD;
		name = "Rock Shield";
		down1 = setup("/objects/Shield_Rock", gp.TILE_SIZE, gp.TILE_SIZE);
		defenseValue = 2;
		description = "[" + name + "]\nA spacerock shield.";
	}

}
