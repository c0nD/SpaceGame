package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_Alien extends Entity{

	public NPC_Alien(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		getNPCImage();
		setDialogue();
	}

	public void getNPCImage() {
		up1 = setup("/npc/Alien_B1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/npc/Alien_B2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/npc/Alien_F1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/npc/Alien_F2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/npc/Alien_L1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/npc/Alien_L2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/npc/Alien_R1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/npc/Alien_R2", gp.TILE_SIZE, gp.TILE_SIZE);
	}
	
	public void setDialogue() {
		dialogues[0] = "Hello, my friend.";
		dialogues[1] = "What brings you to the moon?";
		dialogues[2] = "Ahh, you're on a grand mission!\nI've been living here for\nthousands of years observing Earth...\ncrazy to see a human up close..";
		dialogues[3] = "Anyways, good luck on your mission!\nIf you need anything, I'll be here.";
	}
	
	public void setAction() {
		actionLockCounter++;
		
		if (actionLockCounter == 90) {
			Random random = new Random();
			int i = random.nextInt(100)+1; // 1-100
			
			if (i <= 25) {
				direction = "up";
			}
			else if (i > 25 && i <= 50) {
				direction = "down";
			}
			else if (i > 50 && i <= 75) {
				direction = "left";
			}
			else {
				direction = "right";
			}
			actionLockCounter = 0;
		}
	}
	
	public void speak() {
		// Character specific stuff could go here (ie. having a specific item)
		
		super.speak();
	}
}