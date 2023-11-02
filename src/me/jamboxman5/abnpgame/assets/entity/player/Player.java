package me.jamboxman5.abnpgame.assets.entity.player;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import me.jamboxman5.abnpgame.assets.entity.Entity;
import me.jamboxman5.abnpgame.assets.maps.Map;
import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.main.GameStage;
import me.jamboxman5.abnpgame.managers.KeyHandler;

public class Player extends Entity {
	
	private final KeyHandler keyH;
	private final GamePanel gp;
	private final int screenX;
	private final int screenY;
	private String gamerTag;
	double rotation;
		
	public Player(GamePanel gamePanel, KeyHandler keyHandler, String name) {
		super(gamePanel);

		keyH = keyHandler;
		gp = gamePanel;
		gamerTag = name;
		
		screenX = gamePanel.getScreenWidth()/2 - 50;
		screenY = gamePanel.getScreenHeight()/2 - 50;
		
		setDefaults();
//		setImages();
	}

	private void setImages() {
		setSprite(setup("/resources/entity/player/Player_Rifle", 100,80));
		
	}

	private void setDefaults() {
		setDefaultPosition(gp.getMapManager().getActiveMap());
		
		setSpeed(5);
		setRotation(0);
	}


	private void setDefaultPosition(Map activeMap) {
		if (activeMap.toString().equals("Black_Isle")) {
			setWorldX(950);
			setWorldY(670);
		}
	}
	
	@Override
	public void update() {
		
		setRotation(getAngleToCursor());
		
		if (keyH.isForwardPressed()) {
            setDirection("forward");
        } else if (keyH.isBackPressed()) {
            setDirection("back");
        } else if (keyH.isLeftPressed()) {
            setDirection("left");
        } else if (keyH.isRightPressed()) {
            setDirection("right");
        }
		
		if (keyH.isForwardPressed()
                || keyH.isBackPressed()
                || keyH.isLeftPressed()
                || keyH.isRightPressed()
                || keyH.isEnterPressed()
                || keyH.isSpacePressed()) {
            
            move();
            resetEnterPressedValue();
		}
	}
	
	public void move() {
		
		if (getAdjustedScreenX() == gp.getMousePointer().getX() &&
			getAdjustedScreenY() == gp.getMousePointer().getY()) {
			switch (getDirection()) {
	        case "forward":
	        	setWorldY(getWorldY() - getStrafeSpeed());
	        	break;
	        case "back": 
	        	setWorldY(getWorldY() + getStrafeSpeed());
	        	break;
	        case "left": 
	        	setWorldX(getWorldX() - getStrafeSpeed());
	        	break;
	        case "right": 
	        	setWorldX(getWorldX() + getStrafeSpeed());
	        	break;
			}
		} else {
			switch (getDirection()) {
	        case "forward":
	        	mfw();
	        	break;
	        case "back": 
	        	mbk();
	        	break;
	        case "left": 
	        	mlt();
	        	break;
	        case "right": 
	        	mrt();
	        	break;
			}
		}

		
	}
	
	public void mfw() {
		
		if (gp.getMousePointer() == null) return;

		if (getAdjustedScreenX() < gp.getMousePointer().getX()) {
			double xComp = getSpeed() * Math.cos(rotation);
			double yComp = getSpeed() * Math.sin(rotation);
			setWorldX(getWorldX() + xComp);
			setWorldY(getWorldY() + yComp);
		} else if (getAdjustedScreenX() > gp.getMousePointer().getX()) {
			double xComp = getSpeed() * Math.cos(rotation);
			double yComp = getSpeed() * Math.sin(rotation);
			setWorldX(getWorldX() - xComp);
			setWorldY(getWorldY() - yComp);
		} else if (getAdjustedScreenY() < gp.getMousePointer().getY()) {
			double xComp = getSpeed() * Math.cos(rotation);
			double yComp = getSpeed() * Math.sin(rotation);
			setWorldX(getWorldX() - xComp);
			setWorldY(getWorldY() - yComp);
		} else {
			double xComp = getSpeed() * Math.cos(rotation);
			double yComp = getSpeed() * Math.sin(rotation);
			setWorldX(getWorldX() - xComp);
			setWorldY(getWorldY() - yComp);
		}
	}
	public void mbk() {
		if (gp.getMousePointer() == null) return;

		if (getAdjustedScreenX() < gp.getMousePointer().getX()) {
			double xComp = getStrafeSpeed() * Math.cos(rotation);
			double yComp = getStrafeSpeed() * Math.sin(rotation);
			setWorldX(getWorldX() - xComp);
			setWorldY(getWorldY() - yComp);
		} else if (getAdjustedScreenX() > gp.getMousePointer().getX()) {
			double xComp = getStrafeSpeed() * Math.cos(rotation);
			double yComp = getStrafeSpeed() * Math.sin(rotation);
			setWorldX(getWorldX() + xComp);
			setWorldY(getWorldY() + yComp);
		} else if (getAdjustedScreenY() < gp.getMousePointer().getY()) {
			double xComp = getStrafeSpeed() * Math.cos(rotation);
			double yComp = getStrafeSpeed() * Math.sin(rotation);
			setWorldX(getWorldX() + xComp);
			setWorldY(getWorldY() + yComp);
		} else {
			double xComp = getStrafeSpeed() * Math.cos(rotation);
			double yComp = getStrafeSpeed() * Math.sin(rotation);
			setWorldX(getWorldX() + xComp);
			setWorldY(getWorldY() + yComp);
		}
	}
	public void mrt() {
		if (gp.getMousePointer() == null) return;

		if (getAdjustedScreenX() < gp.getMousePointer().getX()) {
			double xComp = getStrafeSpeed() * Math.cos(rotation - Math.toRadians(90));
			double yComp = getStrafeSpeed() * Math.sin(rotation - Math.toRadians(90));
			setWorldX(getWorldX() - xComp);
			setWorldY(getWorldY() - yComp);
		} else if (getAdjustedScreenX() > gp.getMousePointer().getX()) {
			double xComp = getStrafeSpeed() * Math.cos(rotation - Math.toRadians(90));
			double yComp = getStrafeSpeed() * Math.sin(rotation - Math.toRadians(90));
			setWorldX(getWorldX() + xComp);
			setWorldY(getWorldY() + yComp);
		} else if (getAdjustedScreenY() < gp.getMousePointer().getY()) {
			double xComp = getStrafeSpeed() * Math.cos(rotation - Math.toRadians(90));
			double yComp = getStrafeSpeed() * Math.sin(rotation - Math.toRadians(90));
			setWorldX(getWorldX() + xComp);
			setWorldY(getWorldY() + yComp);
		} else {
			double xComp = getStrafeSpeed() * Math.cos(rotation - Math.toRadians(90));
			double yComp = getStrafeSpeed() * Math.sin(rotation - Math.toRadians(90));
			setWorldX(getWorldX() + xComp);
			setWorldY(getWorldY() + yComp);
		}
	}
	public void mlt() {
		if (gp.getMousePointer() == null) return;

		if (getAdjustedScreenX() < gp.getMousePointer().getX()) {
			double xComp = getStrafeSpeed() * Math.cos(rotation + Math.toRadians(90));
			double yComp = getStrafeSpeed() * Math.sin(rotation + Math.toRadians(90));
			setWorldX(getWorldX() - xComp);
			setWorldY(getWorldY() - yComp);
		} else if (getAdjustedScreenX() > gp.getMousePointer().getX()) {
			double xComp = getStrafeSpeed() * Math.cos(rotation + Math.toRadians(90));
			double yComp = getStrafeSpeed() * Math.sin(rotation + Math.toRadians(90));
			setWorldX(getWorldX() + xComp);
			setWorldY(getWorldY() + yComp);
		} else if (getAdjustedScreenY() < gp.getMousePointer().getY()) {
			double xComp = getStrafeSpeed() * Math.cos(rotation + Math.toRadians(90));
			double yComp = getStrafeSpeed() * Math.sin(rotation + Math.toRadians(90));
			setWorldX(getWorldX() + xComp);
			setWorldY(getWorldY() + yComp);
		} else {
			double xComp = getStrafeSpeed() * Math.cos(rotation + Math.toRadians(90));
			double yComp = getStrafeSpeed() * Math.sin(rotation + Math.toRadians(90));
			setWorldX(getWorldX() + xComp);
			setWorldY(getWorldY() + yComp);
		}
	}
	
	@Override
	public void draw(Graphics2D g2) {
		
		if (gp.getGameStage() != GameStage.InGameSinglePlayer &&
			gp.getGameStage() != GameStage.InGameMultiplayer) return;

		
		if (gp.getMousePointer() == null) return;
				
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
		
		//DRAW PLAYER
		
		int x = getAdjustedScreenX();
		
		int y = getAdjustedScreenY();
		
		
		g2.setColor(Color.RED);
		try {
		    g2.setStroke(new BasicStroke(2));
		    g2.drawLine(x, y, (int)gp.getMousePointer().getX(), (int)gp.getMousePointer().getY());
		} catch (NullPointerException e) {
			
		}	
		
		double angle = 0;
		if (x != gp.getMousePointer().getX() &&
			y != gp.getMousePointer().getY()) {
			angle = getAngleToCursor();
		}
		
		AffineTransform tx = new AffineTransform();
	    AffineTransform old = g2.getTransform();
	    tx.setToTranslation(x, y);
	    tx.rotate(angle);
	    g2.transform(tx);
	    g2.fillRect(-20, -20, 40, 40);
	    g2.setStroke(new BasicStroke(4));
		g2.setColor(Color.white);
		g2.drawRect(-20, -20, 40, 40);
	    
		g2.setStroke(new BasicStroke());
	    g2.setTransform(old);
	    
		g2.setColor(Color.yellow);
		g2.fillOval(x-5, y-5, 10, 10);
		
		
		
	}

	

	private double getAngleToCursor() {
		try {
			double num = (gp.getHeight()/2) - 22 - gp.getMousePointer().getY();
			double denom = (gp.getWidth()/2) - gp.getMousePointer().getX();
			return Math.atan(num/denom);
		} catch (NullPointerException e) {
			return 0;
		}
		
	}

	private void resetEnterPressedValue() { keyH.setEnterPressed(false); }

	public int getScreenX() { return screenX; }
	public int getScreenY() { return screenY; }
	public void setRotation(double i) { rotation = i; }
	public double getRotation() { return rotation; }
	public void setName(String newName) { gamerTag = newName; }
	public String getName() { return gamerTag; }
	public int getAdjustedScreenX() {
		int rightOffset = gp.getScreenWidth() - screenX;
		int x = rightOffset - 50;
		return x;
	}
	public int getAdjustedScreenY() {
		int bottomOffset = gp.getScreenHeight() - screenY - 32;
		int y = bottomOffset - 40;
		return y;
	}
	public double getStrafeSpeed() {
		return getSpeed() *.65;
	}

}
