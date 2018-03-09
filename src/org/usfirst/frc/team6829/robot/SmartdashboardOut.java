package org.usfirst.frc.team6829.robot;

import org.usfirst.frc.team6829.robot.subsystems.Dumper;
import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartdashboardOut {
	
	private IntakeLift intake;
	private Dumper dumper;
	
	public SmartdashboardOut(IntakeLift intake, Dumper dumper) {
		this.intake = intake;
		this.dumper = dumper;
		
		SmartDashboard.putNumber("Intake Encoder", 0.0);
		SmartDashboard.putNumber("Dumper Encoder", 0.0);
	}
	
	public void displaySmartDashboard() {
		SmartDashboard.putNumber("Intake Encoder", intake.getEncoderPosition());
		SmartDashboard.putNumber("DumperEncoder", dumper.getEncoderPosition());
	}
}
