package rogue.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import rogue.ent.creatures.Creature;
import rogue.ent.items.Item;
import rogue.world.Tile;

public class World {
	private Tile[][] tiles;
	public int width;
	public int height;
	
	public List<Creature> creatures;
	public List<Item> items;
	
	public List<String> messages;
	
	public World(GenType type, int width, int height) {
		this.width = width;
		this.height = height;
		tiles = Gen.gen(type, width, height);
		creatures = new ArrayList<Creature>();
		items = new ArrayList<Item>();
		messages = new ArrayList<String>();
	}
	
	public void update() {
		for (int i=0;i < creatures.size();i++) {
			creatures.get(i).update();
		}
	}
    
    public Tile tile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return tiles[y][x];
    }

	public char glyph(int x, int y){
		return tile(x, y).glyph;
	}
	
	public Color color(int x, int y){
		return tile(x, y).color;
	}
    
    public Creature creature(int x, int y){
        for (Creature c : creatures){
            if (c.x == x && c.y == y)
                return c;
        }
        return null;
    }
    
	public Item item(int x, int y){
    	for (Item i : items){
        	if (i.x == x && i.y == y)
            	return i;
    	}
    	return null;
	}
    
    public void dig(int x, int y) {
        if (tile(x,y).isDiggable())
            tiles[y][x] = Tile.FLOOR;
    }
    
    public void addCreature(Creature creature) {
        creatures.add(creature);
    }
    
    public void addItem(Item item) {
    	items.add(item);
    }

	public void remove(Creature creature) {
		creatures.remove(creature);
	}
	
	public void remove(Item item) {
		items.remove(item);
	}
	
	public void message(String text) {
		messages.add(text);
	}
}
