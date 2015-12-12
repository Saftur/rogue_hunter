package rogue.ent.items;

import java.awt.Color;

import rogue.ent.Entity;
import rogue.ent.creatures.Player;
import rogue.world.World;

public class Item extends Entity {
	public static boolean discovered;
	public ItemType type;
	
	protected String hiddenName;
	
	public String name() {
		return discovered ? name : hiddenName;
	}
	
	public boolean take() {
		return player.getItem(this);
	}
	
	public boolean use() {
		world.message(player.name()+" used "+name());
		return true;
	}
	
	public void throwit() {
		world.message("Cannot throw");
	}
	
	public Item(World world, Player player, char glyph, Color color, String name, String hiddenName, ItemType type) {
		super(world, player, glyph, color, name);
		this.type = type;
		this.hiddenName = hiddenName;
		/*this.world = world;
		this.player = player;
		this.glyph = glyph;
		this.color = color;
		this.name = name;*/
	}
	
	public Item(World world, Player player, char glyph, Color color, String name, ItemType type) {
		this(world, player, glyph, color, name, name, type);
	}
}
