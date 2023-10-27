package me.jamboxman5.abnpgame.assets.maps;

import java.awt.image.BufferedImage;

public class Map {

	private BufferedImage img;
	private String name;
	
	public Map(String name) {
		this.name = name;
	}
	
	public BufferedImage getImage() { return img; }
	public Map setImage(BufferedImage image) { img = image; return this; }
	public String toString() { return name; }
	
}
