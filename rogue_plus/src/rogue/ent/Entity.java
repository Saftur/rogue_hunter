package rogue.ent;

import java.awt.Color;

import rogue.ent.creatures.Player;
import rogue.world.World;

public class Entity {
	public long id;
	
	protected World world;
	protected String name;
	protected Player player;
	
	public String name() {
		return name;
	}
	
	public int x;
	public int y;
	
	public char glyph;
	public Color color;
	
	public void update() { }
	
	public double distanceTo(Entity other) {
		return Math.sqrt(Math.pow(Math.abs(this.x-other.x), 2)+Math.pow(Math.abs(this.y-other.y), 2));
	}
	
	protected void moveToEmpty() {
		do {
            x = (int)(Math.random() * world.width);
            y = (int)(Math.random() * world.height);
        } 
        while (!world.tile(x,y).isGround() || world.creature(x,y) != null);
	}
	
	public Entity(World world, Player player, char glyph, Color color, String name) {
		this.world = world;
		this.player = player;
		this.glyph = glyph;
		this.color = color;
		this.name = name;
	}
}
