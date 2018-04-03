package org.usfirst.frc.team6829.robot.commands.intake;

import org.usfirst.frc.team6829.robot.subsystems.IntakeFlywheel;

import edu.wpi.first.wpilibj.command.Command;
import team6829.vision.RaspberryPiComms;

/**
 *
 */
public class RollerInArea extends Command {

	private IntakeFlywheel intake;
	private RaspberryPiComms vision;

	private double closeArea = 0.181165365;
	private double tolerance = 0.05;
	
    public RollerInArea(IntakeFlywheel intake, RaspberryPiComms vision) {
    	this.intake = intake;
    	this.vision = vision;
    	
    	requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	intake.rollIn();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
        return (Math.abs(vision.getRelativeArea() - closeArea) < tolerance);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
