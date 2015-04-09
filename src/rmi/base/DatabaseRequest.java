package rmi.base;

import java.io.Serializable;

public class DatabaseRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final int LAST_7_DAYS = 1;
	public static final int LAST_30_DAYS = 2;
	public static final int LAST_365_DAYS = 3;
	public static final int ALL_DATA = 4;

	private int type;
	private int outlet; // A 0 value in this attribute means "get data from all the outlets";
	
	public DatabaseRequest(int type) throws Exception {
		if (type < 1 || type > 4) {
			throw new Exception("Invalid type for constructor of DatabaseRequest!");
		}
		else {
			this.type = type;
		}
	}
	
	public DatabaseRequest(int type, int outlet) throws Exception {
		if (type < 1 || type > 4) {
			throw new Exception("Invalid type for constructor of DatabaseRequest!");
		}
		else {
			this.type = type;
			this.outlet = outlet;
		}
	}
	
	public int getType() {
		return this.type;
	}
	
	public int getOutlet() {
		return this.outlet;
	}
}
