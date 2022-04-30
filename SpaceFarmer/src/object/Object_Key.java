package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Object_Key extends SuperObject{
	GamePanel gp;
	
	public Object_Key(GamePanel gp) {
		this.gp = gp;
		name = "Key";
		try {
			image = ImageIO.read(getClass().getResource("/objects/Key.png"));
			util.scaledImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// If needed to change hitbox size in the future, can do:
		//hitBox.x = (val);
		//hitBox.y = (val);
	}
}
