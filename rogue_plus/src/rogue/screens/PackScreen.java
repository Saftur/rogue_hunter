package rogue.screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import rogue.ent.creatures.Player;
import rogue.ent.items.ItemType;

public class PackScreen implements Screen {
	private char hrzchr = (char)205;
	private char vrtchr = (char)186;
	private char blcchr = (char)187;
	private char tlcchr = (char)188;
	private char trcchr = (char)200;
	private char brcchr = (char)201;
	
	private Screen lastScreen;
	private Player player;
	
	private List<Character> weapon;
	private List<Character> armor;
	private List<Character> rings;
	private List<Character> potions;
	private List<Character> scrolls;
	
	private int x,y;
	
	private void displayFrame(AsciiPanel terminal) {
		String text[] = new String[23];
		int n=0;
		text[n] = "";
		for (int i=0;i<79;i++) {
			text[n] += ' ';
		}
		n++;
		text[n] = " "+brcchr;
		for (int i=0;i<76;i++) {
			text[n] += hrzchr;
		}
		text[n] += blcchr;
		for (n++;n<22;n++) {
			text[n] = " "+vrtchr;
			for (int i=0;i<76;i++) {
				text[n] += ' ';
			}
			text[n] += vrtchr;
		}
		text[n] = " "+trcchr;
		for (int i=0;i<76;i++) {
			text[n] += hrzchr;
		}
		text[n] += tlcchr;
		for (int i=0;i<text.length;i++) {
			terminal.write(text[i], 0, i, AsciiPanel.yellow);
		}
	}
	
	private void displayList(AsciiPanel terminal, String name, List<Character> list) {
		y++;
		if (y == 21) {
			x += 10;
			y = 5;
		}
		terminal.write(name+":", x, ++y);
		for (char k : list) {
			if (y == 21) {
				x += 10;
				y = 5;
			}
			terminal.write(k+". "+player.pack.get(k).name(), x, ++y);
		}
	}
	
	public void displayOutput(AsciiPanel terminal) {
		displayFrame(terminal);
		terminal.writeCenter("Pack", 3);
		x = 10;
		y = 3;
		terminal.write("Equipped:", x, ++y);
		terminal.write("Weapon: none", x, ++y);
		terminal.write("Armor : none", x, ++y);
		terminal.write("Rings : Left : none", x, ++y);
		terminal.write("      : Right: none", x, ++y);
		if (weapon.size() > 0) {
			displayList(terminal, "Weapon", weapon);
		}
		if (armor.size() > 0) {
			displayList(terminal, "Armor", armor);
		}
		if (rings.size() > 0) {
			displayList(terminal, "Rings", rings);
		}
		if (potions.size() > 0) {
			displayList(terminal, "Potions", potions);
		}
		if (scrolls.size() > 0) {
			displayList(terminal, "Scrolls", scrolls);
		}
	}

	public Screen respondToUserInput(KeyEvent key) {
		return lastScreen;
	}
	
	public PackScreen(Screen lastScreen, Player player) {
		this.lastScreen = lastScreen;
		this.player = player;
		weapon = new ArrayList<Character>();
		armor = new ArrayList<Character>();
		rings = new ArrayList<Character>();
		potions = new ArrayList<Character>();
		scrolls = new ArrayList<Character>();
		for (char k : player.pack.keySet()) {
			if (player.pack.get(k).type == ItemType.WEAPON)
				weapon.add(k);
			else if (player.pack.get(k).type == ItemType.ARMOR)
				armor.add(k);
			else if (player.pack.get(k).type == ItemType.RING)
				rings.add(k);
			else if (player.pack.get(k).type == ItemType.POTION)
				potions.add(k);
			else if (player.pack.get(k).type == ItemType.SCROLL)
				scrolls.add(k);
		}
	}
}
