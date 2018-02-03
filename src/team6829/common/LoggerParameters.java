package team6829.common;

public class LoggerParameters {

	private DriveTrain driveTrain;

	public LoggerParameters(DriveTrain driveTrain) {

		this.driveTrain = driveTrain;

	}

	public String[] data_fields = {"left enc val", "right enc val",
			"left enc vel", "right enc vel"};

	public String[] units_fields = {"ticks", "ticks",
			"ticks per decisecond", "ticks per decisecond"};

	public double[] returnValues() {

		double leftEnc = (double) driveTrain.getLeftEncoderPosition();
		double rightEnc = (double) driveTrain.getRightEncoderPosition();
		double leftVel = (double) driveTrain.getLeftEncoderVelocity();
		double rightVel = (double) driveTrain.getRightEncoderVelocity();

		double[] values = {leftEnc, rightEnc, leftVel, rightVel};
		
		return values;

	}
}
