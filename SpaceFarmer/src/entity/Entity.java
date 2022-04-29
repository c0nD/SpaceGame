package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	public int worldX, worldY;
	public int speed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteState = 1;
	public Rectangle hitBox;
	public int hitBoxDefaultX, hitBoxDefaultY;
	public boolean collisionOn = false;
}
