package me.jamboxman5.abnpgame.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import me.jamboxman5.abnpgame.assets.entity.Entity;
import me.jamboxman5.abnpgame.assets.entity.player.OnlinePlayer;
import me.jamboxman5.abnpgame.assets.entity.player.Player;
import me.jamboxman5.abnpgame.managers.KeyHandler;
import me.jamboxman5.abnpgame.managers.MapManager;
import me.jamboxman5.abnpgame.managers.MouseHandler;
import me.jamboxman5.abnpgame.managers.MouseMotionHandler;
import me.jamboxman5.abnpgame.managers.UIManager;
import me.jamboxman5.abnpgame.managers.WindowHandler;
import me.jamboxman5.abnpgame.net.GameClient;
import me.jamboxman5.abnpgame.net.GameServer;
import me.jamboxman5.abnpgame.net.packets.Packet00Login;
import me.jamboxman5.abnpgame.net.packets.Packet01Disconnect;
import me.jamboxman5.abnpgame.sound.SoundManager;
import me.jamboxman5.abnpgame.util.Utilities;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = -1240054075035218830L;
	
	public JFrame window;
	private Graphics2D graphics2D;
	private BufferedImage tempScreen;
	
	private final int screenHeight = 720;
	private final int screenWidth = 1280;
	private final int FPS = 60;
	private float zoomScale = 1f;
	
	private Thread gameThread;
	private GameStage stage = GameStage.MainMenu;
	private GameStage nextStage;
	
	private final KeyHandler keyHandler = new KeyHandler(this);
	private final MapManager mapManager = new MapManager(this);
	private final MouseMotionHandler mouseMotionHandler = new MouseMotionHandler(this);
	private final MouseHandler mouseActionHandler = new MouseHandler(this);
	private final UIManager ui = new UIManager(this, keyHandler);
	private final WindowHandler windowHandler = new WindowHandler(this);
	
	private final SoundManager music = new SoundManager();
	private final SoundManager sfx = new SoundManager();
	
	public Player player;
	private Point mousePointer;
	private boolean debugMode = false;
	
	private GameClient socketClient;
	private GameServer socketServer;
	
	public GamePanel(JFrame frame) {
		
		window = frame;
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setBackground(Color.black);
		setDoubleBuffered(true);
		addKeyListener(keyHandler); 
		addMouseMotionListener(mouseMotionHandler);
		addMouseListener(mouseActionHandler);
		setFocusable(true);
		window.addWindowListener(windowHandler);				

	}
	
	public void lambda() {
		drawToTempScreen();
		drawToScreen();
	}
	
    public void drawToScreen() {
        Graphics graphics = getGraphics();
        graphics.drawImage(tempScreen, 0, 0, screenWidth, screenHeight, null);
        graphics.dispose();
    }
	
	public void drawToTempScreen() {

        // DEBUG
        long drawStart = System.nanoTime();
        if (keyHandler.isShowDebugText()) {
            drawStart = System.nanoTime();
        }

        if (stage == GameStage.MainMenu) {
            ui.draw(graphics2D);
            if (getMousePosition() != null) {
            	mouseMotionHandler.draw(graphics2D);
            }
        } else if (stage == GameStage.MultiplayerMenu) {
        	ui.draw(graphics2D);
            if (getMousePosition() != null) {
            	mouseMotionHandler.draw(graphics2D);
            }
        } else if (stage.toString().contains("InGame")){
        	if (player == null) return;
            // TILES
            mapManager.draw(graphics2D);
			mapManager.drawEntities(graphics2D);
			player.draw(graphics2D);
			if (getMousePosition() != null) {
            	mouseMotionHandler.draw(graphics2D);
            }
            ui.draw(graphics2D);
        } else if (stage == GameStage.MapSelector) {
        	ui.draw(graphics2D);
            if (getMousePosition() != null) {
            	mouseMotionHandler.draw(graphics2D);
            }        
        }

//         DEBUG
        if (isDebugMode()) {
            drawDebugInfo(graphics2D, drawStart);
        }
    }
	
	public void delta() {
		
		if (stage == GameStage.MainMenu ||
			stage == GameStage.MultiplayerMenu ||
			stage == GameStage.MapSelector) {
			ui.update();
		} else if (stage.toString().contains("InGame")){
			if (player == null) return;
			player.update();
			ui.update();
			mapManager.updateEntities();
		} 
		
	}
	
	@Override
	public void run() {
		double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                lambda();
                delta();
                delta--;
            }
        }
	}
	
	private void drawDebugInfo(Graphics2D g2, long drawStart) {
        long drawEnd = System.nanoTime();
        long passedTime = drawEnd - drawStart;
        long durationInMs = TimeUnit.NANOSECONDS.toMillis(passedTime);

        
        String debugTXT = "World X: " + String.format("%,.2f", getPlayer().getWorldX());
        int y = 50;
        int spacer = 30;
        int x = Utilities.getXForRightAlignedText(getScreenWidth()-30, debugTXT, g2);
        Utilities.drawStringShadow(g2, debugTXT, x, y);
        g2.drawString(debugTXT, x, y);
        //
        debugTXT = "World Y: " + String.format("%,.2f", getPlayer().getWorldY());
        y+=spacer;
        x = Utilities.getXForRightAlignedText(getScreenWidth()-30, debugTXT, g2);
        Utilities.drawStringShadow(g2, debugTXT, x, y);
        g2.drawString(debugTXT, x, y);
        //
        debugTXT = "Player Rotation: " + Math.toDegrees(player.getAngleToCursor());
        y+=spacer;
        x = Utilities.getXForRightAlignedText(getScreenWidth()-30, debugTXT, g2);
        Utilities.drawStringShadow(g2, debugTXT, x, y);
        g2.drawString(debugTXT, x, y);
        //
        debugTXT = "Mouse X: " + String.format("%,.2f", mousePointer.getX());
        y+=spacer;
        x = Utilities.getXForRightAlignedText(getScreenWidth()-30, debugTXT, g2);
        Utilities.drawStringShadow(g2, debugTXT, x, y);
        g2.drawString(debugTXT, x, y);
        //
        debugTXT = "Mouse Y: " + String.format("%,.2f", mousePointer.getY());
        y+=spacer;
        x = Utilities.getXForRightAlignedText(getScreenWidth()-30, debugTXT, g2);
        Utilities.drawStringShadow(g2, debugTXT, x, y);
        g2.drawString(debugTXT, x, y);
        //
        debugTXT = "Frame Time: " + durationInMs + "ms";
        y+=spacer;
        x = Utilities.getXForRightAlignedText(getScreenWidth()-30, debugTXT, g2);
        Utilities.drawStringShadow(g2, debugTXT, x, y);
        g2.drawString(debugTXT, x, y);
    }

	public void setUp() {
		
		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		graphics2D = (Graphics2D) tempScreen.getGraphics();
		mouseMotionHandler.setupCursor("Cursor_Pointer");
		music.setFile("music/Menu_Ambience");
		music.loop();

	}

	public void start() {
		
		setUp();
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}

	public int getScreenWidth() { return screenWidth; }
	public int getScreenHeight() { return screenHeight; }
	public MapManager getMapManager() { return mapManager; }
	public Player getPlayer() {	return player; }
	public void setMousePointer(Point location) { mousePointer = location; }
	public Point getMousePointer() { 
		if (mousePointer == null) return new Point(player.getAdjustedScreenX(), player.getAdjustedScreenY());
		else return mousePointer; 
	}
	public GameClient getClient() { return getSocketClient(); }

	public GameStage getGameStage() { return stage; }
	public void setGameStage(GameStage newStage) { stage = newStage; }
	public int getScreenCenterX() { return getScreenWidth()/2; }
	public int getScreenCenterY() { return (getScreenHeight()/2) - 32; }

	public void quitGame() { System.exit(0); }

	public void moveToMultiplayerMenu() {
		setGameStage(GameStage.MultiplayerMenu);
	}
	
	public void moveToMapSelect(GameStage nextStage) {
		setGameStage(GameStage.MapSelector);
		this.nextStage = nextStage;
	}
	
	public void moveToMultiplayerGame(boolean hosting) {
		mouseMotionHandler.setupCursor("Cursor_Reticle");

		String name = JOptionPane.showInputDialog("Input Gamertag: ");
		if (name == null) name = "";
		if (name.equalsIgnoreCase("")) name = "Spare Brains";
		
		if (hosting && socketServer == null) {
		socketServer = new GameServer(this);
		socketServer.start();
		}
		
		setSocketClient(new GameClient(this, "67.246.103.207"));
		getSocketClient().start();
		
		player = new OnlinePlayer(this, keyHandler, name, null, -1);
		Packet00Login loginPacket = new Packet00Login(name, player.getWorldX(), player.getWorldY());

		if (socketServer != null) {
			socketServer.addConnection((OnlinePlayer) player, loginPacket);
		}
		
		music.stop();
		loginPacket.writeData(getSocketClient());
		
		
		setGameStage(GameStage.InGameMultiplayer);
	}

	public void moveToSingleplayerGame() {
		mouseMotionHandler.setupCursor("Cursor_Reticle");
		player = new Player(this, keyHandler, "");
		setGameStage(GameStage.InGameSinglePlayer);
		music.stop();
	}

	public KeyHandler getKeyHandler() { return keyHandler; }

	public void backToMainMenu() {
		debugMode = false;
		if (getGameStage() != GameStage.MultiplayerMenu) {
			music.setFile("music/Menu_Ambience");
			music.loop();
			mouseMotionHandler.setupCursor("Cursor_Pointer");
		}
		if (getGameStage() == GameStage.InGameMultiplayer) {
			Packet01Disconnect packet = new Packet01Disconnect(getPlayer().getUsername());
			packet.writeData(getSocketClient());
		} 
		setGameStage(GameStage.MainMenu);
		if (getSocketClient() != null) setSocketClient(null);
		if (player != null) player = null;
	}

	public GameClient getSocketClient() {
		return socketClient;
	}

	public void setSocketClient(GameClient socketClient) {
		this.socketClient = socketClient;
	}
	
	public UIManager getUi() { return ui; }

	public void playMusic(String soundName) {
        music.setFile(soundName);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(String soundName) {
        sfx.setFile(soundName);
        sfx.play();
    }

	public GameStage getNextStage() {
		return nextStage;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}
	public float getZoom() { return zoomScale; }
	public void setZoom(float zoom) { zoomScale = zoom; }

	public void zoomIn() {
		setZoom(getZoom()+.002f);
		for (Entity e : mapManager.getEntities()) {
			e.setWorldX(e.getWorldX()*(getZoom()/(getZoom()-.00208)));
			e.setWorldY(e.getWorldY()*(getZoom()/(getZoom()-.00208)));
		}
		player.setWorldX(player.getWorldX()*(getZoom()/(getZoom()-.00208)));
		player.setWorldY(player.getWorldY()*(getZoom()/(getZoom()-.00208)));
	}
	public void zoomOut() {
		setZoom(getZoom()-.005f);
		for (Entity e : mapManager.getEntities()) {
			e.setWorldX(e.getWorldX()*(getZoom()/(getZoom()+.00508)));
			e.setWorldY(e.getWorldY()*(getZoom()/(getZoom()+.00508)));
		}
		player.setWorldX(player.getWorldX()*(getZoom()/(getZoom()+.00508)));
		player.setWorldY(player.getWorldY()*(getZoom()/(getZoom()+.00508)));
	}
}
