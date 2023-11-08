package me.jamboxman5.abnpgame.assets.weapon.firearms.shotgun;

import me.jamboxman5.abnpgame.assets.entity.projectile.ammo.Ammo;
import me.jamboxman5.abnpgame.assets.entity.projectile.ammo.ShellAmmo;
import me.jamboxman5.abnpgame.assets.weapon.firearms.Firearm;
import me.jamboxman5.abnpgame.assets.weapon.mods.WeaponModLoadout;

public class ShotgunWinchester12 extends Firearm {

	public ShotgunWinchester12() {
		this(new WeaponModLoadout(), new ShellAmmo(), 6);
	}
	
	public ShotgunWinchester12(WeaponModLoadout mods, Ammo ammo, int loadedAmmo) {
		this.attackSound = "sfx/weapon/shotgun/Shotgun_Shot";
		this.reloadSound = "sfx/weapon/rifle/Assault_Rifle_Reload";
		this.attackRateMS = 1100;
		this.damage = 85;
		this.equippedMods = mods;
		this.reloadSpeedMS = 2300;
		this.magSize = 6;
		this.range = 350;
		this.playerSprite = setup("/resources/entity/player/Player_Shotgun", .33);
		this.hudSprite = setup("/resources/weapon/shotgun/Winchester12", .33);
		this.loaded = loadedAmmo;
		this.currentAmmo = ammo;
		this.name = "Winchester 12GA";
		this.firingVelocity = 150;

	}

}
