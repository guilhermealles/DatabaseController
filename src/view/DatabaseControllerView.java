package view;

import java.sql.ResultSet;

import model.QueueMessage;
import controller.DatabaseConnectionController;
import controller.DatabaseRequestProcessingThread;
import controller.QueueMessageProcessingThread;

public class DatabaseControllerView {
	
	public static void main(String[] args) {
		/*
		 * Changing a little bit. The database controller will now create two separate threads which will run the sockets for
		 * receiving messages.
		 */
		QueueMessageProcessingThread queueThread = new QueueMessageProcessingThread();
		queueThread.start();
		DatabaseRequestProcessingThread requestThread = new DatabaseRequestProcessingThread();
		requestThread.start();
		System.out.println("log> Both threads already started!");
	}
	
	public static void StoreMessageInDatabase(QueueMessage message) {
		String query = GenerateQuery(message);
		DatabaseConnectionController connection_controller = new DatabaseConnectionController();
		connection_controller.connect();
		connection_controller.setQuery(query);
		try {
			connection_controller.executeUpdateQuery();
			System.out.println("log> Data stored.");
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		connection_controller.closeConnection();
	}

	public static String GenerateQuery(QueueMessage message) {
		final String TABLE_NAME = "NetComputing.ncProduction";
		String insert_clause = "INSERT INTO " + TABLE_NAME + " ";
		String values_clause = "VALUES ("
				+ String.valueOf(message.getOutletId()) + ", "
				+ String.valueOf(message.getPowerConsumption()) + ", NOW()" + ");";
		String final_query = insert_clause + values_clause;
		
		return final_query;
	}
	
	public static ResultSet QueryDatabase(String query) {
		System.out.println("log> Querying database...");
		
		DatabaseConnectionController connection_controller = new DatabaseConnectionController();
		connection_controller.connect();
		connection_controller.setQuery(query);
		try {
			ResultSet result = connection_controller.executeQuery();
			System.out.println("log> Querying OK.");
			return result;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
}
