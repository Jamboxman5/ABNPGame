package me.jamboxman5.abnpgame.assets.weapon.firearms.rifle;

import me.jamboxman5.abnpgame.assets.weapon.firearms.Firearm;
import me.jamboxman5.abnpgame.assets.weapon.mods.WeaponModLoadout;

public class RifleM4A1 extends Firearm {

	public RifleM4A1() {
		this(new WeaponModLoadout(), 100, 30);
	}
	
	public RifleM4A1(WeaponModLoadout mods, int ammo, int loadedAmmo) {
		this.attackSound = "sfx/weapon/rifle/Assault_Rifle_Shot";
		this.attackRateMS = 86;
		this.damage = 30;
		this.equippedMods = mods;
		this.reloadSpeedMS = 2300;
		this.magSize = 30;
		this.range = 1000;
		this.playerSprite = setup("/resources/entity/player/Player_Rifle", .33);
		this.hudSprite = setup("/resources/weapon/rifle/M4A1", .42);
		this.loaded = loadedAmmo;
		this.ammoCount = ammo;
		this.name = "M4A1";
	}

}
