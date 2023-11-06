package me.jamboxman5.abnpgame.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import me.jamboxman5.abnpgame.main.GamePanel;

public class Utilities {
	
	public static BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = scaledImage.createGraphics();
        
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
        
        graphics2D.drawImage(original, 0, 0, width, height, null);
        graphics2D.dispose();

        return scaledImage;
    }
	public static void drawStringShadow(Graphics2D g2, String string, int x, int y) {
		Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f);
        Composite old = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g2.setComposite(comp);
        
		Color oldColor = g2.getColor();
		g2.setColor(Color.black);
		int offset = Utilities.getTextHeight(string, g2)/15;
		if (offset < 2) offset = 2;
        g2.drawString(string, x+offset, y+offset);
		
		g2.setComposite(old);
		g2.setColor(oldColor);
	}
    public static int getXForScreenCenteredText(String text, GamePanel gamePanel, Graphics2D graphics2D) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return gamePanel.getScreenWidth() / 2 - length / 2;
    }
    public static int getXForCenteredText(int centeredX, String text, Graphics2D graphics2D) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return centeredX - length / 2;
    }
    public static int getXForRightAlignedText(int rightX, String text, Graphics2D graphics2D) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return rightX - length;
    }
    public static int getTextHeight(String text, Graphics2D graphics2D) {
        int height = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getHeight();
        return height;
    }
//
//    public static int getXForAlightToRightOfText(String text, int tailX, GamePanel gamePanel, Graphics2D graphics2D) {
//        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
//        return tailX - length;
//    }

}
