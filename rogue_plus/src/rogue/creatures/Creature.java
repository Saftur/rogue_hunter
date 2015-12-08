package rogue.creatures;

import java.awt.Color;

import rogue.world.World;

public class Creature {
	public long id;
	
	protected World world;
	public String name;
	
	public int x;
	public int y;
	
	public char glyph;
	public Color color;
	
	public double maxHp;
	public double hp;

	public double atk;
	public double def;
	
	public int xpgain;
	public double hpgain;
	public double atkgain;
	public double defgain;
	
	protected Player player;
	
	public void update() { }
	
	public void enter(int x, int y) { }
	
	public void move(int mx, int my) { }
	
	public void attack(Creature defender){
		defender.defend(this);
    }
	
	public void defend(Creature attacker) {
        int dmg = Math.max(0, (int)attacker.atk - (int)def);
    
        dmg = (int)(Math.random() * dmg) + 1;

        world.message(attacker.name+" attacked "+this.name+" for "+dmg+" dmg");
        if (hp-dmg <= 0) {
        	hp = 0;
        	world.remove(this);
        	world.message(attacker.name+" defeated "+this.name);
        	attacker.xpUp(xpgain, hpgain, atkgain, defgain);
        } else {
        	hp -= dmg;
        }
	}
	
	public double distanceTo(Creature other) {
		return Math.sqrt(Math.pow(Math.abs(this.x-other.x), 2)+Math.pow(Math.abs(this.y-other.y), 2));
	}
	
	public void xpUp(int xpgain, double hpgain, double atkgain, double defgain) { }
	
	public Creature(World world, Player player, char glyph, Color color, int maxHp, int attack, int defense, int xpgain, double hpgain, double atkgain, double defgain, String name) {
	    this.world = world;
	    this.player = player;
	    this.glyph = glyph;
	    this.color = color;
	    this.maxHp = maxHp;
	    this.hp = maxHp;
	    this.atk = attack;
	    this.def = defense;
	    this.xpgain = xpgain;
	    this.hpgain = hpgain;
	    this.atkgain = atkgain;
	    this.defgain = defgain;
	    this.name = name;
		id = (long) (Math.random()*Math.pow(10, 8));
	    moveToEmpty();
	}
	
	protected void moveToEmpty() {
		do {
            x = (int)(Math.random() * world.width);
            y = (int)(Math.random() * world.height);
        } 
        while (!world.tile(x,y).isGround() || world.creature(x,y) != null);
	}
}
