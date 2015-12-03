package rogue.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class GameScreen implements Screen {
	public void displayOutput(AsciiPanel terminal) {
		
	}
	
	public Screen respondToUserInput(KeyEvent key) {
		switch (key.getKeyCode()) {
		
		}
		return this;
	}
}
