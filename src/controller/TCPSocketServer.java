package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPSocketServer {
	public static final int QUEUE_MESSAGE_SOCKET_PORT = 10000;
	public static final int DATABASE_REQUEST_SOCKET_PORT = 10001;
	
	private int server_port;
	
	public TCPSocketServer(int port) throws Exception {
		if (port == QUEUE_MESSAGE_SOCKET_PORT || port == DATABASE_REQUEST_SOCKET_PORT) {
			this.server_port = port;
		}
		else {
			throw new Exception("Invalid port for server!");
		}
	}
	
	public void listen() {
		try {
			ServerSocket socket = new ServerSocket(server_port);
			
			System.out.println("socket> Socket is listening...");
			
			Socket client_socket = socket.accept();
			
			if (server_port == QUEUE_MESSAGE_SOCKET_PORT) {
				new QueueMessageConnectionThread(client_socket);
			}
			else if (server_port == DATABASE_REQUEST_SOCKET_PORT) {
				new DatabaseRequestConnectionThread(client_socket);
			}
			socket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
