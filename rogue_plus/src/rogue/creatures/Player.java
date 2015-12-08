package rogue.creatures;

import asciiPanel.AsciiPanel;
import rogue.RoguePlus;
import rogue.world.Tile;
import rogue.world.World;

public class Player extends Creature {
	public int level = 1;
	public int xp;
	public int xpToLvlUp = 5;
	private double mhppts;
	private double atkpts;
	private double defpts;
	public boolean god;
	
	public void toggleGod() {
		god = !god;
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
	
	public void move(int mx, int my) {
		Creature other = world.creature(x+mx, y+my);
		if (other == null) {
			enter(x+mx,y+my);
		} else {
			attack(other);
		}
	}
	
	public void defend(Creature attacker) {
        int dmg = Math.max(0, (int)attacker.atk - (int)def);
    
        dmg = (int)(Math.random() * dmg) + 1;
        
        if (god)
        	world.message(this.name+" deflected attack from "+attacker.name);
        else {
        	world.message(attacker.name+" attacked "+this.name+" for "+dmg+" dmg");
        	if (hp-dmg <= 0) {
        		hp = 0;
        		world.message(attacker.name+" defeated "+this.name);
        		attacker.xpUp(xpgain, hpgain, atkgain, defgain);
        	} else {
        		hp -= dmg;
        	}
        }
	}
	
	public void xpUp(int xpgain, double hpgain, double atkgain, double defgain) {
		xp += xpgain;
		mhppts += hpgain;
		atkpts += atkgain;
		defpts += defgain;
		while (xp >= xpToLvlUp) {
			double factor = Math.pow(4d/5d, level-1)/level;
			maxHp += mhppts*factor;
			hp += mhppts*factor;
			mhppts -= mhppts;
			atk += atkpts*factor;
			atkpts -= atkpts;
			def += defpts*factor;
			defpts -= defpts;
			xp -= xpToLvlUp;
			xpToLvlUp += 5;
			level++;
			world.message(name+" leveled up!");
		}
	}
	
	public Player(World world) {
		super(world, null, (char)1, AsciiPanel.brightYellow, 100, 5, 5, 0, 0, 0, 0, RoguePlus.pname);
	}
}
