package me.jamboxman5.abnpgame.assets.ui.menu;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.jamboxman5.abnpgame.assets.ui.Button;
import me.jamboxman5.abnpgame.assets.ui.Fonts;
import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.util.Utilities;

public class ArcadeMenu {
	
	private static BufferedImage bkg;
//	private static final int alignX = GamePanel.getInstance().getScreenWidth()-40;
//	private static final int spacer = 140;
	
	public static Button[] buttons;
	public static Button playButton;
	public static Button shopButton;
	public static Button equipButton;
	public static Button unlocksButton;
	public static Button backButton1;
	public static Button backButton2;
	public static Button activeButton;
	
	private static int buttonWidth = 575;
	private static int buttonHeight = 180;
	private static int buttonXSpace = 40;
	private static int buttonYSpace = 200;
	
	public static void draw(Graphics2D g2, int menuIndex) {
		GamePanel gp = GamePanel.getInstance();
		if (bkg == null) {
			try {
				bkg = ImageIO.read(ArcadeMenu.class.getResourceAsStream("/me/jamboxman5/abnpgame/resources/menu/Dark_Brown_Background.png"));
			} catch (IOException e) {
				
			}
		}
		if (buttons == null) getButtons(gp, g2);
		
		g2.drawImage(bkg, 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);
		
		drawPlayerDetailBox(g2, gp);
		drawButtons(g2, gp);
        
        

		
	}
	
	public static void updateActiveButton(Point p) {
		if (buttons == null) return;
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
	
	private static void drawPlayerDetailBox(Graphics2D g2, GamePanel gp) {
		g2.setColor(new Color(75,0,0));
		Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f);
        Composite old = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		GradientPaint gradient = new GradientPaint(gp.getWidth(), (int)(gp.getHeight()/4.5), new Color(75f/255f, 0,0,1f), gp.getWidth(), (int)((gp.getHeight()/4.5)+10), new Color(75f/255f, 0,0,0f));
		Paint oldPaint = g2.getPaint();
		g2.setComposite(comp);
        g2.fillRect(0, 0, gp.getWidth(), (int)(gp.getHeight()/4.5));
        g2.setComposite(old);
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(2));
		g2.drawLine(0, (int)(gp.getHeight()/4.5), gp.getWidth(), (int)(gp.getHeight()/4.5));
	    g2.setPaint(gradient);
	    g2.fillRect(0, (int)(gp.getHeight()/4.5), gp.getWidth(), 10);
	    g2.setPaint(oldPaint);
	}

	private static void getButtons(GamePanel gp, Graphics2D g2) {
		buttons = new Button[6];
		playButton = new Button(buttonXSpace, buttonYSpace, buttonWidth, buttonHeight, "Play", Fonts.BUTTONFONT);
		shopButton = new Button(buttonXSpace, buttonYSpace + buttonHeight + buttonXSpace, buttonWidth, buttonHeight, "Shop", Fonts.BUTTONFONT);
		equipButton = new Button(gp.getWidth() - buttonXSpace - buttonWidth, buttonYSpace, buttonWidth, buttonHeight, "Equip", Fonts.BUTTONFONT);
		unlocksButton = new Button(gp.getWidth() - buttonXSpace - buttonWidth, buttonYSpace + buttonHeight + buttonXSpace, buttonWidth, buttonHeight, "Unlocks", Fonts.BUTTONFONT);
		buttons[0] = playButton;
		buttons[1] = shopButton;
		buttons[2] = equipButton;
		buttons[3] = unlocksButton;
		
		int y = gp.getScreenHeight() - 80;
        int x = 80;
        Utilities.drawStringWithShadow(g2, "Main Menu", Color.white, x, y);

        Utilities.drawStringWithShadow(g2, ">", Color.RED, x - 40, y);
        
        backButton1 = new Button(x, y, "Main Menu", Fonts.BUTTONFONT, g2);
        backButton2 = new Button(x-40, y, ">", Fonts.BUTTONFONT, g2, Color.red);
        buttons[4] = backButton1;
        buttons[5] = backButton2;
	}
	
	
	
}
