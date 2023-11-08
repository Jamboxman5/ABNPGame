package me.jamboxman5.abnpgame.assets.entity.projectile.ammo;

import me.jamboxman5.abnpgame.assets.entity.projectile.Bullet;
import me.jamboxman5.abnpgame.assets.weapon.firearms.Firearm;
import me.jamboxman5.abnpgame.main.GamePanel;

public abstract class Ammo {

	protected double damageBoost;
	protected double speedBoost;
	protected double rangeBoost;
	protected double spread;
	protected int shots;
	protected int ammoCount;
	
	public void shoot(double rotation, Firearm weapon, int startX, int startY) {
		for (int i = 0; i < shots; i++) {
			double spreadRandom = (Math.random()/15) * spread;
			if (Math.random() > .5) spreadRandom = -spreadRandom;
			rotation += spreadRandom;
			Bullet bullet = new Bullet(rotation, 
									   (int)(weapon.getFiringVelocity() * speedBoost), 
									   startX, startY, 
									   (int)(weapon.getRange() * rangeBoost));
			GamePanel.getInstance().getMapManager().addProjectile(bullet);
		}
	}

	public int getAmmoCount() { return ammoCount; }

	public void remove(int magSize) { ammoCount -= magSize; }
	
}
