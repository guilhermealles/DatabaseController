package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import model.QueueMessage;

// This class implements a TCP client socket to test whether the TCP server socket works
public class ServerSocketTest {
	private static String SERVER_ADDRESS = "localhost";
	private static int SERVER_PORT = 10000;
	
	public static void main(String[] args) {
		Socket s = null;
		DataOutputStream out;
		DataInputStream in;
		try {
			s = new Socket(SERVER_ADDRESS, SERVER_PORT);
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
			//out.writeUTF("Hello from Guilherme!");
			QueueMessage message = new QueueMessage(1, 42);
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(message);
			
			//String data = in.readUTF();
			//System.out.println("From server: " + data);
		}
		catch (UnknownHostException e) {
			System.err.println(e);
		}
		catch (IOException e) {
			System.err.println(e);
		}
		finally {
			if (s != null) {
				try {
					s.close();
				}
				catch (IOException e) {
					System.err.println(e);
				}
			}
		}
	}
	
}
