package me.jamboxman5.abnpgame.managers;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;

import me.jamboxman5.abnpgame.assets.entity.Entity;
import me.jamboxman5.abnpgame.assets.maps.Map;
import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.main.GameStage;
import me.jamboxman5.abnpgame.util.Utilities;

public class MapManager {
	
	private final GamePanel gp;
	
	public List<Entity> entities = new ArrayList<>();
	
	Map m;
	
	public MapManager(GamePanel gamePanel) {
		gp = gamePanel;
		
		getMaps();
	}

	private void getMaps() {
		setup("Black_Isle");
		
	}

	public void draw(Graphics2D g2) {
		
		if (gp.getPlayer() == null) return;
				
		if (gp.getGameStage() != GameStage.InGameSinglePlayer &&
			gp.getGameStage() != GameStage.InGameMultiplayer) return;
		
        int screenX = (int) (0 - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX());
        int screenY = (int) (0 - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY());
		
//		int screenX = 0;
//		int screenY = 0;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
		
		g2.drawImage(m.getImage(), screenX, screenY, null);
		
	}
	
	public void updateEntities() {
		for (Entity e : entities) { e.update(); }
	}
	
	public void drawEntities(Graphics2D g2) {
		for (Entity e : entities) { e.draw(g2); }
	}
	
	public void setup(String imageName) {
        try {
            m = new Map(imageName);
            m.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/me/jamboxman5/abnpgame/resources/maps/" + imageName + ".png"))));
            m.setImage(Utilities.scaleImage(m.getImage(), m.getImage().getWidth()*2, m.getImage().getHeight()*2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public Map getActiveMap() {	return m; }
	
	public void addEntity(Entity entity) { entities.add(entity); }

	public List<Entity> getEntities() { return entities; }
}
