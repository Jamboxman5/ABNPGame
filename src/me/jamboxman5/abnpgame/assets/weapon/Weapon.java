package me.jamboxman5.abnpgame.assets.weapon;

import java.awt.image.BufferedImage;

import me.jamboxman5.abnpgame.assets.weapon.firearms.pistol.Pistol1911;
import me.jamboxman5.abnpgame.assets.weapon.firearms.rifle.RifleM4A1;
import me.jamboxman5.abnpgame.assets.weapon.firearms.shotgun.ShotgunWinchester12;
import me.jamboxman5.abnpgame.assets.weapon.mods.RedDotSight;
import me.jamboxman5.abnpgame.assets.weapon.mods.WeaponMod;
import me.jamboxman5.abnpgame.assets.weapon.mods.WeaponModLoadout;
import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.util.Utilities;

public abstract class Weapon {

	protected double damage;
	protected double durability;
	protected double weight;
	protected boolean reloading = false;
	
	protected long attackRateMS;
	protected long lastAttack;
		
	public BufferedImage[] idleSprites;
	public BufferedImage[] shootSprites;
	public BufferedImage[] reloadSprites;
	public BufferedImage[] activeSprites;
			
	protected BufferedImage dropSprite;
	protected BufferedImage hudSprite;
	protected String attackSound;
	protected String name;
	protected WeaponType type;

	protected WeaponModLoadout equippedMods;
	
	public BufferedImage getPlayerSprite(int idx) {
		return Utilities.scaleImage(activeSprites[idx], 
									(int)(activeSprites[idx].getWidth()*(GamePanel.getInstance().getZoom())), 
									(int)(activeSprites[idx].getHeight()*(GamePanel.getInstance().getZoom())));
		
	}
	public BufferedImage getHudSprite() { return hudSprite; }
	public String getName() { return name; }
	public abstract void attack();
	protected boolean canAttack() {
	    if (reloading) return false;
	    if ((System.currentTimeMillis() - lastAttack) < attackRateMS) return false;
		return true;
	}
	public boolean hasRedDotSight() {
		for (WeaponMod m : equippedMods.getMods()) {
			if (m instanceof RedDotSight) return true;
		}
		return false;
	}
	public void idle() { activeSprites = idleSprites; }
	public void setMods(WeaponModLoadout mods) {
		equippedMods = mods;
	}
	
	public enum WeaponType {
		M1911, M4A1, WINCHESTER12;
	}
	public WeaponType getType() { return type; }
	public static Weapon getByType(WeaponType type) {
		switch(type) {
		case M1911:
			return new Pistol1911();
		case M4A1:
			return new RifleM4A1();
		case WINCHESTER12:
			return new ShotgunWinchester12();
		}
		return new RifleM4A1();
	}
}
