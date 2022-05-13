package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Object_Heart extends Entity{
	public Object_Heart(GamePanel gp) {
		super(gp);
		name = "Heart";
		image = setup("/objects/Heart_Full", gp.TILE_SIZE, gp.TILE_SIZE);
		image2 = setup("/objects/Heart_Half", gp.TILE_SIZE, gp.TILE_SIZE);
		image3 = setup("/objects/Heart_Empty", gp.TILE_SIZE, gp.TILE_SIZE);
	}
}
