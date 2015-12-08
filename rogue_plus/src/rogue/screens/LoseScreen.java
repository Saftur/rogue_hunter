package rogue.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class LoseScreen implements Screen {
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
	
	private boolean select = true;
	
	public void displayFrame(AsciiPanel terminal) {
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
	
	public void displayOptions(AsciiPanel terminal) {
		terminal.writeCenter("Play Again?", 8);
		if (select) {
			terminal.write("Yes", 34, 11, null, AsciiPanel.brightBlack);
			terminal.write("No", 44, 11);
		} else {
			terminal.write("Yes         ", 34, 11);
			terminal.write("No", 44, 11, null, AsciiPanel.brightBlack);
		}
	}
	
	public void displayOutput(AsciiPanel terminal) {
		displayFrame(terminal);
		terminal.writeCenter("Game Over", 3);
		displayOptions(terminal);
	}
	
	public Screen respondToUserInput(KeyEvent key) {
		switch (key.getKeyCode()) {
		case KeyEvent.VK_LEFT: case KeyEvent.VK_RIGHT: select = select ? false : true; break;
		case KeyEvent.VK_SPACE: case KeyEvent.VK_ENTER:
			if (select)
				return new GameScreen();
			else
				return new StartScreen();
		}
		return this;
	}

}
