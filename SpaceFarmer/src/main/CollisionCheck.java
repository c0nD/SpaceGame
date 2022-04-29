package main;

import entity.Entity;

public class CollisionCheck {

	GamePanel gp;
	
	public CollisionCheck(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.hitBox.x;
		int entityRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
		int entityTopWorldY = entity.worldY + entity.hitBox.y;
		int entityBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;
		
		int entityLeftCol = entityLeftWorldX/gp.TILE_SIZE;
		int entityRightCol = entityRightWorldX/gp.TILE_SIZE;
		int entityTopRow = entityTopWorldY/gp.TILE_SIZE;
		int entityBottomRow = entityBottomWorldY/gp.TILE_SIZE;
		
		int tileNum1, tileNum2;
		
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed) / gp.TILE_SIZE;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { // Player is hitting a solid tile
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.TILE_SIZE;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { // Player is hitting a solid tile
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.TILE_SIZE;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { // Player is hitting a solid tile
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.TILE_SIZE;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { // Player is hitting a solid tile
				entity.collisionOn = true;
			}
			break;
		}
	}
	
	// Returns the index of the object that the player collides with
	public int checkObject(Entity entity, boolean player) { 
		int index = -1;
		
		for (int i = 0; i < gp.obj.length; i++) {
			if (gp.obj[i] != null) {
				// Get the entity hitbox position
				entity.hitBox.x += entity.worldX;
				entity.hitBox.y += entity.worldY;
				// Get the object hitbox position
				gp.obj[i].hitBox.x += gp.obj[i].worldX;
				gp.obj[i].hitBox.y += gp.obj[i].worldY;
				
				switch (entity.direction) {
				case "up":
					entity.hitBox.y -= entity.speed;
					// Checks to see if the two hitboxes are touching (intersects)
					if (entity.hitBox.intersects(gp.obj[i].hitBox)) { 
						if (gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = i;
						}
					}
					break;
				case "down":
					entity.hitBox.y += entity.speed;
					if (entity.hitBox.intersects(gp.obj[i].hitBox)) {
						if (gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = i;
						}
					}
					break;
				case "left":
					entity.hitBox.x -= entity.speed;
					if (entity.hitBox.intersects(gp.obj[i].hitBox)) {
						if (gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = i;
						}
					}
					break;
				case "right":
					entity.hitBox.y += entity.speed;
					if (entity.hitBox.intersects(gp.obj[i].hitBox)) {
						if (gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if (player == true) {
							index = i;
						}
					}
					break;
				}
				// Resetting the hitboxes
				entity.hitBox.x = entity.hitBoxDefaultX;
				entity.hitBox.y = entity.hitBoxDefaultY;
				gp.obj[i].hitBox.x = gp.obj[i].hitBoxDefaultX;
				gp.obj[i].hitBox.y = gp.obj[i].hitBoxDefaultY;
			}
		}
		
		return index;
	}
}
