package me.jamboxman5.abnpgame.managers;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import me.jamboxman5.abnpgame.assets.entity.Entity;
import me.jamboxman5.abnpgame.assets.entity.player.OnlinePlayer;
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
		for (Entity e : gp.getMapManager().getEntities()) {
			if (e instanceof OnlinePlayer) {
				g2.setColor(Color.white);
		        g2.setFont(new Font("Courier New", Font.BOLD, 20));
		        int x = (int) (e.getWorldX() - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX());
		        int y = (int) (e.getWorldY() - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY());
		        String name = e.getName(); 
		        int length = (int) g2.getFontMetrics().getStringBounds(name, g2).getWidth();
		        g2.drawString(name, x - (length/2), y - 40);
			}
		}
		g2.setColor(Color.white);
        g2.setFont(new Font("Courier New", Font.BOLD, 20));
        int x = gp.getPlayer().getAdjustedScreenX();
        int y = gp.getPlayer().getAdjustedScreenY();
        String name = gp.getPlayer().getName(); 
        int length = (int) g2.getFontMetrics().getStringBounds(name, g2).getWidth();
        g2.drawString(name, x - (length/2), y - 40);
		
	}
	
	private void drawMainMenu(Graphics2D g2) {
		g2.setColor(new Color(50,0,0));
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		
		setupDefaultGraphics(g2);
		
		String title = "ABNP GAME";
        g2.setFont(new Font("Courier New", Font.BOLD, 180));

        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f);
        Composite old = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g2.setComposite(comp);
        
		g2.setColor(Color.black);
        g2.drawString(title, 60+Utilities.getTextHeight(title, g2)/12, 180+Utilities.getTextHeight(title, g2)/12);
		
		g2.setComposite(old);

        g2.setColor(Color.white);
        g2.drawString(title, 60, 180);
       
        
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 60));
        
        int x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40,"Singleplayer", g2);
        int y = gp.getScreenCenterY() + 180;
        int spacer = 70;
        
        g2.setComposite(comp);
        
		g2.setColor(Color.black);
        g2.drawString("Singleplayer", x+3, y+3);
		
		g2.setComposite(old);

        g2.setColor(Color.white);
        g2.drawString("Singleplayer", x, y);
        
        if (menuIndex == 0) {
        	g2.setComposite(comp);
            
    		g2.setColor(Color.black);
            g2.drawString(">", x-80+3, y+3);
    		
    		g2.setComposite(old);

            g2.setColor(Color.white);
            g2.drawString(">", x-80, y);        
        }
        y+=spacer;
        
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40, "Multiplayer", g2);
        g2.setComposite(comp);
        
		g2.setColor(Color.black);
        g2.drawString("Multiplayer", x+3, y+3);
		
		g2.setComposite(old);

        g2.setColor(Color.white);
        g2.drawString("Multiplayer", x, y);        
        if (menuIndex == 1) {
        	g2.setComposite(comp);
            
    		g2.setColor(Color.black);
            g2.drawString(">", x-80+3, y+3);
    		
    		g2.setComposite(old);

            g2.setColor(Color.white);
            g2.drawString(">", x-80, y);         }
        
        y+= spacer;
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40, "Quit Game", g2);
        g2.setComposite(comp);
        
		g2.setColor(Color.black);
        g2.drawString("Quit Game", x+3, y+3);
		
		g2.setComposite(old);

        g2.setColor(Color.white);
        g2.drawString("Quit Game", x, y);     
        if (menuIndex == 2) {
        	g2.setComposite(comp);
            
    		g2.setColor(Color.black);
            g2.drawString(">", x-80+3, y+3);
    		
    		g2.setComposite(old);

            g2.setColor(Color.white);
            g2.drawString(">", x-80, y);         }
    
	}
	
	private void setupDefaultGraphics(Graphics2D graphics2D) {
		graphics2D.setFont(new Font("Courier New", Font.BOLD, 40));
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setColor(Color.WHITE);
    }

}
