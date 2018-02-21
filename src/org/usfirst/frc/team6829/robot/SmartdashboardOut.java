package org.usfirst.frc.team6829.robot;

import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartdashboardOut {
	
	private IntakeLift intake;
	
	public SmartdashboardOut(IntakeLift intake) {
		this.intake = intake;
		
		SmartDashboard.putNumber("Intake Encoder", 0.0);
	}
	
	public void displaySmartDashboard() {
		SmartDashboard.putNumber("Intake Encoder", intake.getIntakePosition());
	}
}
