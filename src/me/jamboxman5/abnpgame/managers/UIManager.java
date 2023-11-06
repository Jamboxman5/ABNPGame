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
	boolean debugMode = false;
	
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
	            gp.playSoundEffect("sfx/Menu_Select");
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
		            gp.playSoundEffect("sfx/Menu_Select");
					break;
				case 1:
					gp.moveToMultiplayerMenu();
		            gp.playSoundEffect("sfx/Menu_Select");
					break;
				case 2:
		            gp.playSoundEffect("sfx/Menu_Select");
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
					gp.moveToMapSelect(GameStage.InGameMultiplayer);
		            gp.playSoundEffect("sfx/Menu_Select");
					break;
				case 1:
					gp.moveToMultiplayerGame(false);
		            gp.playSoundEffect("sfx/Menu_Select");
					break;
				case 2:
					gp.backToMainMenu();
		            gp.playSoundEffect("sfx/Menu_Select");
					break;
				}
				menuIndex = 0;
			}
			
		} else if (gp.getGameStage() == GameStage.MapSelector) {
			if (keyH.isEnterPressed()) {
				keyH.setEnterPressed(false);
				gp.playSoundEffect("sfx/Menu_Select");
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
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		
		setupDefaultGraphics(g2);
		
		String title = "ABNP GAME";
        g2.setFont(new Font("Courier New", Font.BOLD, 180));

        Utilities.drawStringShadow(g2, title, 60, 180);

        g2.setColor(Color.white);
        g2.drawString(title, 60, 180);
       
        
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 60));
        
        int x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40,"Singleplayer", g2);
        int y = gp.getScreenCenterY() + 180;
        int spacer = 70;
        
        Utilities.drawStringShadow(g2, "Singleplayer", x, y);

        g2.setColor(Color.white);
        g2.drawString("Singleplayer", x, y);
        
        if (menuIndex == 0) {
            
    		Utilities.drawStringShadow(g2, ">", x-80, y);

            g2.setColor(Color.white);
            g2.drawString(">", x-80, y);        
        }
        y+=spacer;
        
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40, "Multiplayer", g2);

        Utilities.drawStringShadow(g2, "Multiplayer", x, y);

        g2.setColor(Color.white);
        g2.drawString("Multiplayer", x, y);        
        if (menuIndex == 1) {
    		Utilities.drawStringShadow(g2, ">", x-80, y);

            g2.setColor(Color.white);
            g2.drawString(">", x-80, y);         }
        
        y+= spacer;
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40, "Quit Game", g2);
        Utilities.drawStringShadow(g2, "Quit Game", x, y);

        g2.setColor(Color.white);
        g2.drawString("Quit Game", x, y);     
        if (menuIndex == 2) {
    		Utilities.drawStringShadow(g2, ">", x-80, y);

            g2.setColor(Color.white);
            g2.drawString(">", x-80, y);         }
    
	}

	private void drawMultiplayerMenu(Graphics2D g2) {
		g2.setColor(new Color(50,0,0));
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		
		setupDefaultGraphics(g2);
		
		String title = "Multiplayer";
        g2.setFont(new Font("Courier New", Font.BOLD, 160));

        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f);
        Composite old = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g2.setComposite(comp);
        
		g2.setColor(Color.black);
        g2.drawString(title, 60+Utilities.getTextHeight(title, g2)/12, 180+Utilities.getTextHeight(title, g2)/12);
		
		g2.setComposite(old);

        g2.setColor(Color.white);
        g2.drawString(title, 60, 180);
       
        
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 60));
        
        int x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40,"Host Game", g2);
        int y = gp.getScreenCenterY() + 180;
        int spacer = 70;
        g2.setComposite(comp);
        
		g2.setColor(Color.black);
        g2.drawString("Host Game", x+3, y+3);
		
		g2.setComposite(old);

        g2.setColor(Color.white);
        g2.drawString("Host Game", x, y);
        
        if (menuIndex == 0) {
        	g2.setComposite(comp);
            
    		g2.setColor(Color.black);
            g2.drawString(">", x-80+3, y+3);
    		
    		g2.setComposite(old);

            g2.setColor(Color.white);
            g2.drawString(">", x-80, y);        
        }
        y+=spacer;
        
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40, "Join Game", g2);
        g2.setComposite(comp);
        
		g2.setColor(Color.black);
        g2.drawString("Join Game", x+3, y+3);
		
		g2.setComposite(old);

        g2.setColor(Color.white);
        g2.drawString("Join Game", x, y);        
        if (menuIndex == 1) {
        	g2.setComposite(comp);
            
    		g2.setColor(Color.black);
            g2.drawString(">", x-80+3, y+3);
    		
    		g2.setComposite(old);

            g2.setColor(Color.white);
            g2.drawString(">", x-80, y);         }
        
        y+= spacer;
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40, "Main Menu", g2);
        g2.setComposite(comp);
        
		g2.setColor(Color.black);
        g2.drawString("Main Menu", x+3, y+3);
		
		g2.setComposite(old);

        g2.setColor(Color.white);
        g2.drawString("Main Menu", x, y);     
        if (menuIndex == 2) {
        	g2.setComposite(comp);
            
    		g2.setColor(Color.black);
            g2.drawString(">", x-80+3, y+3);
    		
    		g2.setComposite(old);

            g2.setColor(Color.white);
            g2.drawString(">", x-80, y);         }
    
	}
	
	private void drawMapSelector(Graphics2D g2) {
		g2.setColor(new Color(50,0,0));
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		
		setupDefaultGraphics(g2);
		
		String title = "Select Map:";
        g2.setFont(new Font("Courier New", Font.BOLD, 160));

        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f);
        Composite old = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g2.setComposite(comp);
        
		g2.setColor(Color.black);
        g2.drawString(title, 60+Utilities.getTextHeight(title, g2)/12, 180+Utilities.getTextHeight(title, g2)/12);
		
		g2.setComposite(old);

        g2.setColor(Color.white);
        g2.drawString(title, 60, 180);
       
        
        
        g2.setFont(new Font("Courier New", Font.PLAIN, 60));
        
        int x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40,"Airbase", g2);
        int y = gp.getScreenHeight();
        int spacer = 70;
        y -= spacer;
        g2.setComposite(comp);
        
		g2.setColor(Color.black);
        g2.drawString("Airbase", x+3, y+3);
		
		g2.setComposite(old);

        g2.setColor(Color.white);
        g2.drawString("Airbase", x, y);
        
        if (menuIndex == 4) {
        	g2.setComposite(comp);
            
    		g2.setColor(Color.black);
            g2.drawString(">", x-80+3, y+3);
    		
    		g2.setComposite(old);

            g2.setColor(Color.white);
            g2.drawString(">", x-80, y);        
        }
        y-=spacer;
        
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40, "Karnivale", g2);
        g2.setComposite(comp);
        
		g2.setColor(Color.black);
        g2.drawString("Karnivale", x+3, y+3);
		
		g2.setComposite(old);

        g2.setColor(Color.white);
        g2.drawString("Karnivale", x, y);        
        if (menuIndex == 3) {
        	g2.setComposite(comp);
            
    		g2.setColor(Color.black);
            g2.drawString(">", x-80+3, y+3);
    		
    		g2.setComposite(old);

            g2.setColor(Color.white);
            g2.drawString(">", x-80, y);         
            }
        
        y -= spacer;
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40, "Farmhouse", g2);
        g2.setComposite(comp);
        
		g2.setColor(Color.black);
        g2.drawString("Farmhouse", x+3, y+3);
		
		g2.setComposite(old);

        g2.setColor(Color.white);
        g2.drawString("Farmhouse", x, y);     
        if (menuIndex == 2) {
        	g2.setComposite(comp);
            
    		g2.setColor(Color.black);
            g2.drawString(">", x-80+3, y+3);
    		
    		g2.setComposite(old);

            g2.setColor(Color.white);
            g2.drawString(">", x-80, y);         
        }
        
        y -= spacer;
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40, "Black Isle", g2);
        g2.setComposite(comp);
        
		g2.setColor(Color.black);
        g2.drawString("Black Isle", x+3, y+3);
		
		g2.setComposite(old);

        g2.setColor(Color.white);
        g2.drawString("Black Isle", x, y);     
        if (menuIndex == 1) {
        	g2.setComposite(comp);
            
    		g2.setColor(Color.black);
            g2.drawString(">", x-80+3, y+3);
    		
    		g2.setComposite(old);

            g2.setColor(Color.white);
            g2.drawString(">", x-80, y);         
        }
        
        y -= spacer;
        x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40, "Verdammtenstadt", g2);
        g2.setComposite(comp);
        
		g2.setColor(Color.black);
        g2.drawString("Verdammtenstadt", x+3, y+3);
		
		g2.setComposite(old);

        g2.setColor(Color.white);
        g2.drawString("Verdammtenstadt", x, y);     
        if (menuIndex == 0) {
        	g2.setComposite(comp);
            
    		g2.setColor(Color.black);
            g2.drawString(">", x-80+3, y+3);
    		
    		g2.setComposite(old);

            g2.setColor(Color.white);
            g2.drawString(">", x-80, y);         
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
