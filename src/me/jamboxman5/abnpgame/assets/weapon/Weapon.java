package me.jamboxman5.abnpgame.assets.weapon;

import java.awt.image.BufferedImage;

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
}
