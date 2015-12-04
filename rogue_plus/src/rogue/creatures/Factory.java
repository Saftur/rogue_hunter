package rogue.creatures;

import rogue.world.World;

public class Factory {
	public static void spawn(World world, Type type, int num) {
		for (int i=0;i<num;i++) {
			if (type == Type.FUNGUS) {
				world.addCreature(new Fungus(world));
			}
		}
	}
}
