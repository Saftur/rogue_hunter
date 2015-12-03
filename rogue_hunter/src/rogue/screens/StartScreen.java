package rogue.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import fr.FileReader;
import rogue.ApplicationMain;

public class StartScreen implements Screen {
	private char hrzchr = (char)205;
	private char vrtchr = (char)186;
	private char blcchr = (char)187;
	private char tlcchr = (char)188;
	private char trcchr = (char)200;
	private char brcchr = (char)201;
	private char tblchr = (char)185;
	private char tbrchr = (char)204;
	private char tlrchr = (char)202;
	private char blrchr = (char)203;
	
	private void displayIntro(AsciiPanel terminal) {
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
		text[n] += blcchr;//+' ';
		for (n++;n<19;n++) {
			text[n] = " "+vrtchr;
			for (int i=0;i<76;i++) {
				text[n] += ' ';
			}
			text[n] += vrtchr;//+' ';
		}
		text[n] = " "+tbrchr;
		for (int i=0;i<76;i++) {
			text[n] += hrzchr;
		}
		text[n] += tblchr;//+' ';
		n++;
		text[n] = " "+vrtchr;
		for (int i=0;i<76;i++) {
			text[n] += ' ';
		}
		text[n] += vrtchr;//+' ';
		n++;
		text[n] = " "+vrtchr;
		for (int i=0;i<76;i++) {
			text[n] += ' ';
		}
		text[n] += vrtchr;//+' ';
		n++;
		text[n] = " "+trcchr;
		for (int i=0;i<76;i++) {
			text[n] += hrzchr;
		}
		text[n] += tlcchr;//+' ';
		/*text[23] = "";
		for (int i=0;i<80;i++) {
			text[23] += ' ';
		}*/
		for (int i=0;i<text.length;i++) {
			terminal.write(text[i], 0, i, AsciiPanel.yellow);
		}
		String[] title = new String[]{"  ____                          _   _             _            ",
									  " |  _ \\ ___   __ _ _   _  ___  | | | |_   _ _ __ | |_ ___ _ __ ",
									  " | |_) / _ \\ / _` | | | |/ _ \\ | |_| | | | | '_ \\| __/ _ \\ '__|",
									  " |  _ < (_) | (_| | |_| |  __/ |  _  | |_| | | | | ||  __/ |   ",
									  " |_| \\_\\___/ \\__, |\\__,_|\\___| |_| |_|\\__,_|_| |_|\\__\\___|_|   ",
									  "             |___/                                             "};
		//String[] title = FileReader.readFile("src/rogue/name.txt");
		for (int i=0;i<title.length;i++) {
			terminal.writeCenter(title[i], i+4, AsciiPanel.red);
		}
		terminal.writeCenter("(C) Copyright 2015", 12, AsciiPanel.yellow);
		terminal.writeCenter("Artificial Dragons, an indie studio", 13, AsciiPanel.brightRed);
		terminal.writeCenter("All Rights Reserved", 14, AsciiPanel.yellow);
		terminal.writeCenter("Based on 1983 \"Rogue\" by Artificial Intelligence Design", 17);
		terminal.writeCenter("Sign your name, and enter the dungeon: ",20,AsciiPanel.green);
	}
	
	private void displayVersion(AsciiPanel terminal) {
		terminal.writeRight(ApplicationMain.version_text, 23);
	}
	
	public void displayOutput(AsciiPanel terminal) {
		displayIntro(terminal);
		displayVersion(terminal);
	}

	public Screen respondToUserInput(KeyEvent key) {
		key.getKeyChar();
		return this;
	}
	
}
