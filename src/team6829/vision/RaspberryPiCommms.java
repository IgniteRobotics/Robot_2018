package team6829.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class RaspberryPiCommms {
	private static NetworkTableInstance inst = NetworkTableInstance.getDefault();
	private static NetworkTable table = inst.getTable("Vision");
	
	public double getX() {
		return (double) table.getEntry("x").getNumber(0);
	}
	

}
