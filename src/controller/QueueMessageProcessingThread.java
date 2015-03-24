package controller;

import java.util.LinkedList;

import model.QueueMessage;

public class QueueMessageProcessingThread extends Thread {
	private TCPSocketServer server;
	public static LinkedList<QueueMessage> buffer;
	
	public QueueMessageProcessingThread() {
		try {
			server = new TCPSocketServer(TCPSocketServer.QUEUE_MESSAGE_SOCKET_PORT);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		buffer = new LinkedList<QueueMessage>();
	}
	
	public void run() {
		while(true) {
			server.listen();
		}
	}
}
