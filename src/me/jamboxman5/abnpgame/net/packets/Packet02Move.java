package me.jamboxman5.abnpgame.net.packets;

import me.jamboxman5.abnpgame.net.GameClient;
import me.jamboxman5.abnpgame.net.GameServer;

public class Packet02Move extends Packet {

	private double x, y, rotation;
	private boolean invert;
	private String username;
	
	public Packet02Move(byte[] data) {
		super(02);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Double.parseDouble(dataArray[1]);
		this.y = Double.parseDouble(dataArray[2]);
		this.rotation = Double.parseDouble(dataArray[3]);
		this.invert = Boolean.valueOf(dataArray[4]);
	}
	
	public Packet02Move(String username, double x, double y, double rotation, boolean invert) {
		super(02);
		this.username = username;
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.invert = invert;
	}

	@Override
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		return ("02" + this.username + "," + this.x + "," + this.y + "," + this.rotation + "," + this.invert).getBytes();
	}
	
	public String getUsername() { return username; }
	public double getX() { return x; }
	public double getY() { return y; }
	public double getRotation() { return rotation; }
	public boolean invertAngle() { return invert; }
}
