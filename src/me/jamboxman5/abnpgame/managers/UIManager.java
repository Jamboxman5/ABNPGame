package me.jamboxman5.abnpgame.managers;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import me.jamboxman5.abnpgame.assets.entity.Entity;
import me.jamboxman5.abnpgame.assets.entity.player.OnlinePlayer;
import me.jamboxman5.abnpgame.assets.ui.Fonts;
import me.jamboxman5.abnpgame.assets.ui.menu.ArcadeMenu;
import me.jamboxman5.abnpgame.assets.ui.menu.MainMenu;
import me.jamboxman5.abnpgame.assets.ui.menu.MapSelectMenu;
import me.jamboxman5.abnpgame.assets.weapon.firearms.Firearm;
import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.main.GameStage;
import me.jamboxman5.abnpgame.util.Utilities;

public class UIManager {
	
	private final GamePanel gp;
	private final KeyHandler keyH;
	private int menuIndex = 0;
	boolean debugMode = false;
	
	BufferedImage menuBKG;
	
	public UIManager(GamePanel gamePanel, KeyHandler keyHandler) {
		gp = gamePanel;
		keyH = keyHandler;
		Fonts.setupFonts();
	}
	
	public void draw(Graphics2D g2) {
				
		if (gp.getGameStage() == GameStage.InGameSinglePlayer ||
			gp.getGameStage() == GameStage.InGameMultiplayer) {
			drawGameUI(g2);
		} else if (gp.getGameStage() == GameStage.MainMenu) {
			drawMainMenu(g2);
	    } else if (gp.getGameStage() == GameStage.ArcadeMenu) {
	    	drawArcadeMenu(g2);
	    } else if (gp.getGameStage() == GameStage.MapSelector) {
	    	drawMapSelector(g2);
	    }
		
	}
	
	public void update() {
		if (gp.getGameStage().toString().contains("InGame")) {
			if (keyH.isEscapePressed()) {
				keyH.setEscapePressed(false);
	            gp.playSoundEffect("sfx/menu/Menu_Select");
	            gp.backToMainMenu();
			}
			if (keyH.isShowDebugText()) {
				keyH.setShowDebugText(false);
				gp.setDebugMode(!gp.isDebugMode());
			}
		}
		else if (gp.getGameStage() == GameStage.MainMenu) {
			if (gp.getMousePointer() != null) {
				MainMenu.updateActiveButton(gp.getMousePointer());
			}
			if (keyH.isEnterPressed()) {
				keyH.setEnterPressed(false);
				switch(menuIndex) {
				case 0:
//					gp.moveToPlayMenu();
					gp.moveToMapSelect(GameStage.InGameSinglePlayer);
		            gp.playSoundEffect("sfx/menu/Menu_Select");
					break;
				case 1:
					gp.moveToArcadeMenu();
		            gp.playSoundEffect("sfx/menu/Menu_Select");
					break;
				case 2:
		            gp.playSoundEffect("sfx/menu/Menu_Select");
		            gp.quitGame();
					break;
				}
				menuIndex = 0;
			}
			
		}
		else if (gp.getGameStage() == GameStage.ArcadeMenu) {
			
			if (gp.getMousePointer() != null) {
				ArcadeMenu.updateActiveButton(gp.getMousePointer());
			}
			
			if (keyH.isEnterPressed()) {
				keyH.setEnterPressed(false);
				switch(menuIndex) {
				case 0:
//					gp.moveToMapSelect(GameStage.InGameMultiplayer);
//		            gp.playSoundEffect("sfx/menu/Menu_Select");
					break;
				case 1:
					gp.moveToMultiplayerGame(false);
		            gp.playSoundEffect("sfx/menu/Menu_Select");
					break;
				case 2:
					gp.backToMainMenu();
		            gp.playSoundEffect("sfx/menu/Menu_Select");
					break;
				}
				menuIndex = 0;
			}
			
		} else if (gp.getGameStage() == GameStage.MapSelector) {
			if (keyH.isEnterPressed()) {
				keyH.setEnterPressed(false);
				gp.playSoundEffect("sfx/menu/Menu_Select");
				if (menuIndex == 5) {
					gp.backToMainMenu();
					return;
				}
				gp.getMapManager().setMap(menuIndex);
				switch(gp.getNextStage()) {
				case InGameMultiplayer:
					gp.moveToMultiplayerGame(true);
					break;
				case InGameSinglePlayer:
					gp.moveToSingleplayerGame();
					break;
				default:
					break;
				}
				menuIndex = 0;
			}
			
		}
		
	}
	
	private void drawGameUI(Graphics2D g2) {
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
		
		drawGamerTags(g2);
		drawWeaponHud(g2);
	}
	
	private void drawWeaponHud(Graphics2D g2) {
		
		int width = 320;
		int height = 140;
		int x = gp.getWidth() - 10 - width;
		int y = 10;
		
		Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f);
        Composite old = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g2.setColor(new Color(140,0,0));
        g2.setStroke(new BasicStroke(3));
        g2.setComposite(comp);
        g2.fillRoundRect(x, y, width, height,16,16);
        g2.setComposite(old);
        g2.setColor(Color.white);
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 8,8);
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        
        BufferedImage weaponIMG = gp.getPlayer().getWeaponLoadout().getActiveWeapon().getHudSprite();
        x += (width/2)-(weaponIMG.getWidth()/2);
        y += (height/2) - (weaponIMG.getHeight()/2) - 10;
        
        g2.drawImage(weaponIMG, x, y, null);
        
        Firearm gun = gp.getPlayer().getWeaponLoadout().getActiveFirearm();
        if (gun == null) return;
        
        String ammo = gun.getLoadedAmmo() + " / " + gun.getAmmoCount();
        x = Utilities.getXForRightAlignedText(gp.getWidth() - 30, ammo, g2);
        y = height - 5;
        g2.drawString(ammo, x, y);
        x = gp.getWidth() - width + 10;
        g2.drawString(gun.getName(), x, y);
	}
	
	private void drawGamerTags(Graphics2D g2) {
		for (Entity e : gp.getMapManager().getEntities()) {
			if (e instanceof OnlinePlayer) {
				g2.setColor(Color.white);
		        g2.setFont(new Font("Courier New", Font.BOLD, 20));
		        int x = (int) (e.getWorldX() - gp.getPlayer().getWorldX() + gp.getPlayer().getAdjustedScreenX());
		        int y = (int) (e.getWorldY() - gp.getPlayer().getWorldY() + gp.getPlayer().getAdjustedScreenY());
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
		MainMenu.draw(g2, menuIndex);    
	}

	private void drawArcadeMenu(Graphics2D g2) {
		ArcadeMenu.draw(g2, menuIndex);
	}
	
	private void drawMapSelector(Graphics2D g2) {
		MapSelectMenu.draw(g2, menuIndex);
	}
	
	public int getCommandNumber() { return menuIndex; }
	public void setCommandNumber(int newIndex) { menuIndex = newIndex; }

}
