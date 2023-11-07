package me.jamboxman5.abnpgame.assets.entity.player;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.net.InetAddress;

import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.main.GameStage;
import me.jamboxman5.abnpgame.managers.KeyHandler;
import me.jamboxman5.abnpgame.net.packets.Packet02Move;

public class OnlinePlayer extends Player {

	public InetAddress ipAddress;
	public int port;
	private boolean invert;
	private double lastRotation;
	
	public OnlinePlayer(GamePanel gamePanel, KeyHandler keyHandler, String name, InetAddress ipAddress, int port) {
		super(gamePanel, keyHandler, name);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public OnlinePlayer(GamePanel gamePanel, String name, int x, int y, InetAddress ipAddress, int port) {
		super(gamePanel, null, name);
		this.ipAddress = ipAddress;
		this.port = port;
		setWorldX(x);
		setWorldY(y);
	}
	
	@Override
	public void update() {
		if (gp.getPlayer() == this) {
			super.update();
			if (isMoving || (rotation != lastRotation)) {
				boolean sendInv = (int)gp.getMousePointer().getX() <= getAdjustedScreenX();
				Packet02Move packet = new Packet02Move(getUsername(), worldX/gp.getZoom(), worldY/gp.getZoom(), rotation, sendInv);
				packet.writeData(gp.getClient());
			}
			lastRotation = rotation;
		}
	}
	
	@Override
	public void draw(Graphics2D g2) {
		if (gp.getPlayer() == this) {
			super.draw(g2);
			return;
		} 
		
		if (gp.getGameStage() != GameStage.InGameSinglePlayer &&
			gp.getGameStage() != GameStage.InGameMultiplayer) return;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
		
		//DRAW PLAYER
		
		int x = (int) (worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getAdjustedScreenX());
        int y = (int) (worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getAdjustedScreenY());
        
        AffineTransform tx = new AffineTransform();
	    AffineTransform oldTrans = g2.getTransform();
		
	    tx.setToTranslation(x, y);
        
	    if (invert) {
			   tx.rotate(rotation - Math.toRadians(180));
		} else {
			tx.rotate(rotation);
		}        
	    g2.transform(tx);

	    g2.drawImage(getSprite(), (int)(-getSprite().getWidth()+(60*gp.getZoom())), (int)(-getSprite().getHeight()+(20*gp.getZoom())), null);
	    g2.setTransform(oldTrans);
	}
	public void setInvert(boolean invert) {
		this.invert = invert;
	}

}
