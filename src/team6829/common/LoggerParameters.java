package team6829.common;

public class LoggerParameters {

	private DriveTrain driveTrain;

	public LoggerParameters(DriveTrain driveTrain) {

		this.driveTrain = driveTrain;

	}

	public String[] data_fields = 

		{
			"left enc vel", "right enc vel", "left enc pos", "right enc pos", "left enc volt", "right enc volt", "heading"
		};


	public String[] units_fields = 

		{
			"ticks/decisecond", "ticks/decisecond", "ticks", "ticks", "volts", "volts", "degrees"
		};

	public double boolToDouble(boolean val) {
		
		if (val) {
			return 1.0;
		}
		return 0.0;
	}

	public double[] returnValues() {

		double leftEnc = (double) driveTrain.getLeftEncoderPosition();
		double rightEnc = (double) driveTrain.getRightEncoderPosition();
		double leftVel = (double) driveTrain.getLeftEncoderVelocity();
		double rightVel = (double) driveTrain.getRightEncoderVelocity();
		double leftVoltage = (double)driveTrain.getLeftVoltage();
		double rightVoltage = (double)driveTrain.getRightVoltage();
		double heading = driveTrain.getAngle();


		double[] values = {leftVel, rightVel, leftEnc, rightEnc, leftVoltage, rightVoltage, heading};

		return values;

	}
}
