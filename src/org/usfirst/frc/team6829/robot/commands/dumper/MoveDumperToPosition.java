package org.usfirst.frc.team6829.robot.commands.dumper;

import org.usfirst.frc.team6829.robot.subsystems.Dumper;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveDumperToPosition extends Command {

	private Dumper dumper;
	private double position;
	private double tolerance = 0.1;
	
    public MoveDumperToPosition(Dumper dumper, double pos) {
    	
    	position = pos;

    	this.dumper = dumper;
		requires(this.dumper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
		dumper.moveDumperToSetpoint(position);
		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
		return Math.abs(dumper.getEncoderPosition() - position) <= tolerance;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
		dumper.stop();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
