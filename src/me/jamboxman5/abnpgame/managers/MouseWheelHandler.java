package me.jamboxman5.abnpgame.managers;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import me.jamboxman5.abnpgame.main.GamePanel;

public class MouseWheelHandler implements MouseWheelListener {

	private GamePanel gp;
	
	public MouseWheelHandler(GamePanel gamePanel) {
		gp = gamePanel;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (gp.getGameStage().toString().contains("InGame")) handleInGameScroll(e);
		
	}

	private void handleInGameScroll(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0) {
			//SCROLL UP
			gp.getPlayer().getWeaponLoadout().nextWeapon();
			gp.playSoundEffect("sfx/menu/Menu_Scroll");
		}
		if (e.getWheelRotation() > 0) {
			//SCROLL DOWN
			gp.getPlayer().getWeaponLoadout().previousWeapon();
			gp.playSoundEffect("sfx/menu/Menu_Scroll");
		}
	}

}
