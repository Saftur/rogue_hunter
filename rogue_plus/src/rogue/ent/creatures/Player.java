package rogue.ent.creatures;

import java.util.HashMap;
import java.util.Map;

import asciiPanel.AsciiPanel;
import rogue.RoguePlus;
import rogue.ent.items.Item;
import rogue.world.Tile;
import rogue.world.World;

public class Player extends Creature {
	public int level = 1;
	public int xp;
	public int xpToLvlUp = 5;
	private double mhppts;
	private double atkpts;
	private double defpts;
	
	public Map<Character, Item> pack;
	private int packsize;
	public int gold;
	
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
        	world.message(this.name+" deflected attack from "+attacker.name());
        else {
        	world.message(attacker.name()+" attacked "+this.name()+" for "+dmg+" dmg");
        	if (hp-dmg <= 0) {
        		hp = 0;
        		world.message(attacker.name()+" defeated "+this.name());
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
	
	public boolean takeItem() {
		Item item = world.item(x, y);
		if (item != null) {
			return item.take();
			/*if (pack.size() < packsize) {
				world.message("Picked up "+item.name);
				pack.put((char)(pack.size()+97),item);
				world.remove(item);
				return true;
			} else {
				world.message("Pack is full");
				return false;
			}*/
		} else {
			world.message("No item to pick up");
			return false;
		}
	}
	
	public void removeItem(char key) {
		//for (Item item : pack.values()) System.out.println(item.name);
		int i = 0;
		boolean pastKey = false;
		for (char k : pack.keySet()) {
			if (k == key)
				pastKey = true;
			if (i == pack.size()-1)
				pack.remove(k);
			else if (pastKey)
				pack.put(k, pack.get((char)((int)k+1)));
			i++;
		}
	}
	
	public boolean getItem(Item item) {
		if (pack.size() < packsize) {
			world.message(name+" picked up "+item.name());
			pack.put((char)(pack.size()+97),item);
			world.remove(item);
			return true;
		} else {
			world.message("Pack is full");
			return false;
		}
	}
	
	public void useItem(char key) {
		if (pack.containsKey(key)) {
			if (pack.get(key).use())
				removeItem(key);
		}
	}

	public void listPack() {
		System.out.println("Pack:");
		for (char k : pack.keySet()) {
			System.out.println(k+". "+pack.get(k).name());
		}
	}
	
	public Player(World world) {
		super(world, null, (char)1, AsciiPanel.brightYellow, 100, 10, 10, 0, 0, 0, 0, RoguePlus.pname);
		pack = new HashMap<Character, Item>();
		gold = 0;
		packsize = 10;
	}
}
