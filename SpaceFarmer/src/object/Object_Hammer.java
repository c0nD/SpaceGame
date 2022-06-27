package object;

import entity.Entity;
import main.GamePanel;

public class Object_Hammer extends Entity{

	public Object_Hammer(GamePanel gp) {
		super(gp);
		
		type = TYPE_HAMMER;
		name = "Astronaut's Hammer";
		down1 = setup("/objects/Hammer", gp.TILE_SIZE, gp.TILE_SIZE);
		attackHitBox.width = 30;
		attackHitBox.height = 30;
		attackValue = 2;
		description = "[" + name + "]\nA mighty hammer.";
	}

}
