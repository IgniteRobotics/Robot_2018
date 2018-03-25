package org.usfirst.frc.team6829.robot;

import org.usfirst.frc.team6829.robot.subsystems.Dumper;
import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team6829.common.DriveTrain;

public class SmartdashboardOut {
	
	private IntakeLift intake;
	private Dumper dumper;
	private DriveTrain driveTrain;
	
	public SmartdashboardOut(IntakeLift intake, Dumper dumper, DriveTrain driveTrain) {
		this.intake = intake;
		this.dumper = dumper;
		this.driveTrain = driveTrain;
		
		SmartDashboard.putNumber("Intake Encoder", 0.0);
		SmartDashboard.putNumber("Dumper Encoder", 0.0);
		SmartDashboard.putNumber("Left Drive Encoder", 0.0);
		SmartDashboard.putNumber("Right Drive Encoder", 0.0);
		SmartDashboard.putNumber("Angle", 0.0);
		SmartDashboard.putNumber("Yaw", 0.0);
		SmartDashboard.putNumber("Pressure", 0.0);
		SmartDashboard.putBoolean("Is navX Calibrating", driveTrain.isCalibrating());
		SmartDashboard.putBoolean("is navX Connected", driveTrain.isConnected());

	}
	
	public void displaySmartDashboard() {
				
		SmartDashboard.putNumber("Intake Encoder", intake.getEncoderPosition());
		SmartDashboard.putNumber("Dumper Encoder", dumper.getEncoderPosition());
		SmartDashboard.putNumber("Left Drive Encoder", driveTrain.getLeftEncoderPosition());
		SmartDashboard.putNumber("Right Drive Encoder", driveTrain.getRightEncoderPosition());
		SmartDashboard.putNumber("Angle", driveTrain.getAngle());
		SmartDashboard.putNumber("Yaw", driveTrain.getYaw());
		SmartDashboard.putNumber("Pressure", driveTrain.getPressure());
		SmartDashboard.putBoolean("Is navX Calibrating", driveTrain.isCalibrating());
		SmartDashboard.putBoolean("is navX Connected", driveTrain.isConnected());
	}
}
