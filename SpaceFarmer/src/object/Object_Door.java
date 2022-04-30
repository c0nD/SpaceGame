package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Object_Door extends SuperObject{
	GamePanel gp;
	public Object_Door(GamePanel gp) {
		this.gp = gp;
		name = "Door";
		try {
			image = ImageIO.read(getClass().getResource("/objects/Door.png"));
			util.scaledImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		collision = true;
	}

}
