package me.jamboxman5.abnpgame.assets.weapon.firearms;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import me.jamboxman5.abnpgame.assets.entity.projectile.ammo.Ammo;
import me.jamboxman5.abnpgame.assets.weapon.Weapon;
import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.util.Utilities;

public class Firearm extends Weapon {
	
	protected int magSize;
	protected int loaded;
	protected int reloadSpeedMS;
	protected int range;
	protected int firingVelocity;
	
	protected String reloadSound;
	
	protected Ammo currentAmmo;

	protected BufferedImage setup(String imagePath, double scale) {
        BufferedImage image = null;

        try {
        	InputStream src = getClass().getResourceAsStream("/me/jamboxman5/abnpgame" + imagePath + ".png");
            image = ImageIO.read(src);

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println(imagePath);
        }

        return Utilities.scaleImage(image, (int)(image.getWidth() * scale), (int)(image.getHeight() * scale));
    }
	public int getLoadedAmmo() { return loaded; }
	public int getAmmoCount() { return currentAmmo.getAmmoCount(); }
	public void shoot() {
		if (loaded <= 0 && !reloading) {
			reload(); return;
		}
		if (!canAttack()) return;
		GamePanel gp = GamePanel.getInstance();
		gp.playSoundEffect(this.attackSound);
		this.lastAttack = System.currentTimeMillis();
		loaded -= 1;
		currentAmmo.shoot(gp.getPlayer().getAdjustedRotation(), 
						  this, 
						  (int)gp.getPlayer().getAdjustedWorldX(), 
						  (int)gp.getPlayer().getAdjustedWorldY());
//		Bullet bullet = new Bullet(gp.getPlayer().getAdjustedRotation(), 
//				150, 
//				gp.getPlayer().getAdjustedWorldX(), 
//				gp.getPlayer().getAdjustedWorldY(),  
//				150);
//		bullet.shoot();
	}
	public void reload() {
		reloading = true;
		GamePanel.getInstance().playSoundEffect(this.reloadSound);
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(reloadSpeedMS);
					loaded = magSize;
					currentAmmo.remove(magSize);
					reloading = false;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				
			}
		}.start();
	}
	
	@Override
	public void attack() {
		shoot();
	}
	public int getFiringVelocity() { return firingVelocity; }
	public int getRange() { return range; }
}
