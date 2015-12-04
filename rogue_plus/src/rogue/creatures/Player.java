package rogue.creatures;

import java.awt.Color;

import rogue.world.Tile;
import rogue.world.World;

public class Player extends Creature {
	public void enter(int x, int y) {
		Tile tile = world.tile(x, y);
		if (tile.isGround()) {
			this.x = x;
			this.y = y;
		} else if (tile.isDiggable()) {
			world.dig(x, y);
		}
	}
	
	public void move(int mx, int my) {
		Creature other = world.creature(x+mx, y+my);
		if (other == null) {
			enter(x+mx,y+my);
		} else {
			attack(other);
		}
	}
	
	public Player(World world, char glyph, Color color, int maxHp, int attack, int defense) {
		super(world, glyph, color, maxHp, attack, defense);
	}
}
