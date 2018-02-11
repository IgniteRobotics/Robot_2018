package org.usfirst.frc.team6829.robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import team6829.common.DriveTrain;

//Derived from team 6502

public class DrivetrainCharacterization extends TimedCommand {

	private TestMode mode;

	private double speed; 
	private double rampRate = .005;
	private double maxSpeed = .6;

	private DriveTrain driveTrain;

	public enum TestMode {
		QUASI_STATIC,
		STEP_VOLTAGE;
	}


	public DrivetrainCharacterization(TestMode mode, double timeout, DriveTrain driveTrain) {

		super(timeout);

		this.mode = mode;
		this.driveTrain = driveTrain;

		requires(this.driveTrain);

	}

	@Override
	protected void initialize() {
		System.out.println("Chracterization starting...");
		speed = 0;
		switch (mode) {
		case QUASI_STATIC:
			break;
		case STEP_VOLTAGE:
			speed = maxSpeed;
			break;
		}
	}

	/**
	 * Called repeatedly when this Command is scheduled to run. If the characterizer is set to QUASI_STATIC mode, then
	 * the speed controller input is incremented repeatedly.
	 */
	@Override
	protected void execute() {
		driveTrain.setLeftRightDrivePower(speed, speed);

		//If rampRate causes speed to exceed max speed, stop the command. Speed never exceeds maxSpeed in
		//STEP_VOLTAGE mode because rampRate is 0. Thus, no switch needed.
		if(speed > maxSpeed) {
			System.out.println("Max speed exceeded " + timeSinceInitialized() + " seconds after initialization");
			cancel();
		}
		speed += rampRate;
	}


	/**
	 * Called when command isFinished returns true. Closes FileWriter and sets speed controllers to 0
	 */
	@Override
	protected void end() {
		//Ramp slowly back down after test to not destroy gearboxes
		for(double s = speed; s > 0; s -= 0.0003) {
			driveTrain.setLeftRightDrivePower(s, s);
		}

	}
}
