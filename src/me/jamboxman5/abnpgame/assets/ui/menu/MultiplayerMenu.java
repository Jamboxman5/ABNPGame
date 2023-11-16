package me.jamboxman5.abnpgame.assets.ui.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.jamboxman5.abnpgame.assets.ui.Fonts;
import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.util.Utilities;

public class MultiplayerMenu {
	
	private static BufferedImage bkg;
	
	public static void draw(Graphics2D g2, int menuIndex) {
		GamePanel gp = GamePanel.getInstance();
		if (bkg == null) {
			try {
				bkg = ImageIO.read(MultiplayerMenu.class.getResourceAsStream("/me/jamboxman5/abnpgame/resources/menu/Menu_Background.png"));
			} catch (IOException e) {
				
			}
		}
		g2.drawImage(bkg, 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);

		int x = 60;
        int y = 220;
		String title = "MULTIPLAYER";
        g2.setFont(Fonts.TITLEFONT.deriveFont(Font.PLAIN, 180));

        Utilities.drawStringShadow(g2, title, x, y);

        g2.setColor(Color.white);
        g2.drawString(title, 60, 180);
       
        
        
        g2.setFont(Fonts.BUTTONFONT);
        
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
	
}
