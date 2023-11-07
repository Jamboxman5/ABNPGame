package me.jamboxman5.abnpgame.assets.weapon;

import java.awt.image.BufferedImage;
import java.util.List;

import me.jamboxman5.abnpgame.assets.weapon.mods.WeaponMod;

public class Weapon {

	protected double damage;
	protected double durability;
	protected double attackRate;
	protected double weight;
	
	protected BufferedImage playerSprite;
	protected BufferedImage dropSprite;

	protected List<WeaponMod> equippedMods;
	
}
