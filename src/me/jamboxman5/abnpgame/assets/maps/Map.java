package me.jamboxman5.abnpgame.assets.maps;

import java.awt.image.BufferedImage;

public class Map {

	private BufferedImage img;
	private String name;
	private final int defaultX, defaultY;
	
	public Map(String name, int x, int y) {
		this.name = name;
		defaultX = x;
		defaultY = y;
	}
	
	public BufferedImage getImage() { 
		return img;
	}
	public Map setImage(BufferedImage image) { img = image; return this; }
	public String toString() { return name; }

	public int getDefaultX() { return defaultX; }
	public int getDefaultY() { return defaultY; }

	public String getName() { return name; }
	
}
