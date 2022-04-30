package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Object_Chest extends SuperObject{
	GamePanel gp;
	public Object_Chest(GamePanel gp) {
		this.gp = gp;
		name = "Chest";
		try {
			image = ImageIO.read(getClass().getResource("/objects/Chest.png"));
			util.scaledImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		collision = true;
	}

}
