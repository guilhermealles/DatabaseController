package controller;

import java.util.LinkedList;

import model.QueueMessage;

public class DatabaseRequestProcessingThread extends Thread {
	private TCPSocketServer server;
	private LinkedList<String> buffer; //The queries buffer
	
	public DatabaseRequestProcessingThread() {
		try {
			server = new TCPSocketServer(TCPSocketServer.DATABASE_REQUEST_SOCKET_PORT);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		buffer = new LinkedList<String>();
	}
	
	public void run() {
		while (true) {
			server.listen();
		}
	}

}
