package rogue.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

import rogue.ApplicationMain;
import rogue.creatures.Creature;
import rogue.creatures.Factory;
import rogue.creatures.Player;
import rogue.world.GenType;
import rogue.world.World;
import rogue.creatures.Type;

public class GameScreen implements Screen {
	private World world;
	private Creature player;
	private int screenWidth;
	private int screenHeight;
	private int width;
	private int height;
	
	public GameScreen() {
		ApplicationMain.round_num = 0;
		screenWidth = 80;
		screenHeight = 21;
		width = 90;
		height = 31;
		world = new World(GenType.CAVE, width, height);
		Factory.spawn(world, Type.FUNGUS, 8);
		player = new Player(world, '@', AsciiPanel.brightYellow, 100, 20, 5);
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
	
	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
        int top = getScrollY();
   
        displayTiles(terminal, left, top);
        displayCreatures(terminal, left, top);
        terminal.write(player.glyph, player.x - left, player.y - top, player.color);
	}
	
	public Screen respondToUserInput(KeyEvent key) {
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
        default: ApplicationMain.round_num--;
        }
		ApplicationMain.round_num++;
		return this;
	}
}
