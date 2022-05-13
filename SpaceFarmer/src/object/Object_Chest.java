package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Object_Chest extends Entity{
	public Object_Chest(GamePanel gp) {
		super(gp);
		name = "Chest";
		down1 = setup("/objects/Chest", gp.TILE_SIZE, gp.TILE_SIZE);
		collision = true;
		
		
	}
}
