package org.usfirst.frc.team6829.robot.commands.driveTrain;

import edu.wpi.first.wpilibj.command.Command;
import team6829.common.DriveTrain;
import team6829.common.Util;

/**
 *
 */
public class DriveToEncoderSetpoint extends Command {

	private DriveTrain driveTrain;
	private double kP = 0.0001;
	private double targetDistance;
	private double tolerance = 10;
	private double currentDistance;
	private double slowDownDistance;
	private double cutoffDistance;
	private double power = 0;
	
    public DriveToEncoderSetpoint(DriveTrain driveTrain, double distance_inches, double power, double timeout) {

    	System.out.println("drive init");
    	this.targetDistance = Util.getEncoderTicksFromInches(Math.abs(distance_inches));
    	this.slowDownDistance = Util.getEncoderTicksFromInches(distance_inches - 12);
    	this.cutoffDistance = Util.getEncoderTicksFromInches(distance_inches - 3);
        this.power = power;
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
    	currentDistance = (left + right) / 2;
    	
    	//double actualPower = kP * power;
    	//System.out.println("Drivetrain position:" + currentPosition);
    	//System.out.println("Position error:" + (encoderSetpoint - currentPosition));
    	//System.out.println(power);
    	//System.out.println("LeftPower:" + driveTrain.getLeftPercentOutput() + "RightPower: " + driveTrain.getRightPercentOutput());
    	//if (power <= 0.35) {
    	//	power = 0.35;
    	//}
    	if (currentDistance > slowDownDistance) {
    		if (power > 0 )
    			power = .15;
    		else 
    			power = -.15;
    		
    	}
    	
    	driveTrain.setLeftRightDrivePower(power, power);
 	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double distanceToTarget = targetDistance - currentDistance;
    	boolean isFinished = (currentDistance > cutoffDistance);
    	/*
    	System.out.println("------------------------------------");
    	System.out.println("Target :" + cutoffDistance);
    	System.out.println("Current :" + currentDistance);
    	System.out.println("Distance away :" + distanceToTarget);	
    	System.out.println("IsFinished :" + isFinished);
    	*/
    	
    	return isFinished || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	driveTrain.stop();
    	driveTrain.zeroEncoders();
    	System.out.println("drive end");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
