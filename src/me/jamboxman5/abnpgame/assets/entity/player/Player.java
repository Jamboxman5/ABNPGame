package me.jamboxman5.abnpgame.assets.entity.player;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import me.jamboxman5.abnpgame.assets.entity.Mob;
import me.jamboxman5.abnpgame.assets.weapon.Weapon;
import me.jamboxman5.abnpgame.assets.weapon.WeaponLoadout;
import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.main.GameStage;
import me.jamboxman5.abnpgame.managers.KeyHandler;

public class Player extends Mob {
	
	private final static int defaultSpeed = 5;
	
	protected final KeyHandler keyH;
	protected Weapon activeWeapon;
	private String gamerTag;
	
	protected WeaponLoadout weapons;
		
	public Player(GamePanel gamePanel, KeyHandler keyHandler, String name) {
		super(gamePanel, 
			  name, 
			  gamePanel.getMapManager().getActiveMap().getDefaultX(), 
			  gamePanel.getMapManager().getActiveMap().getDefaultY(), 
			  defaultSpeed);

		weapons = new WeaponLoadout();
		
		keyH = keyHandler;
		gamerTag = name;
		
		screenX = gamePanel.getScreenWidth()/2 - 50;
		screenY = gamePanel.getScreenHeight()/2 - 50;
		
		setDefaults();
	}

	private void setDefaults() {		
		setSpeed(6.5);
		setRotation(0);
	}
	
	@Override
	public void update() {
		if (isMoving && gp.getZoom() >= .75) {
			gp.zoomOut();
		} else if (!isMoving && gp.getZoom() <= 1) {
			gp.zoomIn();
		}
		
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
                || keyH.isRightPressed()) {
			
			if (getAdjustedScreenX() == gp.getMousePointer().getX() &&
				getAdjustedScreenY() == gp.getMousePointer().getY()) {
				basicMove();
				isMoving = true;
				return;
			}
            
			double xComp = 0;
			double yComp = 0;
			
			if (keyH.isForwardPressed()) {
				if (keyH.isLeftPressed()) {
	            	xComp = getStrafeSpeed() * Math.cos(rotation);
					yComp = getStrafeSpeed() * Math.sin(rotation);
					xComp += (getStrafeSpeed() * Math.cos(rotation - Math.toRadians(90)));
					yComp += (getStrafeSpeed() * Math.sin(rotation - Math.toRadians(90)));
					move(xComp, yComp);
		            isMoving = true;
				} else if (keyH.isRightPressed()) {
					xComp = getStrafeSpeed() * Math.cos(rotation);
					yComp = getStrafeSpeed() * Math.sin(rotation);
					xComp += (getStrafeSpeed() * Math.cos(rotation + Math.toRadians(90)));
					yComp += (getStrafeSpeed() * Math.sin(rotation + Math.toRadians(90)));
					move(xComp, yComp);
		            isMoving = true;
				} else {
					xComp = getSpeed() * Math.cos(rotation);
					yComp = getSpeed() * Math.sin(rotation);
					move(xComp, yComp);
		            isMoving = true;
				}
			} else if (keyH.isBackPressed()) {
				if (keyH.isRightPressed()) {
					xComp = (getStrafeSpeed() * Math.cos(rotation));
					yComp = (getStrafeSpeed() * Math.sin(rotation));
					xComp += (getStrafeSpeed() * Math.cos(rotation - Math.toRadians(90)))/2;
					yComp += (getStrafeSpeed() * Math.sin(rotation - Math.toRadians(90)))/2;
					move(xComp, yComp);
		            isMoving = true;
				} else if (keyH.isLeftPressed()) {
					xComp = (getStrafeSpeed() * Math.cos(rotation));
					yComp = (getStrafeSpeed() * Math.sin(rotation));
					xComp += (getStrafeSpeed() * Math.cos(rotation + Math.toRadians(90)))/2;
					yComp += (getStrafeSpeed() * Math.sin(rotation + Math.toRadians(90)))/2;
					move(xComp, yComp);
		            isMoving = true;
				} else {
					xComp = getStrafeSpeed() * Math.cos(rotation);
					yComp = getStrafeSpeed() * Math.sin(rotation);
					move(xComp, yComp);
		            isMoving = true;
				}
			} else if (keyH.isRightPressed()) {
				xComp = getStrafeSpeed() * Math.cos(rotation - Math.toRadians(90));
				yComp = getStrafeSpeed() * Math.sin(rotation - Math.toRadians(90));
				move(xComp, yComp);
	            isMoving = true;
			} else if (keyH.isLeftPressed()) {
				xComp = getStrafeSpeed() * Math.cos(rotation + Math.toRadians(90));
				yComp = getStrafeSpeed() * Math.sin(rotation + Math.toRadians(90));
				move(xComp, yComp);
	            isMoving = true;
			} 
			
			if (xComp != 0.0 || yComp != 0.0) {
				
			} else {
				isMoving = false;
			}
            
            resetEnterPressedValue();
		} else {
			isMoving = false;
		}
	}
	
	public void basicMove() {
		
		
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
			
			int x = (int) (worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getAdjustedScreenX());
	        int y = (int) (worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getAdjustedScreenY());
						
			g2.setColor(Color.red);
			
			AffineTransform tx = new AffineTransform();
		    AffineTransform oldTrans = g2.getTransform();
			
		    tx.setToTranslation(x, y);
		    
			if (gp.getPlayer().equals(this)) {
				
				if (weapons.getActiveWeapon().hasRedDotSight()) {
					drawRedDotSight(g2, x, y);
				}
				
			    if (gp.getMousePointer().getX() == getAdjustedScreenX() &&
				   	gp.getMousePointer().getY() == getAdjustedScreenY()) {
				   	tx.rotate(0);
				} else {
					tx.rotate(0);
					tx.rotate(getDrawingAngle());
				}
			} else {
				
			}
			
		    
		    g2.transform(tx);

		    BufferedImage sprite = weapons.getActiveWeapon().getPlayerSprite();
		    if (weapons.getActiveFirearm().getName() == "M1911") {
			    g2.drawImage(sprite, (int)(-sprite.getWidth()+(37*gp.getZoom())), (int)(-sprite.getHeight()+(18*gp.getZoom())), null);
		    } else {
			    g2.drawImage(sprite, (int)(-sprite.getWidth()+(60*gp.getZoom())), (int)(-sprite.getHeight()+(18*gp.getZoom())), null);
		    }
		    g2.setTransform(oldTrans);

	}

	public void drawRedDotSight(Graphics2D g2, int x, int y) {
		Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .4f);
        Composite old = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g2.setComposite(comp);
		g2.setStroke(new BasicStroke(2));
	    g2.drawLine(x, y, (int)gp.getMousePointer().getX(), (int)gp.getMousePointer().getY());
	    g2.fillOval((int)gp.getMousePointer().getX()-2, (int)gp.getMousePointer().getY()-2, 4, 4);
	    g2.setComposite(old);
	    g2.setColor(new Color(255,200,200));
	    g2.fillOval((int)gp.getMousePointer().getX()-1, (int)gp.getMousePointer().getY()-1, 1, 1);
	}

	public double getAngleToCursor() {
		try {
			double num = getAdjustedScreenY() - gp.getMousePointer().getY();
			double denom = getAdjustedScreenX() - gp.getMousePointer().getX();
			return Math.atan(num/denom);
		} catch (NullPointerException e) {
			return 0;
		}
		
	}
	
	public double getDrawingAngle() {
		try {
			double num = getAdjustedScreenY() - gp.getMousePointer().getY();
			double denom = getAdjustedScreenX() - gp.getMousePointer().getX();
			double angle = Math.atan(num/denom);
			if ((int)gp.getMousePointer().getX() <= getAdjustedScreenX()) {
				   return angle - Math.toRadians(180);
			   } else {
				   return angle;
			   }
		} catch (NullPointerException e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	private void resetEnterPressedValue() { keyH.setEnterPressed(false); }

	public WeaponLoadout getWeaponLoadout() { return weapons; }
	public int getScreenX() { return screenX; }
	public int getScreenY() { return screenY; }
	public void setRotation(double i) { rotation = i; }
	public double getRotation() { return rotation; }
	public void setName(String newName) { gamerTag = newName; }
	public String getName() { return gamerTag; }
	
	public double getStrafeSpeed() {
		return getSpeed() *.65;
	}

	@Override
	public boolean hasCollided(double xComp, double yComp) {
		// TODO Auto-generated method stub
		return false;
	}
	public String getUsername() { return name; }

	public double getAdjustedRotation() {
		return getDrawingAngle();
	}

}
