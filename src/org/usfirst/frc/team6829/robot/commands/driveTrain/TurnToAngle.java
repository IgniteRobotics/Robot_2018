package org.usfirst.frc.team6829.robot.commands.driveTrain;

import edu.wpi.first.wpilibj.command.Command;
import team6829.common.DriveTrain;

/**
 *
 */
public class TurnToAngle extends Command {

	private DriveTrain driveTrain;
	private double kP = 0.00555;
	private double angle;
	private double tolerance = 2;
	
	public TurnToAngle(DriveTrain driveTrain, double angle) {

		this.angle = angle;
		
    	this.driveTrain = driveTrain;
    	requires(this.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	driveTrain.zeroAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double power = kP * (angle - driveTrain.getAngle());
    	System.out.println("Drivetrain angle:" + driveTrain.getAngle());
    	System.out.println("Angle error:" + (angle - driveTrain.getAngle()));
    	System.out.println(power);
    	System.out.println("LeftPower:" + driveTrain.getLeftPercentOutput() + "RightPower: " + driveTrain.getRightPercentOutput());
    	
    	if (power <= 0.35) {
    		power = 0.35;
    	}
    	
    	driveTrain.setLeftRightDrivePower(-power, power);
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(angle - driveTrain.getAngle()) < tolerance);
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