package object;

import entity.Entity;
import main.GamePanel;

public class Object_Saber_Default extends Entity{

	public Object_Saber_Default(GamePanel gp) {
		super(gp);
		
		type = TYPE_SABER;
		name = "Default Saber";
		down1 = setup("/objects/Saber_Default", gp.TILE_SIZE, gp.TILE_SIZE);
		attackValue = 1;
		attackHitBox.width = 36;
		attackHitBox.height = 36;
		description = "[" + name + "]\nA beginner saber.";
	}

}
