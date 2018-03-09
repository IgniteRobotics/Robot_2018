package org.usfirst.frc.team6829.robot.commands.driveTrain;

import edu.wpi.first.wpilibj.command.Command;
import team6829.common.DriveTrain;

/**
 *
 */
public class DriveToEncoderSetpoint extends Command {

	private DriveTrain driveTrain;
	private double kP = 0.01;
	private double encoderSetpoint;
	private double tolerance = 10;
	private double currentPosition;
	
    public DriveToEncoderSetpoint(DriveTrain driveTrain, double encoderSetpoint, double tolerance, double timeout) {

    	this.encoderSetpoint = encoderSetpoint;

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
    
    	driveTrain.setLeftRightDrivePower(power, power);
    	
    	if (power <= 0.1) {
    		power = 0.2;
    	}
    	    	
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
