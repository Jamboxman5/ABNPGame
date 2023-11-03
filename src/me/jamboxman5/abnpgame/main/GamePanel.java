package me.jamboxman5.abnpgame.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import me.jamboxman5.abnpgame.assets.entity.player.OnlinePlayer;
import me.jamboxman5.abnpgame.assets.entity.player.Player;
import me.jamboxman5.abnpgame.managers.KeyHandler;
import me.jamboxman5.abnpgame.managers.MapManager;
import me.jamboxman5.abnpgame.managers.MouseHandler;
import me.jamboxman5.abnpgame.managers.MouseMotionHandler;
import me.jamboxman5.abnpgame.managers.UIManager;
import me.jamboxman5.abnpgame.net.GameClient;
import me.jamboxman5.abnpgame.net.GameServer;
import me.jamboxman5.abnpgame.net.packets.Packet00Login;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = -1240054075035218830L;
	
	JFrame window;
	private Graphics2D graphics2D;
	private BufferedImage tempScreen;
	
	private final int screenHeight = 720;
	private final int screenWidth = 1280;
	private final int FPS = 60;
	
	private Thread gameThread;
	private GameStage stage = GameStage.MainMenu;
	
	private final KeyHandler keyHandler = new KeyHandler(this);
	private final MapManager mapManager = new MapManager(this);
	private final MouseMotionHandler mouseMotionHandler = new MouseMotionHandler(this);
	private final MouseHandler mouseActionHandler = new MouseHandler(this);
	private final UIManager ui = new UIManager(this, keyHandler);
	
	public Player player;
	private Point mousePointer;
	
	private GameClient socketClient;
	private GameServer socketServer;
	
	public GamePanel() {
		
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setBackground(Color.black);
		setDoubleBuffered(true);
		addKeyListener(keyHandler); 
		addMouseMotionListener(mouseMotionHandler);
		addMouseListener(mouseActionHandler);
		setFocusable(true);
				
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
//        long drawStart = 0;
//        if (keyHandler.isShowDebugText()) {
//            drawStart = System.nanoTime();
//        }

        if (stage == GameStage.MainMenu) {
            ui.draw(graphics2D);
            if (getMousePosition() != null) {
            	mouseMotionHandler.draw(graphics2D);
            }
        } else {
        	if (player == null) return;
            // TILES
            mapManager.draw(graphics2D);
			mapManager.drawEntities(graphics2D);
			player.draw(graphics2D);
			if (getMousePosition() != null) {
            	mouseMotionHandler.draw(graphics2D);
            }
//            drawInteractiveTiles(graphics2D);

            // ASSETS
//            addAssets();
//            sortAssets();
//            drawAssets(graphics2D);
//            assets.clear();

            //UI
            ui.draw(graphics2D);
        }

        // DEBUG
//        if (keyHandler.isShowDebugText()) {
//            drawDebugInfo(graphics2D, drawStart);
//        }
    }
	
	public void delta() {
		
		if (stage == GameStage.MainMenu) {
			ui.update();
		} else {
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

	public void setUp() {
		
		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		graphics2D = (Graphics2D) tempScreen.getGraphics();
		mouseMotionHandler.setupCursor("Cursor_Pointer");

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
	public Point getMousePointer() { return mousePointer; }
	public GameClient getClient() { return socketClient; }

	public GameStage getGameStage() { return stage; }
	public void setGameStage(GameStage newStage) { stage = newStage; }
	public int getScreenCenterX() { return getScreenWidth()/2; }
	public int getScreenCenterY() { return (getScreenHeight()/2) - 32; }

	public void quitGame() { System.exit(0); }

	public void moveToMultiplayerMenu() {
		mouseMotionHandler.setupCursor("Cursor_Reticle");

		String name = JOptionPane.showInputDialog("Input Gamertag: ");
		if (name == null) name = "";
		
		if (JOptionPane.showConfirmDialog(this, "Run as server?") == 0) {
		socketServer = new GameServer(this);
		socketServer.start();
		}
		
		socketClient = new GameClient(this, "localhost");
		socketClient.start();
		
		player = new OnlinePlayer(this, keyHandler, name, null, -1);
		Packet00Login loginPacket = new Packet00Login(name);

		if (socketServer != null) {
			socketServer.addConnection((OnlinePlayer) player, loginPacket);
		}
		
		loginPacket.writeData(socketClient);
		
		
		setGameStage(GameStage.InGameMultiplayer);
	}

	public void moveToSingleplayerMenu() {
		mouseMotionHandler.setupCursor("Cursor_Reticle");
		player = new Player(this, keyHandler, "");
		setGameStage(GameStage.InGameSinglePlayer);
	}

	public KeyHandler getKeyHandler() { return keyHandler; }

}
