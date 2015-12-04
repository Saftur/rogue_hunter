package rogue.creatures;

import java.awt.Color;

import rogue.RoguePlus;
import rogue.world.Tile;
import rogue.world.World;

public class Player extends Creature {
	public int level = 1;
	public int xp;
	public int xpToLvlUp = 10;
	
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
	
	public void addXp(int amount) {
		xp += amount;
		while (xp >= xpToLvlUp) {
			xp -= xpToLvlUp;
			xpToLvlUp += 10;
			maxHp += 10;
			hp += 10;
			attackValue += 5;
			defenseValue += 5;
			level++;
			world.message(name+" leveled up!");
		}
	}
	
	public Player(World world, char glyph, Color color) {
		super(world, glyph, color, 100, 20, 5, 0, RoguePlus.pname);
	}
}
