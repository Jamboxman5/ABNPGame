package me.jamboxman5.abnpgame.assets.weapon.firearms.shotgun;

import me.jamboxman5.abnpgame.assets.weapon.firearms.Firearm;
import me.jamboxman5.abnpgame.assets.weapon.mods.WeaponModLoadout;

public class ShotgunWinchester12 extends Firearm {

	public ShotgunWinchester12() {
		this(new WeaponModLoadout(), 100, 6);
	}
	
	public ShotgunWinchester12(WeaponModLoadout mods, int ammo, int loadedAmmo) {
		this.attackSound = "sfx/weapon/shotgun/Shotgun_Shot";
		this.attackRateMS = 1100;
		this.damage = 85;
		this.equippedMods = mods;
		this.reloadSpeedMS = 2300;
		this.magSize = 6;
		this.range = 350;
		this.playerSprite = setup("/resources/entity/player/Player_Shotgun", .33);
		this.hudSprite = setup("/resources/weapon/shotgun/Winchester12", .33);
		this.loaded = loadedAmmo;
		this.ammoCount = ammo;
		this.name = "Winchester 12GA";
	}

}
