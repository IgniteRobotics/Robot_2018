package org.usfirst.frc.team6829.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import team6829.common.*;
import team6829.common.transforms.ITransform;

/**
 *
 */
public class ArcadeDrive extends Command {

	private final int THROTTLE_AXIS;
	private final int TURN_AXIS;
	private final int SLOW_BUTTON;
	private final double DEADBAND = 0.1;
	
	private DriveTrain driveTrain;
	private Joystick driverJoystick;
	private final ITransform transform;

	public ArcadeDrive(DriveTrain driveTrain, ITransform transform, Joystick driverJoystick,  int throttleId, int turnId, int slowId) {
		this.THROTTLE_AXIS = throttleId;
		this.TURN_AXIS = turnId;
		this.SLOW_BUTTON = slowId;
		this.driveTrain = driveTrain;
		this.driverJoystick = driverJoystick;
		this.transform = transform;

		requires(this.driveTrain);

	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		double driverJoystickThrottleAxis = driverJoystick.getRawAxis(THROTTLE_AXIS);
		double driverJoystickTurnAxis = driverJoystick.getRawAxis(TURN_AXIS);
		double throttlePower = driverJoystickThrottleAxis; // no transforms yet
		double turnPower = driverJoystickTurnAxis;
	
		//only temporary
		if (driverJoystick.getRawButton(SLOW_BUTTON)) {
			throttlePower = throttlePower * 0.75;
			turnPower = turnPower * 0.75;
		}

		driveTrain.arcadeDrive(throttlePower, -turnPower, DEADBAND, transform);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		driveTrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
