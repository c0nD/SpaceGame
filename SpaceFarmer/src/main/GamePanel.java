package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
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
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound music = new Sound();
    Sound soundEffects = new Sound();
    public CollisionCheck cCheck = new CollisionCheck(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    
    // Entity / Objects
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10]; // how many objs can be displayed at once
    
    
    public GamePanel() {
        
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.gray);
        this.setDoubleBuffered(true); // performance
        this.addKeyListener(keyH); 
        this.setFocusable(true);
    }
    
    public void setupGame() {
    	aSetter.setObject();
    	playMusic(0);
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
				
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval; // incrementation between frames
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
    
    // Updating information about the game
    public void update() {
    	player.update();
    }
 
    // Drawing to screen -- draws in "layers" 
    public void paintComponent(Graphics g) { 
    	super.paintComponent(g);
    	
    	Graphics2D g2 = (Graphics2D) g;
    	
    	tileM.draw(g2);
    	
    	for (int i  = 0; i < obj.length; i-=-1) {
    		if (obj[i] != null) {
    			obj[i].draw(g2, this);
    		}
    	}
    	
    	player.draw(g2);
    	
    	ui.draw(g2);
    	
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

    
