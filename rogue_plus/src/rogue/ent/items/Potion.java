package rogue.ent.items;

import java.awt.Color;

import rogue.ent.creatures.Player;
import rogue.world.World;

public abstract class Potion extends Item {
	public Potion(World world, Player player, char glyph, Color color, String name, String hiddenName, ItemType type) {
		super(world, player, glyph, color, name, hiddenName, type);
	}
}
