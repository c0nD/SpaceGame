package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Object_Boots extends SuperObject{
	public Object_Boots() {
		name = "Boots";
		try {
			image = ImageIO.read(getClass().getResource("/objects/Boots.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
