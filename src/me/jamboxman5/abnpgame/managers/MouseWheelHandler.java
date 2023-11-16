package me.jamboxman5.abnpgame.managers;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.main.GameStage;
import me.jamboxman5.abnpgame.net.packets.Packet04Weapon;

public class MouseWheelHandler implements MouseWheelListener {

	private GamePanel gp;
	
	public MouseWheelHandler(GamePanel gamePanel) {
		gp = gamePanel;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (gp.getGameStage().toString().contains("InGame")) handleInGameScroll(e.getWheelRotation());
		
	}

	private void handleInGameScroll(int rotation) {
		if (rotation == 0) return;
		if (rotation < 0) {
			//SCROLL UP
			gp.getPlayer().getWeaponLoadout().nextWeapon();
			gp.playSoundEffect("sfx/menu/Menu_Scroll");
		}
		if (rotation > 0) {
			//SCROLL DOWN
			gp.getPlayer().getWeaponLoadout().previousWeapon();
			gp.playSoundEffect("sfx/menu/Menu_Scroll");
		}
		if (gp.getGameStage() == GameStage.InGameMultiplayer) {
			Packet04Weapon packet = new Packet04Weapon(gp.getPlayer().getUsername(), 
					   gp.getPlayer().getWeaponLoadout().getActiveWeapon().getName());
			packet.writeData(gp.getClient());
		}
		
	}

}
