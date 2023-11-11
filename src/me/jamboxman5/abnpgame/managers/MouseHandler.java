package me.jamboxman5.abnpgame.managers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.main.GameStage;

public class MouseHandler implements MouseListener {

	private final GamePanel gp;
	
	boolean leftMouseHeld = false;
	
	public MouseHandler(GamePanel gamePanel) {
		gp = gamePanel;
	}
	
	public void update() {
		if (gp.getGameStage().toString().contains("InGame")) {
			handleInGameClick();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	private void handleInGameClick() {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			leftMouseHeld = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 1) {
			leftMouseHeld = false;
		}		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean clicking() { return leftMouseHeld; }
	
	
}
