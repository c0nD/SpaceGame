package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Object_Chest extends SuperObject{
	public Object_Chest() {
		name = "Chest";
		try {
			image = ImageIO.read(getClass().getResource("/objects/Chest.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		collision = true;
	}

}
