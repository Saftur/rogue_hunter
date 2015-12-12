package rogue.ent.items;

import asciiPanel.AsciiPanel;
import rogue.ent.creatures.Player;
import rogue.world.World;

public class Gold extends Item {
	public int value;
	
	public boolean take() {
		world.message(player.name()+" picked up "+value+" gold");
		player.gold += value;
		world.remove(this);
		return true;
	}

	public Gold(World world, Player player, int value) {
		super(world, player, '*', AsciiPanel.brightYellow, "Gold", ItemType.GOLD);
		this.value = value;
		moveToEmpty();
	}

}
