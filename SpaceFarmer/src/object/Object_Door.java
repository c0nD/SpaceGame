package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Object_Door extends Entity{
	public Object_Door(GamePanel gp) {
		super(gp);
		name = "Door";
		down1 = setup("/objects/Door", gp.TILE_SIZE, gp.TILE_SIZE);
		collision = true;
		
		hitBox.x = 0;
		hitBox.y = 16;
		hitBox.width = gp.TILE_SIZE;
		hitBox.height = 32;
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
	}

}
