package me.jamboxman5.abnpgame.assets.entity.projectile.ammo;

public class ShellAmmo extends Ammo {
	
	public ShellAmmo(int ammoCount) {
		this.ammoCount = ammoCount;
		this.damageBoost = 1;
		this.speedBoost = 1;
		this.spread = .5;
		this.rangeBoost = 1;
		this.shots = 5;
	}
	
	public ShellAmmo() { this(100); }
	
	
}
