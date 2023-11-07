package me.jamboxman5.abnpgame.assets.weapon;

import java.awt.image.BufferedImage;

import me.jamboxman5.abnpgame.main.GamePanel;

public class Weapon {

	protected double damage;
	protected double durability;
	protected double attackRate;
	protected double weight;
	
	protected BufferedImage playerSprite;
	protected BufferedImage dropSprite;
	
	protected GamePanel gp;
	
	public Weapon(GamePanel gamePanel) {
		gp = gamePanel;
	}
	
}
