package rogue.world;

import rogue.world.Tile;

public class Gen {
	private Tile[][] tiles;
	private int width;
	private int height;
	
	private void cave_randomize() {
		for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
            }
        }
	}
	
	private void cave_smooth(int times) {
		Tile[][] tiles2 = new Tile[height][width];
        for (int time = 0; time < times; time++) {

         for (int y = 0; y < height; y++) {
             for (int x = 0; x < width; x++) {
              int floors = 0;
              int rocks = 0;

              for (int oy = -1; oy < 2; oy++) {
                  for (int ox = -1; ox < 2; ox++) {
                   if (x + ox < 0 || x + ox >= width || y + oy < 0
                        || y + oy >= height)
                       continue;

                   if (tiles[y + oy][x + ox] == Tile.FLOOR)
                       floors++;
                   else
                       rocks++;
                  }
              }
              tiles2[y][x] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
             }
         }
         tiles = tiles2;
        }
	}
	
	public Tile[][] caveGenerator() {
		tiles = new Tile[height][width];
		cave_randomize();
		cave_smooth(8);
		return tiles;
	}
	
	public static Tile[][] caveGen(int width, int height) {
		return new Gen(width, height).caveGenerator();
	}
	
	private static Tile[][] dungeonGen(int width, int height) {
		return null;
	}
	
	private Gen(int width, int height) {
		tiles = new Tile[height][width];
		this.width = width;
		this.height = height;
	}
	
	public static Tile[][] gen(GenType type, int width, int height) {
		if (type == GenType.CAVE) {
			return caveGen(width, height);
		} else if (type == GenType.DUNGEON) {
			return dungeonGen(width, height);
		} else {
			return null;
		}
	}
}
