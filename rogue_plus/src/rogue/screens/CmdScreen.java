package rogue.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

import rogue.RoguePlus;
import rogue.creatures.Player;
import rogue.world.World;

public class CmdScreen implements Screen {
	private GameScreen gameScreen;
	private World world;
	private Player player;
	
	private String cmd;
	private CmdInType intype;
	private String sx;
	private String sy;
	private String sn;
	private int oldpx;
	private int oldpy;
	
	private int n() {
		try {
			return Integer.parseInt(sn);
		} catch (Exception e) {
			return 0;
		}
	}
	
	private int x() {
		try {
			return Integer.parseInt(sx);
		} catch (Exception e) {
			return 0;
		}
	}
	
	private int y() {
		try {
			return Integer.parseInt(sy);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public CmdScreen(GameScreen gameScreen, World world, Player player) {
		this.gameScreen = gameScreen;
		this.world = world;
		this.player = player;
		cmd = "";
		intype = CmdInType.CMD;
		oldpx = player.x;
		oldpy = player.y;
		sx = Integer.toString(oldpx);
		sy = Integer.toString(oldpy);
		sn = "0";
	}
	
	private void displayXY(AsciiPanel terminal) {
		terminal.write("x: "+sx, 30, 21);
		terminal.write("y: "+sy, 30, 22);
	}
	
	private void displayN(AsciiPanel terminal) {
		terminal.write("n: "+sn, 30, 21);
	}
	
	public void displayOutput(AsciiPanel terminal) {
		gameScreen.displayOutput(terminal);
		terminal.write("/"+cmd, 30, 20);
		if (intype == CmdInType.XY)
			displayXY(terminal);
		if (intype == CmdInType.N)
			displayN(terminal);
	}
	
	private boolean processCmd() {
		switch (cmd) {
		case "move": break;
		case "god":
			player.toggleGod();
			if (player.god)
				world.message("God mode activated");
			else
				world.message("God mode deactivated");
			break;
		case "hp":
			player.hp = n();
			break;
		case "atk":
			player.atk = n();
			break;
		case "def":
			player.def = n();
			break;
		case "xp":
			player.xp = n();
			break;
		default: return false;
		}
		return true;
	}

	public Screen respondToUserInput(KeyEvent key) {
		int xyinc = RoguePlus.shift ? 10 : 1;
		switch (key.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			player.x = oldpx;
			player.y = oldpy;
			return gameScreen;
		case KeyEvent.VK_ENTER: case KeyEvent.VK_SPACE: case KeyEvent.VK_TAB:
			if (intype == CmdInType.XY || intype == CmdInType.N || (intype == CmdInType.CMD && cmd.equals("god")))
				if (processCmd()) return gameScreen;
			if (intype == CmdInType.CMD) {
				switch (cmd) {
				case "move": /*case "fungus":*/ intype = CmdInType.XY; break;
				case "hp": case "atk": case "def": case "xp": intype = CmdInType.N; break;
				default: world.message("Illegal command '"+cmd+"'");
				}
			}
			break;
		case KeyEvent.VK_BACK_SPACE:
			if (intype == CmdInType.CMD) {
				if (cmd.equals(""))
					return gameScreen;
				else
					cmd = cmd.substring(0,cmd.length()-1);
			}
			if (intype == CmdInType.N) {
				if (sn.equals("0")) {
					sn = "0";
					intype = CmdInType.CMD;
				} else if (sn.length() == 1)
					sn = "0";
				else
					sn = sn.substring(0,sn.length()-1);
			}
			break;
		case KeyEvent.VK_LEFT: if (intype == CmdInType.XY) sx = Integer.toString(x()-xyinc); break;
		case KeyEvent.VK_RIGHT: if (intype == CmdInType.XY) sx = Integer.toString(x()+xyinc); break;
		case KeyEvent.VK_UP: if (intype == CmdInType.XY) sy = Integer.toString(y()-xyinc); break;
		case KeyEvent.VK_DOWN: if (intype == CmdInType.XY) sy = Integer.toString(y()+xyinc); break;
		default:
			if (intype == CmdInType.CMD) {
				char c = key.getKeyChar();
				if (Character.isLetter(c))
					cmd += c;
			}
			if (intype == CmdInType.N) {
				char c = key.getKeyChar();
				if (Character.isDigit(c)) {
					sn += c;
					sn = Integer.toString(n());
				} else if (c == '-')
					sn = Integer.toString(-n());
			}
		}
		
		if (x() > world.width-1)
			sx = Integer.toString(x()-world.width);
		if (x() < 0)
			sx = Integer.toString(x()+world.width);
		if (y() > world.height-1)
			sy = Integer.toString(y()-world.height);
		if (y() < 0)
			sy = Integer.toString(y()+world.height);
		if (intype == CmdInType.XY) {
			if (cmd.equals("move")) {
				player.x = x();
				player.y = y();
			}
		}
		
		return this;
	}
}
