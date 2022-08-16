package main;

import enemy.Enemy_Squelb;
import entity.NPC_Alien;
import interactive_tiles.InteractiveTileRock;
import object.Object_Boots;
import object.Object_Chest;
import object.Object_Door;
import object.Object_Hammer;
import object.Object_Health_Pack;
import object.Object_Heart;
import object.Object_Key;
import object.Object_Mana_Orb;
import object.Object_Moon_Coin;
import object.Object_Shield_Rock;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		gp.obj[0] = new Object_Moon_Coin(gp);
		gp.obj[0].worldX = gp.TILE_SIZE*30;
		gp.obj[0].worldY = gp.TILE_SIZE*23;
		
		gp.obj[1] = new Object_Hammer(gp);
		gp.obj[1].worldX = gp.TILE_SIZE*24;
		gp.obj[1].worldY = gp.TILE_SIZE*16;
		
		gp.obj[2] = new Object_Shield_Rock(gp);
		gp.obj[2].worldX = gp.TILE_SIZE*24;
		gp.obj[2].worldY = gp.TILE_SIZE*18;
	}

	public void setNPC() {
		gp.npc[0] = new NPC_Alien(gp);
		gp.npc[0].worldX = gp.TILE_SIZE * 42; // Col
		gp.npc[0].worldY = gp.TILE_SIZE * 23; // Row
		
		gp.npc[0] = new NPC_Alien(gp);
		gp.npc[0].worldX = gp.TILE_SIZE * 43; // Col
		gp.npc[0].worldY = gp.TILE_SIZE * 23; // Row
	}
	
	public void setEnemy() {
		gp.enemy[0] = new Enemy_Squelb(gp);
		gp.enemy[0].worldX = gp.TILE_SIZE * 24; // Col
		gp.enemy[0].worldY = gp.TILE_SIZE * 28; // Row
	}
	// 22 39
	public void setInteractiveTile() {
		gp.interactiveTiles[0] = new InteractiveTileRock(gp, 22, 39);
	}
}
