package org.usfirst.frc.team6829.robot.commands.dumper;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team6829.robot.subsystems.*;

/**
 *
 */
public class DumpCube extends Command {
	
	private Dumper dumper;
	
	private static final double POWER = .75;
	private static final double SETPOINT = 1000;
	private static final double HOME_SETPOINT = 0;
	private static final double TOLERANCE = 10;

    public DumpCube(Dumper dumper) {
        // Use requires() here to declare subsystem dependencies
    	this.dumper = dumper;
        requires(this.dumper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	dumper.moveToEncoderSetpoint(POWER, SETPOINT, TOLERANCE);
    	dumper.moveToEncoderSetpoint(POWER, HOME_SETPOINT, TOLERANCE);

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
    	dumper.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
