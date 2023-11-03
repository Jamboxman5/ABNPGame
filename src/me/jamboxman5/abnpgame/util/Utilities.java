package me.jamboxman5.abnpgame.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import me.jamboxman5.abnpgame.main.GamePanel;

public class Utilities {
	
	public static BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
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
