package rogue.world;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public enum Tile {
    FLOOR((char)250, AsciiPanel.yellow),
    WALL((char)177, AsciiPanel.yellow),
    BOUNDS('x', AsciiPanel.brightBlack);

    public char glyph;

    public Color color;
    
    public boolean isDiggable() {
        return this == Tile.WALL;
    }
    
    public boolean isGround() {
        return this != WALL && this != BOUNDS;
    }

    Tile(char glyph, Color color){
        this.glyph = glyph;
        this.color = color;
    }
}