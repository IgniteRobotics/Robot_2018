package org.usfirst.frc.team6829.robot.commands.driveTrain;

import edu.wpi.first.wpilibj.command.Command;
import team6829.common.DriveTrain;
import team6829.common.transforms.ITransform;
import team6829.vision.RaspberryPiComms;

/**
 *
 */
public class PursueCube extends Command {

	private RaspberryPiComms vision;
	private DriveTrain driveTrain;
	private double kP = 0.3;
	private double kD = 0.0;
	
	private double throttle = 0.5;

	private double turnPower;
	private final ITransform noTransform;
	
	private double x1 = 0;
	private double x2 = 0;

	private double closeArea = 0.181165365;
	private double tolerance = 0.05;
	
    public PursueCube(DriveTrain driveTrain, RaspberryPiComms vision, ITransform noTransform) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.vision = vision;
    	this.driveTrain = driveTrain;
    	this.noTransform = noTransform;
    	requires(this.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {    	

    	x2 = vision.getRelativeX();
    	turnPower = (x2*kP) - (Math.abs(x2 - x1) * kD);
    	x1 = x2;
    	
//    	if (Math.abs(turnPower) > 0.25) {
//    		turnPower = Math.copySign(0.25, turnPower);
//    		System.out.println("turn power too low");
//    	}
    	
		driveTrain.arcadeDrive(throttle, turnPower, 0, noTransform);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(vision.getRelativeArea() - closeArea) < tolerance);
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
