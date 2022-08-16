package interactive_tiles;

import main.GamePanel;

public class InteractiveTileRock extends InteractiveTile {

	GamePanel gp;
	
	public InteractiveTileRock(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.TILE_SIZE*col;
		this.worldY = gp.TILE_SIZE*row;
		
		down1 = setup("/interactable_tiles/Breakable_Rock", gp.TILE_SIZE, gp.TILE_SIZE);
		destructable = true;
	}

}
