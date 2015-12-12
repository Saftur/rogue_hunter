package rogue.ent;

import rogue.ent.creatures.Creature;
import rogue.ent.creatures.Player;
import rogue.ent.items.ItemName;
import rogue.ent.items.Potion;
import rogue.ent.items.Gold;
import rogue.ent.items.HealthPotion;
import rogue.ent.items.Item;
import rogue.world.World;

public class Factory {
	private World world;
	private Player player;
	
//	public void spawn(CreatureType type, int num) {
//		for (int i=0;i<num;i++) {
//			if (type == CreatureType.FUNGUS) {
//				world.addCreature(new Fungus(world, player));
//			} else if (type == CreatureType.ORC) {
//				world.addCreature(new Orc(world, player));
//			}
//		}
//	}
	
	public void spawn(Class<?> type, int num) {
		for (int i=0;i<num;i++) {
			try {
				world.addCreature((Creature)type.getConstructor(new Class[] {World.class, Player.class}).newInstance(world, player));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void additem(Class<?> type, int num, int low, int high) {
		for (int i=0;i<num;i++) {
			if (Potion.class.isAssignableFrom(type)) {
				try {
					world.addItem((Item)type.getConstructor(new Class[] {World.class, Player.class, double.class}).newInstance(world, player, (int)(Math.random()*(high-low)+low)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (Gold.class.isAssignableFrom(type)) {
				try {
					world.addItem((Item)type.getConstructor(new Class[] {World.class, Player.class, int.class}).newInstance(world, player, (int)(Math.random()*(high-low)+low)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void additem(Class<?> type, int num, double amount) {
		for (int i=0;i<num;i++) {
			if (Potion.class.isAssignableFrom(type)) {
				try {
					world.addItem((Item)type.getConstructor(new Class[] {World.class, Player.class, double.class}).newInstance(world, player, amount));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (Gold.class.isAssignableFrom(type)) {
				try {
					world.addItem((Item)type.getConstructor(new Class[] {World.class, Player.class, int.class}).newInstance(world, player, (int)amount));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void additem(Class<?> type, int num) {
		additem(type, num, 0);
	}
	
	public void additem(ItemName type, int num) {
		for (int i=0;i<num;i++) {
			/*if (type == ItemType.POTION) {
				world.addItem(new Potion(world, player, (int)(Math.random()*20)));
			}*/
		}
	}
	
	public void addpotion(int num, int value) {
		for (int i=0;i<num;i++) {
			world.addItem(new HealthPotion(world, player, value));
		}
	}
	
	public void addpotion(int num, int low, int high) {
		for (int i=0;i<num;i++) {
			world.addItem(new HealthPotion(world, player, (int)(Math.random()*(high-low)+low)));
		}
	}
	
	public Factory(World world, Player player) {
		this.world = world;
		this.player = player;
	}
}
