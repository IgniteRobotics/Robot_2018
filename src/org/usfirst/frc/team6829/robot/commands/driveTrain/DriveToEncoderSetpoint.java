package org.usfirst.frc.team6829.robot.commands.driveTrain;

import edu.wpi.first.wpilibj.command.Command;
import team6829.common.DriveTrain;

/**
 *
 */
public class DriveToEncoderSetpoint extends Command {

	private DriveTrain driveTrain;
	
	private double encoderSetpoint;
	private double tolerance;
	private double power;
	private double currentPosition;
	
    public DriveToEncoderSetpoint(DriveTrain driveTrain, double encoderSetpoint, double tolerance, double power) {

    	this.driveTrain = driveTrain;
    	this.encoderSetpoint = encoderSetpoint;
    	this.tolerance = tolerance;
    	this.power = power;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	driveTrain.zeroEncoders();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	currentPosition = driveTrain.getLeftEncoderPosition();
    	driveTrain.driveToEncoderSetpoint(power, encoderSetpoint, tolerance);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
        return Math.abs(currentPosition-encoderSetpoint) <= tolerance;
    }

    // Called once after isFinished returns true
    protected void end() {
    	driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
