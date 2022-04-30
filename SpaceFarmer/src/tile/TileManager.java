package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[50]; // Initializing how many types of tiles to be allocated
		mapTileNum = new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
		
		getTileImage();
		loadMap("/maps/world02.txt");
	}
	
	public void getTileImage() {
		// Index, Name, Collision
		//// Doing this so the map will line up better w/ 2 digit numbers
		setup(0, "Moon00", false);
		setup(1, "Moon00", false);
		setup(2, "Moon00", false);
		setup(3, "Moon00", false);
		setup(4, "Moon00", false);
		setup(5, "Moon00", false);
		setup(6, "Moon00", false);
		setup(7, "Moon00", false);
		setup(8, "Moon00", false);
		setup(9, "Moon00", false);
		
		setup(10, "Moon01", false);
		setup(11, "Moon_Rock00", true);
		setup(12, "Moon_Path", false);
		setup(13, "Moon_PathBL", false);
		setup(14, "Moon_PathBM", false);
		setup(15, "Moon_PathBR", false);
		setup(16, "Moon_PathML", false);
		setup(17, "Moon_PathMR", false);
		setup(18, "Moon_PathTL", false);
		setup(19, "Moon_PathTM", false);
		setup(20, "Moon_PathTR", false);
		setup(21, "Wall1", true);
		setup(22, "Space00", true);
		setup(23, "Space01", true);
		setup(24, "SpaceBL", true);
		setup(25, "SpaceBM", true);
		setup(26, "SpaceBR", true);
		setup(27, "SpaceML", true);
		setup(28, "SpaceMR", true);
		setup(29, "SpaceTL", true);
		setup(30, "SpaceTM", true);
		setup(31, "SpaceTR", true);
		setup(32, "Moon00", false);
	}
	
	public void setup(int index, String imageName, boolean collision) {
		UtilityTool util = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResource("/tiles/" + imageName + ".png"));
			tile[index].image = util.scaledImage(tile[index].image, gp.TILE_SIZE, gp.TILE_SIZE);
			tile[index].collision = collision;
			
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
					String numbers[] = line.split("\t");
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
					g2.drawImage(tile[tileNum].image, screenX, screenY, null);
				}
			worldCol++;
			
			if (worldCol == gp.MAX_WORLD_COL) {
				worldCol = 0;
				worldRow++;

			}
		}
		
	}
}
