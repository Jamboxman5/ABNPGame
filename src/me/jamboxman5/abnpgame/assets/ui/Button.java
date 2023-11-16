package me.jamboxman5.abnpgame.assets.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import me.jamboxman5.abnpgame.util.Utilities;

public class Button {

	Rectangle bounds;
	String text;
	Font font;
	boolean fill;
	Color color;
	TextAlign align = TextAlign.CENTER;
	
	public Button(int x, int y, int width, int height, String text, Font font) {
		this.text = text;
		this.font = font;
		this.fill = true;
		align = TextAlign.CENTER;
		bounds = new Rectangle(x, y, width, height);
	}
	
	public Button(int x, int y, String text, Font font, Graphics2D g2) {
		this.text = text;
		this.font = font;
		this.fill = false;
		align = TextAlign.CENTER;
		g2.setFont(font);
		bounds = new Rectangle(x, y, Utilities.getTextWidth(text, g2), Utilities.getTextHeight(text, g2));
	}
	
	public Button(int x, int y, String text, Font font, Graphics2D g2, TextAlign align) {
		this.text = text;
		this.font = font;
		this.fill = false;
		this.align = align;
		g2.setFont(font);
		bounds = new Rectangle(x, y, Utilities.getTextWidth(text, g2), Utilities.getTextHeight(text, g2));
	}
	
	public Button(int x, int y, String string, Font font, Graphics2D g2, Color color) {
		this(x,y,string,font,g2);
		this.color = color;
		align = TextAlign.CENTER;
	}
	
	public Button(int x, int y, String string, Font font, Graphics2D g2, Color color, TextAlign align) {
		this(x,y,string,font,g2, color);
		this.align = align;
	}

	public void draw(Graphics2D g2, boolean active) {
		g2.setFont(font);
		g2.setColor(new Color(75,0,0));
		Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f);
        Composite old = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g2.setComposite(comp);	
		if (fill) g2.fill(bounds);
		g2.setComposite(old);
		int x = 0;
		switch (align) {
		case CENTER:
			x = Utilities.getXForCenteredText(bounds.x + bounds.width/2, text, g2);
			break;
		case LEFT:
			x = bounds.x;
			break;
		case RIGHT:
			x = Utilities.getXForRightAlignedText(bounds.x + bounds.width, text, g2);
			break;
		default:
			x = Utilities.getXForCenteredText(bounds.x + bounds.width/2, text, g2);
			break;
		}
		int y = bounds.y + (int)(bounds.height/1.7);
		Color color = Color.white;
		if (active) color = Color.LIGHT_GRAY;
		if (this.color != null) color = this.color;
		Utilities.drawStringWithShadow(g2, text, color, x, y);
	}
	
	public boolean contains(Point point) {
		return bounds.contains(point);
	}
	public boolean contains(int x, int y) {
		return bounds.contains(x, y);
	}
	
	public enum TextAlign {
		CENTER, RIGHT, LEFT;
	}
	
}
