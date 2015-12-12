package rogue.ent.items;

import asciiPanel.AsciiPanel;
import rogue.ent.creatures.Player;
import rogue.world.World;

public class HealthPotion extends Potion {
	public static boolean discovered = false;
	private static String pColor;
	private double amount;
	
	public String name() {
		return discovered ? name : hiddenName;
	}
	
	public static void setPColor(String newcolor) {
		pColor = newcolor;
	}
	
	public boolean use() {
		super.use();
		double newAmount = Math.min(player.maxHp-player.hp, amount);
		world.message(player.name()+" regained "+(int)(newAmount+.5)+" hp");
		discovered = true;
		player.hp += newAmount;
		return true;
	}
	
	public HealthPotion(World world, Player player, double amount) {
		super(world, player, (char)173, AsciiPanel.brightBlue, "Health Potion", pColor+" Potion", ItemType.POTION);
		this.amount = amount;
		moveToEmpty();
	}
}
