package main;

import java.awt.Rectangle;

public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		eventRect = new EventRect[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
		
		int col = 0;
		int row = 0;
		while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
			
			col-=-1;
			if (col == 50) {
				col = 0;
				row-=-1;
			}
		}
	}
	
	public void checkEvent() {
		// Finds pure distance between player and collision tile
		int xDist = Math.abs(gp.player.worldX - previousEventX);
		int yDist = Math.abs(gp.player.worldY - previousEventY);
		int totalDistance = Math.max(xDist, yDist);
		if (totalDistance > gp.TILE_SIZE) {
			canTouchEvent = true;
		}
		
		if (canTouchEvent) {
			if (hit(34, 21, "up")) damagePit(34, 21, gp.DIALOGUE_STATE); // col,row,direction
			if (hit(44, 21, "up")) damagePit(34, 21, gp.DIALOGUE_STATE);
			if (hit(24, 7, "up")) healingPool(24, 7, gp.DIALOGUE_STATE);
		}
	}
	
	public boolean hit(int col, int row, String requiredDirection) {
		boolean hit = false;
		
		gp.player.hitBox.x += gp.player.worldX;
		gp.player.hitBox.y += gp.player.worldY;
		eventRect[col][row].x += col*gp.TILE_SIZE;
		eventRect[col][row].y += row*gp.TILE_SIZE;
		
		if (gp.player.hitBox.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
			if (gp.player.direction.contentEquals(requiredDirection) || requiredDirection.contentEquals("any")) {
				hit = true;
				
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
		}
		// Resetting
		gp.player.hitBox.x = gp.player.hitBoxDefaultX;
		gp.player.hitBox.y = gp.player.hitBoxDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		
		return hit;
	}
	
	public void damagePit(int col, int row, int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You fell into a pit!";
		gp.player.hp -= 1;
//		eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}
	
	public void healingPool(int col, int row, int gameState) {
		if (gp.keyH.enterPressed) {
			gp.gameState = gameState;
			gp.player.attackCanceled = true;
			gp.ui.currentDialogue = "You absorb the cosmic rays.\nYou feel rejuvinated.";
			gp.player.hp = gp.player.maxHP;
		}
	}
}
