package me.jamboxman5.abnpgame.managers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;

import me.jamboxman5.abnpgame.assets.entity.Entity;
import me.jamboxman5.abnpgame.assets.entity.player.OnlinePlayer;
import me.jamboxman5.abnpgame.assets.maps.Map;
import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.main.GameStage;
import me.jamboxman5.abnpgame.util.Utilities;

public class MapManager {
	
	private final GamePanel gp;
	
	public List<Entity> entities = new ArrayList<>();
	public List<Map> maps = new ArrayList<>();
	
	Map m;
	
	public MapManager(GamePanel gamePanel) {
		gp = gamePanel;
		
		getMaps();
	}

	private void getMaps() {
		setup("Verdammtenstadt");
		setup("Black_Isle");
		setup("Farmhouse");
		setup("Karnivale");
		setup("Airbase");
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
		
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		g2.drawImage(m.getImage(), screenX, screenY,(int) (m.getImage().getWidth()*gp.getZoom()), (int)(m.getImage().getHeight()*gp.getZoom()), null);
		
	}
	
	public void updateEntities() {
		for (Entity e : entities) { e.update(); }
	}
	
	public void drawEntities(Graphics2D g2) {
		for (Entity e : entities) { e.draw(g2); }
	}
	
	public void setup(String imageName) {
        try {
        	int x = 0;
        	int y = 0;
        	if (imageName.equals("Black_Isle")) {
    			x = 1753;
    			y = 1232;
    		} else if (imageName.equals("Verdammtenstadt")) {
    			x = 1426;
    			y = 1374;
    		} else if (imageName.equals("Farmhouse")) {
    			x = 583;
    			y = 483;
    		} else if (imageName.equals("Airbase")) {
    			x = 583;
    			y = 483;
    		} else if (imageName.equals("Karnivale")) {
    			x = 583;
    			y = 483;
    		} 
            m = new Map(imageName, x, y);
            m.setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/me/jamboxman5/abnpgame/resources/maps/" + imageName + ".png"))));
            m.setImage(Utilities.scaleImage(m.getImage(), (int)(m.getImage().getWidth()*1.4), (int)(m.getImage().getHeight()*1.4)));
            maps.add(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public Map getActiveMap() {	return m; }
	
	public void addEntity(Entity entity) { entities.add(entity); }

	public List<Entity> getEntities() { return entities; }

	public void removeConnectedPlayer(String username) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) instanceof OnlinePlayer && 
				((OnlinePlayer)entities.get(i)).getUsername().equals(username)) {
				entities.remove(i);
				break;
			}
		}
	}
	
	private int getConnectedPlayerIndex(String username) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) instanceof OnlinePlayer && 
				((OnlinePlayer)entities.get(i)).getUsername().equals(username)) {
				return i;
			}
		}
		return -1;
	}
	
	public void movePlayer(String username, double x, double y, double rotation, boolean invert) {
		int index = getConnectedPlayerIndex(username);
		if (index < 0) return;
		entities.get(index).setWorldX(x*(1/gp.getZoom()));
		entities.get(index).setWorldY(y*(1/gp.getZoom()));
		entities.get(index).setRotation(rotation);
		((OnlinePlayer)entities.get(index)).setInvert(invert);
	}

	public void setMap(int mapIndex) {
		m = maps.get(mapIndex);
	}

	public void setMap(String map) {
		for(Map m2 : maps) {
			if (m2.toString().equals(map)) {
				m = m2;
				gp.getPlayer().setWorldX(m.getDefaultX());
				gp.getPlayer().setWorldY(m.getDefaultY());
				return;
			}
		}
	}
}
