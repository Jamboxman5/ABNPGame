package me.jamboxman5.abnpgame.managers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.main.GameStage;
import me.jamboxman5.abnpgame.util.Utilities;

public class UIManager {
	
	private final GamePanel gp;
	private final KeyHandler keyH;
	private int menuIndex = 0;
	
	public UIManager(GamePanel gamePanel, KeyHandler keyHandler) {
		gp = gamePanel;
		keyH = keyHandler;
	}
	
	public void draw(Graphics2D g2) {
		
		setupDefaultGraphics(g2);
		
		if (gp.getGameStage() == GameStage.InGameSinglePlayer ||
			gp.getGameStage() == GameStage.InGameMultiplayer) {
			drawGameUI(g2);
		} else if (gp.getGameStage() == GameStage.MainMenu) {
			drawMainMenu(g2);
	    }
		
	}
	
	public void update() {
		if (gp.getGameStage() == GameStage.MainMenu) {
			if (keyH.isForwardPressed()) {
				menuIndex--;
				if (menuIndex < 0) menuIndex = 2;
				keyH.setForwardPressed(false);
			}
			if (keyH.isBackPressed()) {
				menuIndex++;
				if (menuIndex > 2) menuIndex = 0;
				keyH.setBackPressed(false);
			}
			if (keyH.isEnterPressed()) {
				switch(menuIndex) {
				case 0:
					gp.moveToSingleplayerMenu();
					break;
				case 1:
					gp.moveToMultiplayerMenu();
					break;
				case 2:
					gp.quitGame();
					break;
				}
				keyH.setEnterPressed(false);
				menuIndex = 0;
			}
			
		}
	}
	
	private void drawGameUI(Graphics2D g2) {
		g2.setColor(Color.white);
        g2.setFont(new Font("Courier New", Font.BOLD, 20));
        String name = gp.getPlayer().getName();
        int length = (int) g2.getFontMetrics().getStringBounds(name, g2).getWidth();
        g2.drawString(name, gp.getScreenCenterX() - (length/2), gp.getScreenCenterY() - 40);
	}
	
	private void drawMainMenu(Graphics2D g2) {
		g2.setColor(new Color(50,0,0));
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		
		setupDefaultGraphics(g2);
		
		g2.setColor(Color.white);
		String title = "ABNP GAME";
        int length = (int) g2.getFontMetrics().getStringBounds(title, g2).getWidth();
        g2.setFont(new Font("Courier New", Font.BOLD, 160));
        g2.drawString(title, Utilities.getXForCenterOfText(title, gp, g2), gp.getScreenCenterY() - 50);
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 60));
        
        int x = Utilities.getXForCenterOfText("Singleplayer", gp, g2);
        int y = gp.getScreenCenterY() + 120;
        int spacer = 70;
        
        g2.drawString("Singleplayer", x, y);
        if (menuIndex == 0) {
        	g2.drawString(">", x - 80, y);
        }
        y+=spacer;
        
        x = Utilities.getXForCenterOfText("Multiplayer", gp, g2);
        g2.drawString("Multiplayer", x, y);
        if (menuIndex == 1) {
        	g2.drawString(">", x - 80, y);
        }
        
        y+= spacer;
        x = Utilities.getXForCenterOfText("Quit Game", gp, g2);
        g2.drawString("Quit Game", x, y);
        if (menuIndex == 2) {
        	g2.drawString(">", x - 80, y);
        }
    
	}
	
	private void setupDefaultGraphics(Graphics2D graphics2D) {
		graphics2D.setFont(new Font("Courier New", Font.BOLD, 40));
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setColor(Color.WHITE);
    }

}
