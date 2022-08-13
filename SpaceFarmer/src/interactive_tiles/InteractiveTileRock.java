package interactive_tiles;

import main.GamePanel;

public class InteractiveTileRock extends InteractiveTile{

	GamePanel gp;
	
	public InteractiveTileRock(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		down1 = setup("/interactive_tiles/Breakable_Rock", gp.TILE_SIZE, gp.TILE_SIZE);
		destructable = true;
	}

}
