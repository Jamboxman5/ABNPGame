package me.jamboxman5.abnpgame.assets.entity.projectile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import me.jamboxman5.abnpgame.main.GamePanel;

public class Bullet extends Projectile{

	public Bullet(double rotation, int speed, double x, double y, int range) {
		this.rotation = rotation;
		this.speed = speed;
		this.worldX = x;
		this.worldY = y;
		this.range = range;
//		System.out.println("Bullet Start x: " + getScreenX());
//		System.out.println("Bullet Start y: " + getScreenY());
	}

	@Override
	public void update() {
		if (traveled > range) GamePanel.getInstance().getMapManager().disposeProjectile(this);
		int xComp = (int) (speed * Math.cos(rotation));
		int yComp = (int) (speed * Math.sin(rotation));
		
		int[] xPoints = {(int) worldX, (int) (worldX+xComp)};
		int[] yPoints = {(int) worldY, (int) (worldY+yComp)};
		Polygon bulletCollision = new Polygon(xPoints, yPoints, 2);
		
		worldX += xComp;
		worldY += yComp;
		traveled += speed;
	}

	@Override
	public void draw(Graphics2D g2) {
		GamePanel gp = GamePanel.getInstance();
		int x = (int) (worldX - gp.getPlayer().getAdjustedWorldX() + gp.getPlayer().getAdjustedScreenX());
        int y = (int) (worldY - gp.getPlayer().getAdjustedWorldY() + gp.getPlayer().getAdjustedScreenY());
//        x *= gp.getZoom();
//        y *= gp.getZoom();
		int xComp = (int) (speed * Math.cos(rotation));
		int yComp = (int) (speed * Math.sin(rotation));
		g2.setColor(new Color(255,255,180));
		g2.setStroke(new BasicStroke(2));
		g2.drawLine(x-(xComp/2), y-(yComp/2), (int)x+xComp*2, (int)y+yComp*2);
	}
	
	public void shoot() {
		
	}

}
