package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Object_Key extends SuperObject{
	public Object_Key() {
		name = "Key";
		try {
			image = ImageIO.read(getClass().getResource("/objects/Key.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// If needed to change hitbox size in the future, can do:
		//hitBox.x = (val);
		//hitBox.y = (val);
	}
}
