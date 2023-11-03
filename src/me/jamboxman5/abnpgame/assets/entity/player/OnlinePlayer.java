package me.jamboxman5.abnpgame.assets.entity.player;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.net.InetAddress;

import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.main.GameStage;
import me.jamboxman5.abnpgame.managers.KeyHandler;

public class OnlinePlayer extends Player {

	public InetAddress ipAddress;
	public int port;
	
	public OnlinePlayer(GamePanel gamePanel, KeyHandler keyHandler, String name, InetAddress ipAddress, int port) {
		super(gamePanel, keyHandler, name);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public OnlinePlayer(GamePanel gamePanel, String name, InetAddress ipAddress, int port) {
		super(gamePanel, null, name);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	@Override
	public void update() {
		if (keyH != null) {
			super.update();
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
			
			double x = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getAdjustedScreenX();
	        double y = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getAdjustedScreenY();
						
			g2.setColor(Color.red);
			AffineTransform tx = new AffineTransform();
		    AffineTransform old = g2.getTransform();
		    tx.setToTranslation(x, y);
		    tx.rotate(rotation);
		    g2.transform(tx);
		    g2.fillRect(-20, -20, 40, 40);
		    g2.setStroke(new BasicStroke(4));
			g2.setColor(Color.white);
			g2.drawRect(-20, -20, 40, 40);
		    
			g2.setStroke(new BasicStroke());
		    g2.setTransform(old);
	}

}
