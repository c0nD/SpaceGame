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
		gp.obj[0] = new Object_Key(gp);
		gp.obj[0].worldX = 36 * gp.TILE_SIZE; // Col (-1)
		gp.obj[0].worldY = 17 * gp.TILE_SIZE; // Row (-1)
		
		gp.obj[1] = new Object_Key(gp);
		gp.obj[1].worldX = 15 * gp.TILE_SIZE;
		gp.obj[1].worldY = 10 * gp.TILE_SIZE;
		
		gp.obj[2] = new Object_Key(gp);
		gp.obj[2].worldX = 43 * gp.TILE_SIZE;
		gp.obj[2].worldY = 42 * gp.TILE_SIZE;
		
		gp.obj[3] = new Object_Door(gp);
		gp.obj[3].worldX = 22 * gp.TILE_SIZE;
		gp.obj[3].worldY = 39 * gp.TILE_SIZE;
		
		gp.obj[4] = new Object_Door(gp);
		gp.obj[4].worldX = 14 * gp.TILE_SIZE;
		gp.obj[4].worldY = 39 * gp.TILE_SIZE;
		
		gp.obj[5] = new Object_Door(gp);
		gp.obj[5].worldX = 9 * gp.TILE_SIZE;
		gp.obj[5].worldY = 30 * gp.TILE_SIZE;
		
		gp.obj[6] = new Object_Chest(gp);
		gp.obj[6].worldX = 9 * gp.TILE_SIZE;
		gp.obj[6].worldY = 27 * gp.TILE_SIZE;
		
		gp.obj[7] = new Object_Boots(gp);
		gp.obj[7].worldX = 24 * gp.TILE_SIZE;
		gp.obj[7].worldY = 8 * gp.TILE_SIZE;
	}
}
