package me.jamboxman5.abnpgame.managers;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.main.GameStage;
import me.jamboxman5.abnpgame.util.Utilities;

public class MouseMotionHandler implements MouseMotionListener {

	private final GamePanel gp;
	private BufferedImage cursor;
	private BufferedImage cursorShadow;
	
	public MouseMotionHandler(GamePanel gamePanel) {
		gp = gamePanel;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		gp.setMousePointer(e.getPoint());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		gp.setMousePointer(e.getPoint());
	}	
	
	public void setupCursor(String cursorName) {
		
		try {
			cursor = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/me/jamboxman5/abnpgame/resources/cursors/" + cursorName + ".png")));
			cursorShadow = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/me/jamboxman5/abnpgame/resources/cursors/" + cursorName + "_Shadow.png")));
		} catch (IOException | NullPointerException e) {
			cursorShadow = null;
		}
		
		
		 gp.setCursor(gp.getToolkit().createCustomCursor(
                 new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ),
                 new Point(),
                 null ) );	}
	
	public void draw(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
		
		try {
			if (gp.getGameStage() == GameStage.InGameMultiplayer ||
				gp.getGameStage() == GameStage.InGameSinglePlayer) {
				g2.drawImage(Utilities.scaleImage(cursor, 80, 80), (int) gp.getMousePointer().getX()-40, (int) gp.getMousePosition().getY()-40, null);	
			} else if (gp.getGameStage() == GameStage.MainMenu) {
				if (cursorShadow != null) {
					Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f);
					Composite old = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
					g2.setComposite(comp);
					g2.drawImage(Utilities.scaleImage(cursorShadow, 60, 60), (int) gp.getMousePointer().getX()+5, (int) gp.getMousePosition().getY()+5, null);	
					g2.setComposite(old);
				}
				g2.drawImage(Utilities.scaleImage(cursor, 60, 60), (int) gp.getMousePointer().getX(), (int) gp.getMousePosition().getY(), null);	
				
			}
		} catch (NullPointerException e) {
			
		}
	
	}

}
