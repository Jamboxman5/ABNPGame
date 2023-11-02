package me.jamboxman5.abnpgame.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import me.jamboxman5.abnpgame.assets.entity.player.OnlinePlayer;
import me.jamboxman5.abnpgame.assets.entity.player.Player;
import me.jamboxman5.abnpgame.main.GamePanel;
import me.jamboxman5.abnpgame.net.packets.Packet;
import me.jamboxman5.abnpgame.net.packets.Packet.PacketTypes;
import me.jamboxman5.abnpgame.net.packets.Packet00Login;

public class GameServer extends Thread {

	private DatagramSocket socket;
	private GamePanel gp;
	
	private List<OnlinePlayer> connectedPlayers = new ArrayList<>();
	
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
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
//			String message = new String(packet.getData()).trim();
//			System.out.println("CLIENT [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "] > " + message);
//			if (message.equalsIgnoreCase("ping")) {
//				System.out.println("Returning pong...");
//				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
//			}
		}
	}
	
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = Packet.lookupPacket(message.substring(0,2));
		switch (type) {
		default:
			break;
		case INVALID:
			break;
		case LOGIN:
			Packet00Login packet = new Packet00Login(data);
			System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername() + " has connected.");
			OnlinePlayer player = null;
			System.out.println(address.getHostAddress());
			if (address.getHostAddress().equalsIgnoreCase(socket.getInetAddress().getHostAddress())) {
				player = new OnlinePlayer(gp, gp.getKeyHandler(), packet.getUsername(), address, port);
			} else {
				player = new OnlinePlayer(gp, packet.getUsername(), address, port);
			}
			if (player != null) {
				connectedPlayers.add(player);
			}
			break;
		case DISCONNECT:
			break;
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

	public void sendDataToAllClients(byte[] data) {
		for (OnlinePlayer p: connectedPlayers) {
			sendData(data, p.ipAddress, p.port);
		}
	}
}
