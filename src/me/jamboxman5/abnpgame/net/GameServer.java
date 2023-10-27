package me.jamboxman5.abnpgame.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import me.jamboxman5.abnpgame.main.GamePanel;

public class GameServer extends Thread {

	private DatagramSocket socket;
	private GamePanel gp;
	
	public GameServer(GamePanel gamePanel) {
		gp = gamePanel;
		try {
			socket = new DatagramSocket(13331);
		} catch (SocketException e) {
			e.printStackTrace();
		} 
	}
	
	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String message = new String(packet.getData()).trim();
			System.out.println("CLIENT [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "] > " + message);
			if (message.equalsIgnoreCase("ping")) {
				System.out.println("Returning pong...");
				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
			}
		}
	}
	
	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
