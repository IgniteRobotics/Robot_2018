package team6829.common;

import org.usfirst.frc.team6829.robot.subsystems.Shooter;

public class LoggerParameters {

	private DriveTrain driveTrain;
	private Shooter shooter;
	
	public LoggerParameters(DriveTrain driveTrain, Shooter shooter) {

		this.driveTrain = driveTrain;
		this.shooter = shooter;

	}

	public String[] data_fields = 

		{
			"left enc vel", "right enc vel", "left enc pos", "right enc pos","left pow", "right pow", "left enc volt", "right enc volt", "heading", "shoot?"
		};


	public String[] units_fields = 

		{
			"ticks/decisecond", "ticks/decisecond", "ticks", "ticks", "percent", "percent", "volts", "volts", "degrees", "1: yes, 0: no"
		};

	public double boolToDouble(boolean val) {
		
		if (val) {
			return 1.0;
		} else {
		return 0.0;
		}
	}

	public double[] returnValues() {

		double leftEnc = (double) driveTrain.getLeftEncoderPosition();
		double rightEnc = (double) driveTrain.getRightEncoderPosition();
		double leftVel = (double) driveTrain.getLeftEncoderVelocity();
		double rightVel = (double) driveTrain.getRightEncoderVelocity();
		double leftVoltage = (double)driveTrain.getLeftVoltage();
		double leftPower = driveTrain.getLeftPercentOutput();
		double rightPower = driveTrain.getRightPercentOutput();
		double rightVoltage = (double)driveTrain.getRightVoltage();
		double heading = driveTrain.getAngle();
		double shoot = boolToDouble(shooter.isExtended());


		double[] values = {leftVel, rightVel, leftEnc, rightEnc, leftPower, rightPower, leftVoltage, rightVoltage, heading, shoot};

		return values;

	}
}
