package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Object_Key extends Entity{
	public Object_Key(GamePanel gp) {
		super(gp);
		name = "Key";
		down1 = setup("/objects/Key", gp.TILE_SIZE, gp.TILE_SIZE);
	}
}
