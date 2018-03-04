package team6829.common;

public class Util {

	private static int WHEEL_CIRCUMFERENCE = 6;
	private static int ENCODER_TICKS_PER_REV = 2048;
	
	public static double nativeToInches(double input) {
		
		double inches = (input*WHEEL_CIRCUMFERENCE*Math.PI)/ENCODER_TICKS_PER_REV; 
		
		return inches;
	}
	
	public static double inchesToNative(double input) {
		
		double ticks = (input*ENCODER_TICKS_PER_REV)/(WHEEL_CIRCUMFERENCE*Math.PI);
		
		return ticks * 4; //Talons return 4x sampling of ticks, resulting in encoder values 4x larger than normal.
	}
}
