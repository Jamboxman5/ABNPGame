package managers;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import main.GamePanel;
import util.Utilities;

public class MouseMotionHandler implements MouseMotionListener {

	private final GamePanel gp;
	private BufferedImage cursor;
	
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
			cursor = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/cursors/" + cursorName + ".png")));
		} catch (IOException e) {
			e.printStackTrace();
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
			g2.drawImage(Utilities.scaleImage(cursor, 80, 80), (int) gp.getMousePointer().getX()-40, (int) gp.getMousePosition().getY()-40, null);	
		} catch (NullPointerException e) {
			
		}
	
	}

}
