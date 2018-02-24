package org.usfirst.frc.team6829.robot.commands.intake;

import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveIntakeLiftToPosition extends Command {

	private IntakeLift intake;
	private double position;
	private double tolerance = 0.1;
	
    public MoveIntakeLiftToPosition(IntakeLift intake, double pos) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	position = pos;
    	
    	this.intake = intake;
    	requires(this.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	intake.moveIntakeLiftToSetpoint(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(intake.getIntakeLiftPosition() - position) <= tolerance);
    }

    // Called once after isFinished returns true
    protected void end() {
    	//intake.resetLift();
    	intake.stopLift(); // TODO: Hopefully this holds
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
