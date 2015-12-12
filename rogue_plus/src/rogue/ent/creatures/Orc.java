package rogue.ent.creatures;

import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import rogue.world.Tile;
import rogue.world.World;

public class Orc extends Creature {
	//private int[] lm = {0,0};
	
	public void update() {
		if (this.distanceTo(player) < 8) {
			int mx = 0;
			int my = 0;
			
			if (x > player.x)
				mx = -1;
			else if (x < player.x)
				mx = 1;
			
			if (y > player.y)
				my = -1;
			else if (y < player.y)
				my = 1;
			
			int oldmx = mx;
			int oldmy = my;
			if (world.creature(x+mx, y+my) != null && x == player.x) {
				mx = 1;
			}
			if (world.creature(x+mx, y+my) != null && x == player.x) {
				mx = -1;
			}
			if (world.creature(x+mx, y+my) != null && y == player.y) {
				my = 1;
			}
			if (world.creature(x+mx, y+my) != null && y == player.y) {
				my = -1;
			}
			if (world.creature(x+mx, y+my) != null/* || (-lm[0] == mx && -lm[1] == my)*/) {
				mx = 0;
				my = oldmy;
			}
			if (world.creature(x+mx, y+my) != null/* || (-lm[0] == mx && -lm[1] == my)*/) {
				mx = oldmx;
				my = 0;
			}
			if (world.creature(x+mx, y+my) != null/* || (-lm[0] == mx && -lm[1] == my)*/) {
				mx = oldmx;
				my = -oldmy;
			}
			if (world.creature(x+mx, y+my) != null/* || (-lm[0] == mx && -lm[1] == my)*/) {
				mx = -oldmx;
				my = oldmy;
			}
			if (world.creature(x+mx, y+my) != null/* || (-lm[0] == mx && -lm[1] == my)*/) {
				mx = 0;
				my = -oldmy;
			}
			if (world.creature(x+mx, y+my) != null/* || (-lm[0] == mx && -lm[1] == my)*/) {
				mx = -oldmx;
				my = 0;
			}
			if (world.creature(x+mx, y+my) != null/* || (-lm[0] == mx && -lm[1] == my)*/) {
				mx = -oldmx;
				my = -oldmy;
			}
			
			//lm = new int[]{mx, my};
			move(mx, my);
		} else {
			//lm = new int[]{0,0};
			List<int[]> dirs = new ArrayList<int[]>();
			for (int my=-1;my<2;my++) {
				for (int mx=-1;mx<2;mx++) {
					if ((x != 0 || y != 0) && !world.tile(x+mx, y+my).isDiggable())
						dirs.add(new int[]{mx,my});
				}
			}
			if (Math.random() <= .3) {
				int[] d = dirs.get((int)(Math.random()*dirs.size()));
				int mx = d[0];
				int my = d[1];
				move(mx, my);
			}
		}
	}
	
	public void move(int mx, int my) {
		Creature other = world.creature(x+mx, y+my);
		other = other == null ? player.x == x+mx && player.y == y+my ? player : null : other;
		if (other == null) {
			enter(x+mx, y+my);
		} else if (player.x == x+mx && player.y == y+my) {
			attack(player);
		}
	}
	
	public void enter(int x, int y) {
		Tile tile = world.tile(x, y);
		if (tile.isGround()) {
			this.x = x;
			this.y = y;
		} else if (tile.isDiggable()) {
			world.dig(x, y);
		}
	}
	
	public Orc(World world, Player player) {
		super(world, player, 'O', AsciiPanel.red, 30, 10, 10, 2, 1d, 2d, 2d, "Orc");
	}
}
