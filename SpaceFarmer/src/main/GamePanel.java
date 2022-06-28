package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import entity.Entity;
import entity.NPC_Alien;
import entity.Player;
import tile.TileManager;
import java.util.*;

public class GamePanel extends JPanel implements Runnable {
    // Screen Settings
    final int ORIGINAL_TILE_SIZE = 16; //16x16 tile -- default size of any sprite/tile
    final int SCALE = 3;
    
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48 tile
    public final int MAX_SCREEN_COL = 16;
    public final int MAX_SCREEN_ROW = 12;
    
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 786 pixels
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels
    
    // World Settings -- Current max map size is 50x50
    public final int MAX_WORLD_COL = 50; 
    public final int MAX_WORLD_ROW = 50;
    
    // FPS
    int FPS = 60;
    
    // Instances
    
    // System
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffects = new Sound();
    public CollisionCheck cCheck = new CollisionCheck(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;
    
    
    // Entity / Objects
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10]; // how many objs can be displayed at once
    public Entity npc[] = new Entity[10]; // How many npcs can be displayed at once
    public Entity enemy[] = new Entity[25]; // how many monsters can be displayed at once
    ArrayList<Entity> entityList = new ArrayList<>();
    

    
    // Game State
    public int gameState;
    public final int TITLE_STATE = 0;
    public final int PLAY_STATE = 1;
    public final int PAUSE_STATE = 2;
    public final int DIALOGUE_STATE = 3;
    public final int CHARACTER_STATE = 4;
    
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.gray);
        this.setDoubleBuffered(true); // performance
        this.addKeyListener(keyH); 
        this.setFocusable(true);
        setFocusTraversalKeysEnabled(false); // allows me to use tabs for control
    }
    
    public void setupGame() {
    	aSetter.setObject();
    	aSetter.setNPC();
    	aSetter.setEnemy();
//    	playMusic(0);
    	gameState = TITLE_STATE;
    }

    public void startGameThread() {
    	gameThread = new Thread(this); // passing GamePanel to thread constructor -- making instance of thread
    	gameThread.start();
    }
    
    
    // Main Game Loop
    @Override
    public void run() {
    	double drawInterval = 1000000000/FPS;
    	double nextDrawTime = System.nanoTime() + drawInterval;
    	
    	while (gameThread != null) {
    		update();
    		repaint();
    		
    		// New Frame Calculation
    		try {
    			double remainingTime = nextDrawTime - System.nanoTime(); // Time until next frame
    			remainingTime /= 1000000; // sleep accepts milli
				if (remainingTime < 0) remainingTime = 0;
				Thread.sleep((long) remainingTime);
				nextDrawTime += drawInterval; // incrementation between frames
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
    
    // Updating information about the game
    public void update() {
    	if (gameState == PLAY_STATE) {
    		// Player
    		player.update();
    		// NPC
    		for (int i = 0; i < npc.length; i++) {
    			if (npc[i] != null) {
    				npc[i].update();
    			}
    		}
    		// Enemy
	    	for (int i = 0; i < enemy.length; i++) {
				if (enemy[i] != null) {
					if (enemy[i].alive == true && enemy[i].dying == false) {
						enemy[i].update();
					} 
					if (enemy[i].alive == false){
						enemy[i] = null;	
					}
				}
			}
    	}
    	if (gameState == PAUSE_STATE) {
    		// do nothing
    	}
    }
 
    // Drawing to screen -- draws in "layers" 
    public void paintComponent(Graphics g) { 
    	long drawStart = System.nanoTime();
    	super.paintComponent(g);
    	
    	Graphics2D g2 = (Graphics2D) g;
    	
    	// Title
    	if (gameState == TITLE_STATE) {
    		ui.draw(g2);
    	}
    	else {
    		//     Tiles
        	tileM.draw(g2);
        	//     Entities
        	// Player
        	entityList.add(player);
        	// NPCs
        	for (int i = 0; i < npc.length; i++) {
        		if (npc[i] != null) 
        			entityList.add(npc[i]);
        	}
        	// Objects
        	for (int i = 0; i < obj.length; i++) {
        		if (obj[i] != null) 
        			entityList.add(obj[i]);
        	}
        	// Enemies
        	for (int i = 0; i < enemy.length; i++) {
        		if (enemy[i] != null) 
        			entityList.add(enemy[i]);
        	}
        	// Sorting
        	Collections.sort(entityList, new Comparator<Entity>() {
				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY,  e2.worldY);
					return result;
				}
        		
        	});
        	// Drawing entities
        	for (int i = 0; i < entityList.size(); i++) {
        		entityList.get(i).draw(g2);
        	}
        	entityList.clear();
        	
        	//     UI
        	ui.draw(g2);
    	}
    	
    	// Debug
    	if (keyH.showDebugText == true) {
    		long drawEnd = System.nanoTime();
    		long passed = drawEnd - drawStart;
    		
    		g2.setFont(new Font("Arial", Font.PLAIN, 20));
    		g2.setColor(Color.white);
    		int x = 10;
    		int y = 400;
    		int lineHeight = 20;
    		
    		g2.drawString("WorldX: " + player.worldX, x, y); y += lineHeight;
    		g2.drawString("WorldY: " + player.worldY, x, y); y += lineHeight;
    		g2.drawString("WorldCol: " + (player.worldX + player.hitBox.x) / TILE_SIZE, x, y); y += lineHeight;
    		g2.drawString("WorldRow: " + (player.worldY + player.hitBox.y) / TILE_SIZE, x, y); y += lineHeight;
    		
    		g2.drawString("Draw Time: "+ passed, x, y);
    		System.out.println("Draw Time: "+ passed); 
    	}
    	
    	g2.dispose();
    }
    
    public void playMusic(int index) {
    	music.setFile(index);
    	music.play();
    	music.loop();
    }
    
    public void stopMusic() {
    	music.stop();
    }
    
    public void playSoundEffect(int index) {
    	soundEffects.setFile(index);
    	soundEffects.play();
    }
}

    
