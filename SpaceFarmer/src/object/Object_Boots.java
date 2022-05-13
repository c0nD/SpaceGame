package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Object_Boots extends Entity{
	public Object_Boots(GamePanel gp) {
		super(gp);
		name = "Boots";
		down1 = setup("/objects/Boots", gp.TILE_SIZE, gp.TILE_SIZE);
	}
}
