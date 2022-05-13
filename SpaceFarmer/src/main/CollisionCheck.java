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
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) { // Player is hitting a solid tile
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.TILE_SIZE;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) { // Player is hitting a solid tile
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.TILE_SIZE;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) { // Player is hitting a solid tile
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.TILE_SIZE;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) { // Player is hitting a solid tile
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
				case "up": entity.hitBox.y -= entity.speed;
					break;
				case "down": entity.hitBox.y += entity.speed;
					break;
				case "left": entity.hitBox.x -= entity.speed;
					break;
				case "right": entity.hitBox.y += entity.speed;
					break;
				}
				// Checks to see if the two hitboxes are touching (intersects)
				if (entity.hitBox.intersects(gp.obj[i].hitBox)) { 
					if (gp.obj[i].collision) entity.collisionOn = true;
					if (player == true) index = i;
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
	
	// Check NPC/Monster collision
	public int checkEntity(Entity entity, Entity[] target) {
		int index = -1;
		
		for (int i = 0; i <target.length; i++) {
			if (target[i] != null) {
				// Get the entity hitbox position
				entity.hitBox.x += entity.worldX;
				entity.hitBox.y += entity.worldY;
				// Get the object hitbox position
				target[i].hitBox.x += target[i].worldX;
				target[i].hitBox.y += target[i].worldY;
				
				switch (entity.direction) {
				case "up": entity.hitBox.y -= entity.speed;
					break;
				case "down": entity.hitBox.y += entity.speed;
					break;
				case "left": entity.hitBox.x -= entity.speed;
					break;
				case "right": entity.hitBox.y += entity.speed;
					break;
				}
				// Checks to see if the two hitboxes are touching (intersects)
				if (entity.hitBox.intersects(target[i].hitBox)) { 
					if (target[i] != entity) {
						entity.collisionOn = true;
						index = i;
					}
				}
				
				// Resetting the hitboxes
				entity.hitBox.x = entity.hitBoxDefaultX;
				entity.hitBox.y = entity.hitBoxDefaultY;
				target[i].hitBox.x = target[i].hitBoxDefaultX;
				target[i].hitBox.y = target[i].hitBoxDefaultY;
			}
		}
		
		return index;
	}
	
	public boolean checkPlayer(Entity entity) {
		boolean contactPlayer = false;
		
		// Get the entity hitbox position
		entity.hitBox.x += entity.worldX;
		entity.hitBox.y += entity.worldY;
		// Get the object hitbox position
		gp.player.hitBox.x += gp.player.worldX;
		gp.player.hitBox.y += gp.player.worldY;
		
		switch (entity.direction) {
		case "up": entity.hitBox.y -= entity.speed;
			break;
		case "down": entity.hitBox.y += entity.speed;
			break;
		case "left": entity.hitBox.x -= entity.speed;
			break;
		case "right": entity.hitBox.y += entity.speed;
			break;
		}
		// Checks to see if the two hitboxes are touching (intersects)
		if (entity.hitBox.intersects(gp.player.hitBox)) { 
			entity.collisionOn = true;
			contactPlayer = true;
			}
		// Resetting the hitboxes
		entity.hitBox.x = entity.hitBoxDefaultX;
		entity.hitBox.y = entity.hitBoxDefaultY;
		gp.player.hitBox.x = gp.player.hitBoxDefaultX;
		gp.player.hitBox.y = gp.player.hitBoxDefaultY;
		
		return contactPlayer;
	}
}
