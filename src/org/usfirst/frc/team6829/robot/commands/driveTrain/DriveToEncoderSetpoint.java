package org.usfirst.frc.team6829.robot.commands.driveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
	
    public DriveToEncoderSetpoint(DriveTrain driveTrain, double encoderSetpoint, double tolerance, double power, double timeout) {

    	this.driveTrain = driveTrain;
    	this.encoderSetpoint = encoderSetpoint;
    	this.tolerance = tolerance;
    	this.power = power;
    	
    	setTimeout(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	driveTrain.zeroEncoders();
    	SmartDashboard.putNumber("Encoder target", encoderSetpoint);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	driveTrain.setLeftRightDrivePower(power, power);
    	double right = Math.abs( driveTrain.getRightEncoderPosition());
    	double left = Math.abs(driveTrain.getLeftEncoderPosition());
    	currentPosition = (left + right) / 2;
    	SmartDashboard.putNumber("Encoder value", currentPosition);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
        return (/*Math.abs(currentPosition) >= encoderSetpoint ||*/ isTimedOut() );
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
