package view;

import model.QueueMessage;
import controller.DatabaseConnectionController;
import controller.QueueMessageProcessingThread;

public class DatabaseControllerView {
	// public static LinkedList<QueueMessage> buffer;
	// public static LinkedList<RquestResponse> buffer;
	
	public static void main(String[] args) {
		/*
		 * Changing a little bit. The database controller will now create two separate threads which will run the sockets for
		 * receiving messages.
		 */
		QueueMessageProcessingThread thread = new QueueMessageProcessingThread();
		thread.start();
	}
	
	public static void StoreMessageInDatabase(QueueMessage message) {
		System.out.println("Sending to DB...");
		
		String query = GenerateQuery(message);
		DatabaseConnectionController connection_controller = new DatabaseConnectionController();
		connection_controller.connect();
		connection_controller.setQuery(query);
		try {
			connection_controller.executeQuery();
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		connection_controller.closeConnection();
		
		System.out.println("Sent!");
		
	}

	public static String GenerateQuery(QueueMessage message) {
		final String TABLE_NAME = "netcomputing-simple";
		String insert_clause = "INSERT INTO " + TABLE_NAME + " ";
		String values_clause = "VALUE ("
				+ String.valueOf(message.getOutletId()) + ", "
				+ String.valueOf(message.getPowerConsumption()) + ");";
		String final_query = insert_clause + values_clause;
		
		return final_query;
	}
}
