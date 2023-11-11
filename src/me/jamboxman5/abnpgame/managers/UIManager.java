package me.jamboxman5.abnpgame.managers;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import me.jamboxman5.abnpgame.assets.entity.Entity;
import me.jamboxman5.abnpgame.assets.entity.player.OnlinePlayer;
import me.jamboxman5.abnpgame.assets.maps.Map;
import me.jamboxman5.abnpgame.assets.weapon.firearms.Firearm;
import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.main.GameStage;
import me.jamboxman5.abnpgame.util.Utilities;

public class UIManager {
	
	private final GamePanel gp;
	private final KeyHandler keyH;
	private int menuIndex = 0;
	boolean debugMode = false;
	Font titleFont;
	Font subTitleFont;
	Font selectionFont;
	
	BufferedImage menuBKG;
	
	public UIManager(GamePanel gamePanel, KeyHandler keyHandler) {
		gp = gamePanel;
		keyH = keyHandler;
		
		try {
			menuBKG = ImageIO.read(getClass().getResourceAsStream("/me/jamboxman5/abnpgame/resources/menu/Menu_Background.png"));
			InputStream is = getClass().getResourceAsStream("/me/jamboxman5/abnpgame/resources/fonts/SASFONT.ttf");
			titleFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD, 260);
			subTitleFont = titleFont.deriveFont(Font.PLAIN, 100);
			selectionFont = titleFont.deriveFont(Font.PLAIN, 60);
		} catch (IOException | FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		
		setupDefaultGraphics(g2);
		
		if (gp.getGameStage() == GameStage.InGameSinglePlayer ||
			gp.getGameStage() == GameStage.InGameMultiplayer) {
			drawGameUI(g2);
		} else if (gp.getGameStage() == GameStage.MainMenu) {
			drawMainMenu(g2);
	    } else if (gp.getGameStage() == GameStage.MultiplayerMenu) {
	    	drawMultiplayerMenu(g2);
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
			
			if (keyH.isEnterPressed()) {
				keyH.setEnterPressed(false);
				switch(menuIndex) {
				case 0:
					gp.moveToMapSelect(GameStage.InGameSinglePlayer);
		            gp.playSoundEffect("sfx/menu/Menu_Select");
					break;
				case 1:
					gp.moveToMultiplayerMenu();
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
		else if (gp.getGameStage() == GameStage.MultiplayerMenu) {
			
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
		
		int width = 300;
		int height = 120;
		int x = gp.getWidth() - 20 - width;
		int y = 20;
		
		Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f);
        Composite old = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        g2.setColor(new Color(100,0,0));
        g2.setStroke(new BasicStroke(4));
        g2.setComposite(comp);
        g2.fillRoundRect(x, y, width, height,8,8);
        g2.setComposite(old);
        g2.setColor(Color.white);
        g2.drawRoundRect(x, y, width, height, 8,8);
        
        BufferedImage weaponIMG = gp.getPlayer().getWeaponLoadout().getActiveWeapon().getHudSprite();
        x += (width/2)-(weaponIMG.getWidth()/2);
        y += (height/2) - (weaponIMG.getHeight()/2) - 10;
        
        g2.drawImage(weaponIMG, x, y, null);
        
        Firearm gun = gp.getPlayer().getWeaponLoadout().getActiveFirearm();
        if (gun == null) return;
        
        String ammo = gun.getLoadedAmmo() + " / " + gun.getAmmoCount();
        x = Utilities.getXForRightAlignedText(gp.getWidth() - 30, ammo, g2);
        y = height + 5;
        g2.drawString(ammo, x, y);
        x = gp.getWidth() - 10 - width;
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
		g2.setColor(new Color(50,0,0));
		g2.drawImage(menuBKG, 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);
		
		
		setupDefaultGraphics(g2);
		
		String title = "ABNP:";
		String subTitle = "Zombie Assault";
        g2.setFont(titleFont);

        int x = 60;
        int y = 220;
        
        Utilities.drawStringShadow(g2, title, x, y);

        g2.setColor(Color.white);
        g2.drawString(title, x, y);
       
        y += 90;
        g2.setFont(subTitleFont);
        
        
        Utilities.drawStringShadow(g2, subTitle, x, y);

        g2.setColor(Color.white);
        g2.drawString(subTitle, x, y);
        
        g2.setFont(selectionFont);
        
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40,"Singleplayer", g2);
        y = gp.getScreenCenterY() + 180;
        int spacer = 70;
        
        Utilities.drawStringShadow(g2, "Singleplayer", x, y);

        g2.setColor(Color.white);
        g2.drawString("Singleplayer", x, y);
        
        if (menuIndex == 0) {
            
    		Utilities.drawStringShadow(g2, ">", x-60, y);

            g2.setColor(Color.white);
            g2.drawString(">", x-60, y);        
        }
        y+=spacer;
        
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40, "Multiplayer", g2);

        Utilities.drawStringShadow(g2, "Multiplayer", x, y);

        g2.setColor(Color.white);
        g2.drawString("Multiplayer", x, y);        
        if (menuIndex == 1) {
    		Utilities.drawStringShadow(g2, ">", x-60, y);

            g2.setColor(Color.white);
            g2.drawString(">", x-60, y);         }
        
        y+= spacer;
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40, "Quit Game", g2);
        Utilities.drawStringShadow(g2, "Quit Game", x, y);

        g2.setColor(Color.white);
        g2.drawString("Quit Game", x, y);     
        if (menuIndex == 2) {
    		Utilities.drawStringShadow(g2, ">", x-60, y);

            g2.setColor(Color.white);
            g2.drawString(">", x-60, y);         }
    
	}

	private void drawMultiplayerMenu(Graphics2D g2) {
		g2.setColor(new Color(50,0,0));
		g2.drawImage(menuBKG, 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);
		
		setupDefaultGraphics(g2);
		
		int x = 60;
        int y = 220;
		String title = "MULTIPLAYER";
        g2.setFont(titleFont.deriveFont(Font.PLAIN, 180));

        Utilities.drawStringShadow(g2, title, x, y);

        g2.setColor(Color.white);
        g2.drawString(title, 60, 180);
       
        
        
        g2.setFont(selectionFont);
        
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40,"Host Game", g2);
        y = gp.getScreenCenterY() + 180;
        int spacer = 70;
        
		Utilities.drawStringShadow(g2, "Host Game", x, y);
		

        g2.setColor(Color.white);
        g2.drawString("Host Game", x, y);
        
        if (menuIndex == 0) {
    		Utilities.drawStringShadow(g2, ">", x-60, y);
            g2.setColor(Color.white);
            g2.drawString(">", x-60, y);        
        }
        y+=spacer;
        
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40, "Join Game", g2);
        
		Utilities.drawStringShadow(g2, "Join Game", x, y);
		g2.setColor(Color.white);
        g2.drawString("Join Game", x, y);        
        if (menuIndex == 1) {
        	Utilities.drawStringShadow(g2, ">", x-60, y);
            g2.setColor(Color.white);
            g2.drawString(">", x-60, y);      
        }
        
        y+= spacer;
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40, "Main Menu", g2);
        Utilities.drawStringShadow(g2, "Main Menu", x, y);
		g2.setColor(Color.white);
        g2.drawString("Main Menu", x, y);     
        if (menuIndex == 2) {
        	Utilities.drawStringShadow(g2, ">", x-60, y);
            g2.setColor(Color.white);
            g2.drawString(">", x-60, y);            
        }
    
	}
	
	private void drawMapSelector(Graphics2D g2) {
		g2.setColor(new Color(50,0,0));
		g2.drawImage(menuBKG, 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);
		
		g2.setFont(titleFont.deriveFont(Font.BOLD, 180));
		String title = "Select Map:";

		int x = 60+Utilities.getTextHeight(title, g2)/12;
		int y = 180+Utilities.getTextHeight(title, g2)/12;
		
        Utilities.drawStringShadow(g2, title, x, y);
        g2.setColor(Color.white);
        g2.drawString(title, 60, 180);
       
        
        
        g2.setFont(selectionFont);
        
        
        int spacer = 70;
        y = gp.getScreenHeight();

        
        
        for (int i = gp.getMapManager().getMapList().size()-1; i >= 0; i--) {
        	Map m = gp.getMapManager().getMapList().get(i);
        	x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40,m.getName().replace("_", " "), g2);
            y -= spacer;
        	Utilities.drawStringShadow(g2, m.getName().replace("_", " "), x, y);
            g2.setColor(Color.white);
            g2.drawString(m.getName().replace("_", " "), x, y);
            if (menuIndex == i) {
            	Utilities.drawStringShadow(g2, ">", x - 60, y);
                g2.setColor(Color.white);
                g2.drawString(">", x - 60, y);
            }
        }
    
	}
	
	private void setupDefaultGraphics(Graphics2D graphics2D) {
		graphics2D.setFont(new Font("Courier New", Font.BOLD, 40));
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setColor(Color.WHITE);
    }
	
	public int getCommandNumber() { return menuIndex; }
	public void setCommandNumber(int newIndex) { menuIndex = newIndex; }

}
