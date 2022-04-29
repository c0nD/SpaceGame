package main;

import object.Object_Boots;
import object.Object_Chest;
import object.Object_Door;
import object.Object_Key;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		gp.obj[0] = new Object_Key();
		gp.obj[0].worldX = 44 * gp.TILE_SIZE; // Col (-1)
		gp.obj[0].worldY = 30 * gp.TILE_SIZE; // Row (-1)
		
		gp.obj[1] = new Object_Key();
		gp.obj[1].worldX = 24 * gp.TILE_SIZE;
		gp.obj[1].worldY = 8 * gp.TILE_SIZE;
		
		gp.obj[2] = new Object_Door();
		gp.obj[2].worldX = 44 * gp.TILE_SIZE;
		gp.obj[2].worldY = 5 * gp.TILE_SIZE;
		
		gp.obj[3] = new Object_Door();
		gp.obj[3].worldX = 46 * gp.TILE_SIZE;
		gp.obj[3].worldY = 10 * gp.TILE_SIZE;
		
		gp.obj[4] = new Object_Chest();
		gp.obj[4].worldX = 46 * gp.TILE_SIZE;
		gp.obj[4].worldY = 16 * gp.TILE_SIZE;
		
		gp.obj[5] = new Object_Boots();
		gp.obj[5].worldX = 8 * gp.TILE_SIZE;
		gp.obj[5].worldY = 29 * gp.TILE_SIZE;
	}
}
