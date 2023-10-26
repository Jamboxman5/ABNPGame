package assets.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import main.GamePanel;
import util.Utilities;

public abstract class Entity {
	
	private final GamePanel gp;
	
	private BufferedImage sprite;
	
	private double worldX, worldY;
	private String name;
	private double speed;
	
	private String direction;
	private int spriteCounter = 0;
	private int spriteNumber = 1;
	
	public Entity(GamePanel gamePanel) {
		gp = gamePanel;
		setDirection("forward");
	}

	public void setDirection(String dir) { direction = dir; }
	public void setWorldX(double x) { worldX = x; }
	public void setWorldY(double y) { worldY = y; }
	public void setSpeed(double speed) { this.speed = speed; }

	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public BufferedImage setup(String imagePath, int width, int height) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Utilities.scaleImage(image, width, height);
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
	public BufferedImage getSprite() { return sprite; }
	public double getSpeed() { return speed; }
	public double getWorldX() { return worldX; }
	public double getWorldY() { return worldY; }
	public String getDirection() { return direction; }

	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}

}
