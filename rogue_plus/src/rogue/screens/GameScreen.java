package rogue.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

import rogue.RoguePlus;
import rogue.creatures.Creature;
import rogue.creatures.Fungus;
import rogue.creatures.Orc;
import rogue.creatures.Player;
import rogue.world.GenType;
import rogue.world.World;
import rogue.creatures.CreatureType;

public class GameScreen implements Screen {
	private char hrzchr = (char)205;
	private char vrtchr = (char)186;
	private char blcchr = (char)187;
	private char tlcchr = (char)188;
	private char trcchr = (char)200;
	private char brcchr = (char)201;
	/*private char tblchr = (char)185;
	private char tbrchr = (char)204;
	private char tlrchr = (char)202;
	private char blrchr = (char)203;*/
	
	public World world;
	public Player player;
	
	private int screenWidth;
	private int screenHeight;
	private int width;
	private int height;
	private int level = 1;
	
	public GameScreen() {
		RoguePlus.round_num = 0;
		screenWidth = 80;
		screenHeight = 20;
		width = 90;
		height = 30;
		world = new World(GenType.CAVE, width, height);
		player = new Player(world);
		spawn(world, CreatureType.FUNGUS, 40);
		spawn(world, CreatureType.ORC, 5);
	}
	
	public void spawn(World world, CreatureType type, int num) {
		for (int i=0;i<num;i++) {
			if (type == CreatureType.FUNGUS) {
				world.addCreature(new Fungus(world, player));
			} else if (type == CreatureType.ORC) {
				world.addCreature(new Orc(world, player));
			}
		}
	}
	
	public int getScrollX() {
	    return Math.max(0, Math.min(player.x - screenWidth / 2, world.width - screenWidth));
	}
	
	public int getScrollY() {
	    return Math.max(0, Math.min(player.y - screenHeight / 2, world.height - screenHeight));
	}
	
	public void displayTiles(AsciiPanel terminal, int left, int top) {
		for (int y=0;y<screenHeight;y++) {
			for (int x=0;x<screenWidth;x++) {
				int nx = x+left;
				int ny = y+top;
				
				terminal.write(world.glyph(nx, ny), x, y, world.color(nx, ny));
			}
		}
	}
	
	private void displayCreatures(AsciiPanel terminal, int left, int top) {
		for (Creature creature : world.creatures) {
			if (creature.x >= left && creature.x < left+screenWidth && creature.y >= top && creature.y < top+screenHeight) {
				terminal.write(creature.glyph, creature.x - left, creature.y - top, creature.color);
			}
		}
	}
	
	private void displayMessages(AsciiPanel terminal) {
		int n = world.messages.size()-1;
		if (n > -1)
			terminal.writeRight(world.messages.get(n), 20, AsciiPanel.brightWhite);
		if (n > 0)
			terminal.writeRight(world.messages.get(n-1), 21, AsciiPanel.white);
		if (n > 1)
			terminal.writeRight(world.messages.get(n-2), 22, AsciiPanel.brightBlack);
		if (n > 2)
			terminal.writeRight(world.messages.get(n-3), 23, AsciiPanel.brightBlack);
	}
	
	private void displayStatus(AsciiPanel terminal) {
		String[] frame = new String[3];
		frame[0] = ""+brcchr;
		for (int i=0;i<9;i++) {
			frame[0] += hrzchr;
		}
		frame[0] += blcchr;
		frame[1] = ""+vrtchr;
		for (int i=0;i<9;i++) {
			frame[1] += ' ';
		}
		frame[1] += vrtchr;
		frame[2] = ""+trcchr;
		for (int i=0;i<9;i++) {
			frame[2] += hrzchr;
		}
		frame[2] += tlcchr;
		for (int i=0;i<frame.length;i++) {
			terminal.write(frame[i], 0, i+20, AsciiPanel.brightBlue);
		}
		terminal.write("Level:"+level, 1, 21, level > 30 ? AsciiPanel.brightRed : AsciiPanel.brightWhite);
		String pn = RoguePlus.pname;
		terminal.write(pn+":", 13, 20, AsciiPanel.brightCyan);
		terminal.write("HP : "+(int)player.hp+"/"+(int)player.maxHp, 13, 21, AsciiPanel.brightWhite);
		terminal.write("A/D: "+(int)player.atk+"/"+(int)player.def, 13, 22, AsciiPanel.brightWhite);
		terminal.write("Exp: "+player.level+"("+player.xp+"/"+player.xpToLvlUp+")", 13, 23, AsciiPanel.brightWhite);
		displayMessages(terminal);
	}
	
	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
        int top = getScrollY();
   
        displayTiles(terminal, left, top);
        displayCreatures(terminal, left, top);
        terminal.write(player.glyph, player.x - left, player.y - top, player.color);
        displayStatus(terminal);
	}
	
	public Screen respondToUserInput(KeyEvent key) {
		int rn_change = 0;
		switch (key.getKeyCode()){
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_H: player.move(-1, 0); break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_L: player.move( 1, 0); break;
        case KeyEvent.VK_UP:
        case KeyEvent.VK_K: player.move( 0,-1); break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_J: player.move( 0, 1); break;
        case KeyEvent.VK_Y: player.move(-1,-1); break;
        case KeyEvent.VK_U: player.move( 1,-1); break;
        case KeyEvent.VK_B: player.move(-1, 1); break;
        case KeyEvent.VK_N: player.move( 1, 1); break;
        case KeyEvent.VK_ESCAPE: return new PauseScreen(this);
        case KeyEvent.VK_SLASH: return new CmdScreen(this, world, player);
        default: rn_change--;
        }
		rn_change++;
		RoguePlus.round_num += rn_change;
		if (rn_change > 0) {
			world.update();
		}
		if (player.hp <= 0)
			return new LoseScreen();
		else
			return this;
	}
}
