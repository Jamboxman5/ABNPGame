package me.jamboxman5.abnpgame.assets.entity.player;

import java.net.InetAddress;

import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.managers.KeyHandler;

public class OnlinePlayer extends Player {

	public InetAddress ipAddress;
	public int port;
	
	public OnlinePlayer(GamePanel gamePanel, KeyHandler keyHandler, String name, InetAddress ipAddress, int port) {
		super(gamePanel, keyHandler, name);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public OnlinePlayer(GamePanel gamePanel, String name, InetAddress ipAddress, int port) {
		super(gamePanel, null, name);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	@Override
	public void update() {
		super.update();
	}

}
