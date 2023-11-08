package me.jamboxman5.abnpgame.assets.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.util.Utilities;

public abstract class Entity {
	
	protected final GamePanel gp;
	
	private BufferedImage sprite;
	
	protected double worldX, worldY;
	protected String name;
	protected double speed;
	protected double rotation;
	protected Rectangle collision;
	protected int collisionWidth;
	
	protected String direction;
//	private int spriteCounter = 0;
//	private int spriteNumber = 1;
	
	public Entity(GamePanel gamePanel) {
		gp = gamePanel;
		setDirection("forward");
	}

	public void setDirection(String dir) { direction = dir; }
	public void setWorldX(double x) { worldX = x; }
	public void setWorldY(double y) { worldY = y; }
	public void setSpeed(double speed) { this.speed = speed; }

	public abstract void update();
	public abstract void draw(Graphics2D g2);
	
	public BufferedImage setup(String imagePath, double scale) {
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
	
	public void moveIfCollisionNotDetected() {
//        if (!isCollisionOn() && !gamePanel.getKeyHandler().isEnterPressed() && !gamePanel.getKeyHandler().isSpacePressed()) {
            switch (getDirection()) {
                case "forward":
                	setWorldY(getWorldY() - getSpeed());
                	break;
                case "back": 
                	setWorldY(getWorldY() + getSpeed());
                	break;
                case "left": 
                	setWorldX(getWorldX() - getSpeed());
                	break;
                case "right": 
                	setWorldX(getWorldX() + getSpeed());
                	break;
            }
        }
	
	
	public void setSprite(BufferedImage img) { sprite = img; }
	public BufferedImage getSprite() { 
		return Utilities.scaleImage(sprite, (int)(sprite.getWidth()*(gp.getZoom())), (int)(sprite.getHeight()*(gp.getZoom()))) ;
	}
	public double getSpeed() { return speed*gp.getZoom(); }
	public double getWorldX() { return worldX; }
	public double getWorldY() { return worldY; }
	public double getAdjustedWorldX() { return worldX/gp.getZoom(); }
	public double getAdjustedWorldY() { return worldY/gp.getZoom(); }
	public String getDirection() { return direction; }

	public String getName() { return name; }
	public int getScreenY() {
		return (int) (worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX());
	}
	public int getScreenX() {
		return (int) (worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY());
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

}
