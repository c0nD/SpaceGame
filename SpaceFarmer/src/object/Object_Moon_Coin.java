package object;

import entity.Entity;
import main.GamePanel;

public class Object_Moon_Coin extends Entity{
	GamePanel gp;
	
	public Object_Moon_Coin(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = TYPE_PICKUP;
		name = "Moon Coin";
		down1 = setup("/objects/Moon_Coin", gp.TILE_SIZE, gp.TILE_SIZE);
		value = 1;
	}

	public void use(Entity entity) {
		gp.playSoundEffect(1);
		gp.ui.addMessage("Cash +" + value);
		gp.player.cash += value;
	}
}
