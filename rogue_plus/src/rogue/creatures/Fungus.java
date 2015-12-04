package rogue.creatures;

import asciiPanel.AsciiPanel;
import rogue.world.World;

public class Fungus extends Creature {
	public void update() {
		
	}

	public Fungus(World world) {
		super(world, 'f', AsciiPanel.green, 20, 0, 0, 1, "Fungus");
	}
}
