package rogue.ent.creatures;

import asciiPanel.AsciiPanel;
import rogue.world.World;

public class Fungus extends Creature {
	public Fungus(World world, Player player) {
		super(world, player, 'f', AsciiPanel.green, 15, 0, 0, 1, 1d, 1d, .5d, "Fungus");
	}
}
