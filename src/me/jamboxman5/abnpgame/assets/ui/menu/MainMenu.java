package me.jamboxman5.abnpgame.assets.ui.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.jamboxman5.abnpgame.assets.ui.Button;
import me.jamboxman5.abnpgame.assets.ui.Fonts;
import me.jamboxman5.abnpgame.assets.ui.Button.TextAlign;
import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.util.Utilities;

public class MainMenu {
	
	private static BufferedImage bkg;
	private static final int alignX = GamePanel.getInstance().getScreenWidth() - 40;
	private static final int spacer = 70;
	
	private static String title = "ABNP:";
	private static String subTitle = "Zombie Assault";
	
	public static Button[] buttons;
	public static Button campaignButton;
	public static Button arcadeButton;
	public static Button quitButton;
	public static Button activeButton;
	
	public static void draw(Graphics2D g2, int menuIndex) {
		GamePanel gp = GamePanel.getInstance();
		if (bkg == null) {
			try {
				bkg = ImageIO.read(MainMenu.class.getResourceAsStream("/me/jamboxman5/abnpgame/resources/menu/Menu_Background_0.png"));
			} catch (IOException e) {
				
			}
		}
		if (buttons == null) getButtons(gp, g2);
		g2.drawImage(bkg, 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);

		
        drawTitle(g2);
        drawButtons(g2, gp);
        
		
	}
	
	private static void drawTitle(Graphics2D g2) {

        g2.setFont(Fonts.TITLEFONT);
        

        int x = 60;
        int y = 220;
        
        Utilities.drawStringWithShadow(g2, title, Color.white, x, y);
       
        y += 90;
        g2.setFont(Fonts.SUBTITLEFONT);
        
        Utilities.drawStringWithShadow(g2, subTitle, Color.white, x, y);
	}
	
	public static void updateActiveButton(Point p) {
		for (Button b : buttons) {
			if (b.contains(p))  {
				activeButton = b;
				return;
			}
		}
		activeButton = null;
	}
	
	private static void drawButtons(Graphics2D g2, GamePanel gp) {
		for (Button b : buttons) {
			b.draw(g2, (activeButton == b));
		}
	}

	private static void getButtons(GamePanel gp, Graphics2D g2) {
		buttons = new Button[3];
		g2.setFont(Fonts.BUTTONFONT);
		int x = alignX - Utilities.getTextWidth("Campaign", g2);
		int y = gp.getHeight() - spacer*(buttons.length);
		campaignButton = new Button(x, y, "Campaign", Fonts.BUTTONFONT, g2, TextAlign.RIGHT);
		x = alignX - Utilities.getTextWidth("Arcade", g2);
		y += spacer;
		arcadeButton = new Button(x, y, "Arcade", Fonts.BUTTONFONT, g2, TextAlign.RIGHT);
		x = alignX - Utilities.getTextWidth("Quit", g2);
		y += spacer;
		quitButton = new Button(x, y, "Quit", Fonts.BUTTONFONT, g2, TextAlign.RIGHT);
		
		buttons[0] = campaignButton;
		buttons[1] = arcadeButton;
		buttons[2] = quitButton;
	
	}
	
}
