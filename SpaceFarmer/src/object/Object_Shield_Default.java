package object;

import entity.Entity;
import main.GamePanel;

public class Object_Shield_Default extends Entity{

	public Object_Shield_Default(GamePanel gp) {
		super(gp);
		name = "Default Shield";
		down1 = setup("/objects/Shield_Default", gp.TILE_SIZE, gp.TILE_SIZE);
		defenseValue = 1;
	}

}
