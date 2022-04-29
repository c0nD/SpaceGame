package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle hitBox = new Rectangle(0,0,48,48); // 48 is tile size
	public int hitBoxDefaultX = 0;
	public int hitBoxDefaultY = 0;
	
	public void draw(Graphics2D g2, GamePanel gp) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX; // Returning the screen position from world and centering it
		int screenY = worldY - gp.player.worldY + gp.player.screenY; 
		
		// Optimization to not render stuff that isn't in view of the camera
		if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX && 
			worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
			worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
			worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {
				g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
		}
	}
}
