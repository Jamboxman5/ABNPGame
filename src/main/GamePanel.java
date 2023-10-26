package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import assets.entity.player.Player;
import managers.KeyHandler;
import managers.MapManager;
import managers.MouseHandler;
import managers.MouseMotionHandler;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = -1240054075035218830L;
	
	JFrame window;
	private Graphics2D graphics2D;
	private BufferedImage tempScreen;
	
	private final int screenHeight = 720;
	private final int screenWidth = 1280;
	private final int FPS = 60;
	
	private Thread gameThread;
	
	
	private final KeyHandler keyHandler = new KeyHandler(this);
	private final MapManager mapManager = new MapManager(this);
	private final MouseMotionHandler mouseMotionHandler = new MouseMotionHandler(this);
	private final MouseHandler mouseActionHandler = new MouseHandler(this);
	
	private final Player player = new Player(this, keyHandler);
	private Point mousePointer;
	
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

//        if (gameState == titleState) {
//            ui.draw(graphics2D);
//        } else {

            // TILES
            mapManager.draw(graphics2D);
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

            // UI
//            ui.draw(graphics2D);
//        }

        // DEBUG
//        if (keyHandler.isShowDebugText()) {
//            drawDebugInfo(graphics2D, drawStart);
//        }
    }
	
	public void delta() {
		player.update();
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
		mouseMotionHandler.setupCursor("Cursor_Reticle");
		
	}

	public void start() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public int getScreenWidth() { return screenWidth; }
	public int getScreenHeight() { return screenHeight; }
	public MapManager getMapManager() { return mapManager; }
	public Player getPlayer() {	return player; }
	public void setMousePointer(Point location) { mousePointer = location; }
	public Point getMousePointer() { return mousePointer; }

}
