package rogue.creatures;

import java.awt.Color;

import rogue.world.World;

public class Creature {
	protected World world;
	public String name;
	
	public int x;
	public int y;
	
	public char glyph;
	public Color color;
	
	public int maxHp;
	public int hp;

	public int attackValue;
	public int defenseValue;
	
	public int xpgain;
	
	public void enter(int x, int y) { }
	
	public void move(int mx, int my) { }
	
	public void update() { }
	
	public void attack(Creature defender){
		defender.defend(this);
    }
	
	public void defend(Creature attacker) {
        int dmg = Math.max(0, attacker.attackValue - defenseValue);
    
        dmg = (int)(Math.random() * dmg) + 1;

        world.message(attacker.name+" attacked "+this.name+" for "+dmg+" dmg");
        if (hp-dmg <= 0) {
        	hp = 0;
        	world.remove(this);
        	attacker.addXp(xpgain);
        } else {
        	hp -= dmg;
        }
	}
	
	public void addXp(int amount) { }
	
	public Creature(World world, char glyph, Color color, int maxHp, int attack, int defense, int xpgain, String name){
	    this.world = world;
	    this.glyph = glyph;
	    this.color = color;
	    this.maxHp = maxHp;
	    this.hp = maxHp;
	    this.attackValue = attack;
	    this.defenseValue = defense;
	    this.xpgain = xpgain;
	    this.name = name;
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
