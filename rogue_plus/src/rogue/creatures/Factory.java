package rogue.creatures;

import rogue.world.World;

public class Factory {
	public static void spawn(World world, CreatureType type, int num) {
		for (int i=0;i<num;i++) {
			if (type == CreatureType.FUNGUS) {
				world.addCreature(new Fungus(world));
			}
		}
	}
}
