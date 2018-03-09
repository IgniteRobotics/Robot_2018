package org.usfirst.frc.team6829.robot.commands.driveTrain;

import edu.wpi.first.wpilibj.command.Command;
import team6829.common.DriveTrain;

/**
 *
 */
public class DriveToMotionMagicSetpoint extends Command {

	private DriveTrain driveTrain;
	private double position;
	private double tolerance = 0.1;
	
    public DriveToMotionMagicSetpoint(DriveTrain driveTrain, double pos) {
    	
    	position = pos;
    	
    	this.driveTrain = driveTrain;
    	requires(this.driveTrain);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
