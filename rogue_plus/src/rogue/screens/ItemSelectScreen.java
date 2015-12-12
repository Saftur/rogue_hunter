package rogue.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import rogue.RoguePlus;
import rogue.ent.creatures.Player;
import rogue.world.World;

public class ItemSelectScreen implements Screen {
	private Screen lastScreen;
	private Screen packScreen;
	private Player player;
	private World world;
	
	private boolean showPack;
	private String action;

	public void displayOutput(AsciiPanel terminal) {
		if (showPack) {
			packScreen.displayOutput(terminal);
		} else {
			lastScreen.displayOutput(terminal);
		}
	}

	public Screen respondToUserInput(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
			world.message("");
			return lastScreen;
		}
		char keychar = key.getKeyChar();
		//System.out.println(keychar);
		if (Character.isLetter(keychar)) {
			//System.out.println(player.pack.containsKey(keychar));
			if (player.pack.containsKey(keychar)) {
				//System.out.println(action);
				switch (action) {
				case "use": player.useItem(keychar);
				}
				world.update();
			} else {
				world.message("No item '"+keychar+"' in pack");
			}
			return lastScreen;
		} else if (key.getKeyCode() == KeyEvent.VK_8 && RoguePlus.shift) {
			showPack = !showPack;
		}
		return this;
	}
	
	public ItemSelectScreen(Screen lastScreen, Player player, World world, String action) {
		if (action != "use" && action != "put on" && action != "equip") RoguePlus.app.changeScreen(lastScreen);
		this.lastScreen = lastScreen;
		this.player = player;
		this.world = world;
		this.packScreen = new PackScreen(this, player);
		this.action = action;
		world.message("Type character corresponding to item. Press * for pack");
	}
}
