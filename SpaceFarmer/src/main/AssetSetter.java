package main;

import enemy.Enemy_Squelb;
import entity.NPC_Alien;
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
		
	}

	public void setNPC() {
		gp.npc[0] = new NPC_Alien(gp);
		gp.npc[0].worldX = gp.TILE_SIZE * 42; // Col
		gp.npc[0].worldY = gp.TILE_SIZE * 23; // Row
	}
	
	public void setEnemy() {
		gp.enemy[0] = new Enemy_Squelb(gp);
		gp.enemy[0].worldX = gp.TILE_SIZE * 24; // Col
		gp.enemy[0].worldY = gp.TILE_SIZE * 28; // Row
	}
}
