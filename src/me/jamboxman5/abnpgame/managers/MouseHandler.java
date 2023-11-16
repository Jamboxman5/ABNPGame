package me.jamboxman5.abnpgame.managers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import me.jamboxman5.abnpgame.assets.ui.menu.ArcadeMenu;
import me.jamboxman5.abnpgame.assets.ui.menu.MainMenu;
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
		switch(gp.getGameStage()) {
		case ArcadeMenu:
			handleArcadeClick();
			break;
		case MainMenu:
			handleMainMenuClick();
			break;
		default:
			break;
		}
	}
	
	private void handleMainMenuClick() {
		if (MainMenu.activeButton == null) return;
		if (MainMenu.activeButton.equals(MainMenu.campaignButton)) {
			gp.moveToMapSelect(GameStage.InGameSinglePlayer);
            gp.playSoundEffect("sfx/menu/Menu_Select");
		} else if (MainMenu.activeButton.equals(MainMenu.arcadeButton)) {
			gp.moveToArcadeMenu();
            gp.playSoundEffect("sfx/menu/Menu_Select");
		} else if (MainMenu.activeButton.equals(MainMenu.quitButton)) {
			gp.playSoundEffect("sfx/menu/Menu_Select");
            gp.quitGame();
        } 
	}

	private void handleArcadeClick() {
		if (ArcadeMenu.activeButton == null) return;
		if (ArcadeMenu.activeButton.equals(ArcadeMenu.playButton)) {
			System.out.println("PLAY");
		} else if (ArcadeMenu.activeButton.equals(ArcadeMenu.shopButton)) {
			System.out.println("SHOP");
		} else if (ArcadeMenu.activeButton.equals(ArcadeMenu.equipButton)) {
			System.out.println("EQUIP");
		} else if (ArcadeMenu.activeButton.equals(ArcadeMenu.unlocksButton)) {
			System.out.println("UNLOCKS");
		} else if (ArcadeMenu.activeButton.equals(ArcadeMenu.backButton1) ||
				   ArcadeMenu.activeButton.equals(ArcadeMenu.backButton2)) {
			gp.backToMainMenu();
		}
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
