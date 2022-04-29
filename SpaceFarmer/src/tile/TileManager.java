package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[10]; // Initializing how many types of tiles to be allocated
		mapTileNum = new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
		
		getTileImage();
		loadMap("/maps/world01.txt");
	}
	
	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResource("/tiles/Moon.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResource("/tiles/Soil.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResource("/tiles/Water.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResource("/tiles/Wall1.png"));
			tile[3].collision = true;
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResource("/tiles/Moon_Rock.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResource("/tiles/Moon_Path.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {
		try {
			// Allows us to read from the map file
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
				String line = br.readLine();
				
				while (col < gp.MAX_WORLD_COL) {
					// Converting string -> int
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if (col == gp.MAX_WORLD_COL) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;

		
		while (worldCol < gp.MAX_WORLD_COL && worldRow < gp.MAX_WORLD_ROW) {
			
			int tileNum = mapTileNum[worldCol][worldRow]; // extracts a tile-type from the map
			
			int worldX = worldCol * gp.TILE_SIZE;
			int worldY = worldRow * gp.TILE_SIZE;
			int screenX = worldX - gp.player.worldX + gp.player.screenX; // Returning the screen position from world and centering it
			int screenY = worldY - gp.player.worldY + gp.player.screenY; 
			
			// Optimization to not render stuff that isn't in view of the camera
			if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX && 
				worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
				worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
				worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {
					g2.drawImage(tile[tileNum].image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
				}
			worldCol++;
			
			if (worldCol == gp.MAX_WORLD_COL) {
				worldCol = 0;
				worldRow++;

			}
		}
		
	}
}
