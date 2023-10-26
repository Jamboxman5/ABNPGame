package managers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.GamePanel;

public class MouseHandler implements MouseListener {

	private final GamePanel gp;
	
	public MouseHandler(GamePanel gamePanel) {
		gp = gamePanel;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("playerx: " + gp.getPlayer().getAdjustedScreenX());
		System.out.println("playery: " + gp.getPlayer().getAdjustedScreenY());
		System.out.println("mousex: " + gp.getMousePointer().getX());
		System.out.println("mousey: " + gp.getMousePointer().getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
