package me.jamboxman5.abnpgame.assets.weapon.firearms;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import me.jamboxman5.abnpgame.assets.entity.projectile.Bullet;
import me.jamboxman5.abnpgame.assets.weapon.Weapon;
import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.util.Utilities;

public class Firearm extends Weapon {
	
	protected int magSize;
	protected int loaded;
	protected int ammoCount;
	protected int reloadSpeedMS;
	protected int range;

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
	public int getAmmoCount() { return ammoCount; }
	public void shoot() {
		if (loaded <= 0 && !reloading) {
			reload(); return;
		}
		if (!canAttack()) return;
		GamePanel gp = GamePanel.getInstance();
		gp.playSoundEffect(this.attackSound);
		this.lastAttack = System.currentTimeMillis();
		loaded -= 1;
		gp.getMapManager().addProjectile(new Bullet(gp.getPlayer().getAdjustedRotation(), 
													150, 
													gp.getPlayer().getAdjustedWorldX(), 
													gp.getPlayer().getAdjustedWorldY(),  
													150));
	}
	public void reload() {
		reloading = true;
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(reloadSpeedMS);
					loaded = magSize;
					ammoCount -= magSize;
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
}
