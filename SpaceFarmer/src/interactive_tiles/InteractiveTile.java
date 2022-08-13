package interactive_tiles;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity{

	GamePanel gp;
	public boolean destructable = false;
	
	public InteractiveTile(GamePanel gp) {
		super(gp);
		this.gp = gp;
	}
	
	public void update() {
		
	}
	
	
}
