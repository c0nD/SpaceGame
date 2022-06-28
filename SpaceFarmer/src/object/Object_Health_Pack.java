package object;

import entity.Entity;
import main.GamePanel;

public class Object_Health_Pack extends Entity{
	GamePanel gp;
	int value = 5;
	
	public Object_Health_Pack(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = TYPE_CONSUMABLE;
		name = "Health Pack";
		down1 = setup("/objects/Health_Pack", gp.TILE_SIZE, gp.TILE_SIZE);
		description = "[Health Pack]\nRestores " + value + " HP.";
	}
	
	public void use(Entity entity) {
		gp.gameState = gp.DIALOGUE_STATE;
		gp.ui.currentDialogue = "You consume the " + name + "!"
				+ "\nYour HP has been resored by + " + value + ".";
		entity.hp += value;
		if (gp.player.hp > gp.player.maxHP) {
			gp.player.hp = gp.player.maxHP;
		}
		gp.playSoundEffect(2);
	}
}
