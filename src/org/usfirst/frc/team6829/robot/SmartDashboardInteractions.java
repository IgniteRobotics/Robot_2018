package org.usfirst.frc.team6829.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team6829.common.DriveTrain;

public class SmartDashboardInteractions {
	
	DriveTrain driveTrain;
	
	public SmartDashboardInteractions(DriveTrain driveTrain) {
		
		this.driveTrain = driveTrain;

	}
	
	public void isCalibrating() {
		SmartDashboard.putBoolean("navX Calibration", driveTrain.isCalibrating());
	}
	
	public void isConnected() {
		SmartDashboard.putBoolean("navX Connection", driveTrain.isConnected());
	}

}
