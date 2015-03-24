package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import view.DatabaseControllerView;
import model.QueueMessage;

public class QueueMessageConnectionThread extends Thread {
	private DataInputStream in;
	private DataOutputStream out;
	private Socket client_socket;
	
	public QueueMessageConnectionThread(Socket client_socket) {
		this.client_socket = client_socket;
		try {
			in = new DataInputStream(this.client_socket.getInputStream());
			out = new DataOutputStream(this.client_socket.getOutputStream());
			this.start();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		// De-serialize the QueueMessage
		try {
			ObjectInputStream object_stream = new ObjectInputStream(in);
			QueueMessage data = (QueueMessage)object_stream.readObject();
			// TODO still have to check if the following works
			if (data != null) {
				DatabaseControllerView.StoreMessageInDatabase(data);
			}
		}
		catch (Exception e) {
			System.err.println("Error when de-serializing QueueMessage");
		}
	}
}
