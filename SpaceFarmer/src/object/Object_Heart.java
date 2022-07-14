package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Object_Heart extends Entity{
	GamePanel gp;
	
	public Object_Heart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Heart";
		type = TYPE_PICKUP;
		value = 2;
		down1 = setup("/objects/Heart_Full", gp.TILE_SIZE, gp.TILE_SIZE);
		
		image = setup("/objects/Heart_Full", gp.TILE_SIZE, gp.TILE_SIZE);
		image2 = setup("/objects/Heart_Half", gp.TILE_SIZE, gp.TILE_SIZE);
		image3 = setup("/objects/Heart_Empty", gp.TILE_SIZE, gp.TILE_SIZE);
	}
	
	public void use(Entity entity) {
		gp.playSoundEffect(2);
		gp.ui.addMessage("HP +" + value);
		entity.hp += value;
	}
}
