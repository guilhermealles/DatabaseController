package model;

import java.io.Serializable;

public class QueueMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int outlet_id;
	private double outlet_power_consumption;
	
	public QueueMessage(int outlet_id, double outlet_power_consumption) {
		this.outlet_id = outlet_id;
		this.outlet_power_consumption = outlet_power_consumption;
	}
	
	public int getOutletId() {
		return this.outlet_id;
	}
	
	public double getPowerConsumption() {
		return this.outlet_power_consumption;
	}
}
