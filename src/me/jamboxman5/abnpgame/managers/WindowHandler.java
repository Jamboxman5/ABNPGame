package me.jamboxman5.abnpgame.managers;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.net.packets.Packet01Disconnect;

public class WindowHandler implements WindowListener {

	private final GamePanel gp;
	
	public WindowHandler(GamePanel gamePanel) {
		gp = gamePanel;
	}
	
	@Override
	public void windowOpened(WindowEvent e) {		
	}

	@Override
	public void windowClosing(WindowEvent e) {	
		if (gp.getPlayer() != null && 
			gp.getClient() != null) {
			Packet01Disconnect packet = new Packet01Disconnect(gp.getPlayer().getUsername());
			packet.writeData(gp.getSocketClient());
		}
		
	}

	@Override
	public void windowClosed(WindowEvent e) {		
	}

	@Override
	public void windowIconified(WindowEvent e) {		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {		
	}

	@Override
	public void windowActivated(WindowEvent e) {		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {		
	}

}
