package rmi.base;

import java.io.Serializable;
import java.util.ArrayList;

public class RequestResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Integer> id_list;
	private ArrayList<Double> consumption_list;
	
	public RequestResponse() {
		id_list = new ArrayList<Integer>();
		consumption_list = new ArrayList<Double>();
	}
	
	public void addConsumption(int id, double consumption) {
		// Searches for the id index and add the consumption value. If the id is not found, add a new entry to the list
		for (int i = 0; i < id_list.size(); i++) {
			if (id_list.get(i) == id) {
				double old_consumption = consumption_list.get(i);
				double new_consumption = old_consumption + consumption;
				consumption_list.remove(i);
				consumption_list.add(i, new_consumption);
				return;
			}
		}
		// If the flow reaches this code, the id was not found in the list.
		id_list.add(id);
		consumption_list.add(consumption);
		return;
	}
	
	public String toString() {
		String returnVal = "ID List Size: " + id_list.size() + ", \nConsumption List Size: " + consumption_list.size() + ";\n\n";
		
		for (int i=0; i < id_list.size(); i++) {
			returnVal += "ID: " + id_list.get(i) + ", Consumption: " + consumption_list.get(i) + ";\n";
		}
		
		return returnVal;
	}
	
	public ArrayList<Integer> getIdList() {
		return this.id_list;
	}
	
	public ArrayList<Double> getConsumptionList() {
		return this.consumption_list;
	}
}
