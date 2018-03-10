package org.usfirst.frc.team6829.robot.commands.driveTrain;

import edu.wpi.first.wpilibj.command.Command;
import team6829.common.DriveTrain;
import team6829.common.Util;

/**
 *
 */
public class DriveToEncoderSetpoint extends Command {

	private DriveTrain driveTrain;
	private double kP = 0.001;
	private double encoderSetpoint;
	private double tolerance = 10;
	private double currentPosition;
	
    public DriveToEncoderSetpoint(DriveTrain driveTrain, double distance_inches, double timeout) {

    	this.encoderSetpoint = Util.getEncoderTicksFromInches(distance_inches);

    	this.driveTrain = driveTrain;
    	requires(this.driveTrain);
    	
    	setTimeout(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	driveTrain.zeroEncoders();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double right = Math.abs(driveTrain.getRightEncoderPosition());
    	double left = Math.abs(driveTrain.getLeftEncoderPosition());
    	currentPosition = (left + right) / 2;
    	
    	double power = kP * (encoderSetpoint - currentPosition);

    	System.out.println("Drivetrain position:" + currentPosition);
    	System.out.println("Position error:" + (encoderSetpoint - currentPosition));
    	System.out.println(power);
    	System.out.println("LeftPower:" + driveTrain.getLeftPercentOutput() + "RightPower: " + driveTrain.getRightPercentOutput());
    
    	if (power <= 0.2) {
    		power = 0.2; 
    	}
    	
    	driveTrain.setLeftRightDrivePower(power, -power);
    	    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (Math.abs(encoderSetpoint - currentPosition)) < tolerance;
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
