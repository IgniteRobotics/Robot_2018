package org.usfirst.frc.team6829.robot.commands.dumper;

import org.usfirst.frc.team6829.robot.subsystems.Dumper;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BurpDown extends Command {

	private Dumper dumper;

	private static final double POWER = .75;
	private static final double SETPOINT = 0;
	private static final double TOLERANCE = 5;


	public BurpDown(Dumper dumper) {

		this.dumper = dumper;
		requires(this.dumper);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
       	dumper.moveToEncoderSetpoint(POWER, SETPOINT, TOLERANCE);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return dumper.isAtSetpoint(SETPOINT, TOLERANCE);
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
