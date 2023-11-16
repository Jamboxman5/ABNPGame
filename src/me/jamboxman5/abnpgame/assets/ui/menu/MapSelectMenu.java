package me.jamboxman5.abnpgame.assets.ui.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.jamboxman5.abnpgame.assets.maps.Map;
import me.jamboxman5.abnpgame.assets.ui.Fonts;
import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.util.Utilities;

public class MapSelectMenu {
	
	private static BufferedImage bkg;
	
	public static void draw(Graphics2D g2, int menuIndex) {
		GamePanel gp = GamePanel.getInstance();
		if (bkg == null) {
			try {
				bkg = ImageIO.read(MapSelectMenu.class.getResourceAsStream("/me/jamboxman5/abnpgame/resources/menu/Menu_Background_1.png"));
			} catch (IOException e) {
				
			}
		}
		g2.drawImage(bkg, 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);

		g2.setFont(Fonts.TITLEFONT.deriveFont(Font.BOLD, 180));
		String title = "Select Map:";

		int x = 60+Utilities.getTextHeight(title, g2)/12;
		int y = 180+Utilities.getTextHeight(title, g2)/12;
		
        Utilities.drawStringWithShadow(g2, title, Color.white, x, y);
        
        g2.setFont(Fonts.BUTTONFONT);
        
        
        int spacer = 60;
        y = gp.getScreenHeight() - 40;

        
        
        for (int i = gp.getMapManager().getMapList().size()-1; i >= 0; i--) {
        	Map m = gp.getMapManager().getMapList().get(i);
        	x = Utilities.getXForRightAlignedText(gp.getScreenWidth() - 40,m.getName().replace("_", " "), g2);
        	Utilities.drawStringShadow(g2, m.getName().replace("_", " "), x, y);
            g2.setColor(Color.white);
            g2.drawString(m.getName().replace("_", " "), x, y);
            if (menuIndex == i) {
            	Utilities.drawStringShadow(g2, ">", x - 60, y);
                g2.setColor(Color.white);
                g2.drawString(">", x - 60, y);
            }
            y -= spacer;

        }
        
        y = gp.getScreenHeight() - 40;
        x = 80;
        Utilities.drawStringShadow(g2, "Main Menu", x, y);
        if (menuIndex == 5) {
        	g2.setColor(Color.LIGHT_GRAY);
        } else {
        	g2.setColor(Color.white);
        }
        g2.drawString("Main Menu", x, y);
        Utilities.drawStringShadow(g2, ">", x - 40, y);
        g2.setColor(Color.RED);
        g2.drawString(">", x - 40, y);
		
	}
	
}
