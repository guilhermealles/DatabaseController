package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DatabaseRequestConnectionThread extends Thread {
	private DataInputStream in;
	private DataOutputStream out;
	private Socket client_socket;
	
	public DatabaseRequestConnectionThread(Socket client_socket) {
		this.client_socket = client_socket;
		try {
			in = new DataInputStream(client_socket.getInputStream());
			out = new DataOutputStream(client_socket.getOutputStream());
			this.start();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		// TODO insert code here		
	}
}
