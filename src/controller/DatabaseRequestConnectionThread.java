package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;

import rmi.base.DatabaseRequest;
import rmi.base.RequestResponse;
import view.DatabaseControllerView;

public class DatabaseRequestConnectionThread extends Thread {
	private DataInputStream in;
	private DataOutputStream out;
	private Socket client_socket;
	
	public DatabaseRequestConnectionThread(Socket client_socket) {
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
		// De-serialize the request
		try {
			ObjectInputStream object_stream = new ObjectInputStream(in);
			DatabaseRequest data = (DatabaseRequest)object_stream.readObject();
			if (data != null) {
				String query = generateQuery(data);
				ResultSet result = DatabaseControllerView.QueryDatabase(query);
				RequestResponse response = processResultSet(result);
				// serialize response and send it back to the client
				ObjectOutputStream oos = new ObjectOutputStream(out);
				oos.writeObject(response);
			}
		}
		catch (Exception e) {
			System.err.println("Error when de-serializing DatabaseRequest");
		}
	}
	
	private String generateQuery(DatabaseRequest request) {
		String select_clause;
		String from_clause;
		String where_clause = "";
		
		if (request.getOutlet() == 0) {
			select_clause = "SELECT id, consumption "; // if no outlet is selected
		}
		else {
			select_clause = "SELECT consumption"; // if one outlet is selected
		}
		
		from_clause = " FROM NetComputing.ncProduction ";
		
		switch(request.getType()) {
		case DatabaseRequest.LAST_7_DAYS:
			if (request.getOutlet() == 0) {
				where_clause = " WHERE DATEDIFF(NOW(), date_added) <= 7; ";
			}
			else {
				where_clause = " WHERE DATEDIFF(NOW(), date_added) <= 7 AND id=" + String.valueOf(request.getOutlet()) + ";";
			}
			break;
		case DatabaseRequest.LAST_30_DAYS:
			if (request.getOutlet() == 0) {
				where_clause = " WHERE DATEDIFF(NOW(), date_added) <= 30; ";
			}
			else {
				where_clause = " WHERE DATEDIFF(NOW(), date_added) <= 30 AND id=" + String.valueOf(request.getOutlet()) + ";";
			}
			break;
		case DatabaseRequest.LAST_365_DAYS:
			if (request.getOutlet() == 0) {
				where_clause = " WHERE DATEDIFF(NOW(), date_added) <= 365; ";
			}
			else {
				where_clause = " WHERE DATEDIFF(NOW(), date_added) <= 365 AND id=" + String.valueOf(request.getOutlet()) + ";";
			}
			break;
		case DatabaseRequest.ALL_DATA:
			if (request.getOutlet() == 0) {
				where_clause = " ; ";
			}
			else {
				where_clause = " WHERE id=" + String.valueOf(request.getOutlet()) + ";";
			}
			break;
		}
		String query = select_clause + from_clause + where_clause;
		return query;
	}
	
	private RequestResponse processResultSet(ResultSet result) {
		RequestResponse response = new RequestResponse();
		
		if (result != null) {
			try {
				while (result.next()) {
					int id = result.getInt("id");
					double consumption = result.getDouble("consumption");
					response.addConsumption(id, consumption);
				}
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		
		return response;
	}
}
