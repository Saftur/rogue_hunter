package rogue.ent.creatures;

import java.awt.Color;

import rogue.ent.Entity;
import rogue.world.World;

public class Creature extends Entity {
	public double maxHp;
	public double hp;

	public double atk;
	public double def;
	
	public int xpgain;
	public double hpgain;
	public double atkgain;
	public double defgain;
	
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
        	if (attacker instanceof Player) attacker.xpUp(xpgain, hpgain, atkgain, defgain);
        } else {
        	hp -= dmg;
        }
	}
	
	public void xpUp(int xpgain, double hpgain, double atkgain, double defgain) { }
	
	public Creature(World world, Player player, char glyph, Color color, int maxHp, int attack, int defense, int xpgain, double hpgain, double atkgain, double defgain, String name) {
	    super(world, player, glyph, color, name);
		/*this.world = world;
	    this.player = player;
	    this.glyph = glyph;
	    this.color = color;*/
	    this.maxHp = maxHp;
	    this.hp = maxHp;
	    this.atk = attack;
	    this.def = defense;
	    this.xpgain = xpgain;
	    this.hpgain = hpgain;
	    this.atkgain = atkgain;
	    this.defgain = defgain;
	    //this.name = name;
		id = (long) (Math.random()*Math.pow(10, 8));
	    moveToEmpty();
	}
}
