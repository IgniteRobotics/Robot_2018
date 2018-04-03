package team6829.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class RaspberryPiComms {
	private static NetworkTableInstance inst = NetworkTableInstance.getDefault();
	private static NetworkTable table = inst.getTable("Vision");
	
	public double getRawX() {
		return (double) table.getEntry("x").getNumber(0);
	}
	
	public double getImgWidth() {
		return (double) table.getEntry("img_width").getNumber(0);
	}
	
	public double getImgHeight() {
		return (double) table.getEntry("img_height").getNumber(0);
	}
	
	public double getRelativeX() {
		return getRawX() / (getImgWidth()/2);
	}
	
	public double getRawArea() {
		return (double) table.getEntry("area").getNumber(0);
	}
	
	public double getRelativeArea() {
		return getRawArea() / (getImgWidth() * getImgHeight());
	}
	
	

}
