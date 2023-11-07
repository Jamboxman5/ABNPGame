package me.jamboxman5.abnpgame.assets.weapon.firearms.pistol;

import me.jamboxman5.abnpgame.assets.weapon.firearms.Firearm;
import me.jamboxman5.abnpgame.assets.weapon.mods.WeaponModLoadout;

public class Pistol1911 extends Firearm {

	public Pistol1911() {
		this(new WeaponModLoadout(), 100, 8);
	}
	
	public Pistol1911(WeaponModLoadout mods, int ammo, int loadedAmmo) {
		this.attackSound = "sfx/weapon/pistol/Pistol_Shot";
		this.attackRateMS = 350;
		this.damage = 18;
		this.equippedMods = mods;
		this.reloadSpeedMS = 1500;
		this.magSize = 8;
		this.range = 480;
		this.playerSprite = setup("/resources/entity/player/Player_Handgun", .33);
		this.hudSprite = setup("/resources/weapon/pistol/1911", .33);
		this.loaded = loadedAmmo;
		this.ammoCount = ammo;
		this.name = "M1911";
	}
	
}
