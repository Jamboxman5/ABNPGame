package me.jamboxman5.abnpgame.assets.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class Fonts {

	public static Font TITLEFONT;
	public static Font SUBTITLEFONT;
	public static Font BUTTONFONT;
	
	public static void setupFonts() {
		try {
			InputStream is = Fonts.class.getResourceAsStream("/me/jamboxman5/abnpgame/resources/fonts/SASFONT.ttf");
			TITLEFONT = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD, 260);
			SUBTITLEFONT = TITLEFONT.deriveFont(Font.PLAIN, 100);
			BUTTONFONT = TITLEFONT.deriveFont(Font.PLAIN, 60);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
