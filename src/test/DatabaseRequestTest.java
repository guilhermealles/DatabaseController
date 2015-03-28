package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import model.DatabaseRequest;
import model.RequestResponse;

public class DatabaseRequestTest {
	private static final String SERVER_ADDRESS = "192.168.0.104";
	private static final int SERVER_PORT = 10001;
	
	public static void main(String[] args) throws Exception {
		Socket s = null;
		DataOutputStream out;
		DataInputStream in;
		try {
			s = new Socket(SERVER_ADDRESS, SERVER_PORT);
			out = new DataOutputStream(s.getOutputStream());
			DatabaseRequest request = new DatabaseRequest(DatabaseRequest.LAST_7_DAYS);
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(request);
			
			in = new DataInputStream(s.getInputStream());
			ObjectInputStream object_stream = new ObjectInputStream(in);
			RequestResponse response = (RequestResponse)object_stream.readObject();
			System.out.println("From server: ");
			System.out.println(response.toString());
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
