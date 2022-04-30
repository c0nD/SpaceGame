package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Object_Boots extends SuperObject{
	GamePanel gp;
	public Object_Boots(GamePanel gp) {
		this.gp = gp;
		name = "Boots";
		try {
			image = ImageIO.read(getClass().getResource("/objects/Boots.png"));
			util.scaledImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
