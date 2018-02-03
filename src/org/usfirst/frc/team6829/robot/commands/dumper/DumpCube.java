package org.usfirst.frc.team6829.robot.commands.dumper;

import org.usfirst.frc.team6829.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team6829.robot.subsystems.*;

/**
 *
 */
public class DumpCube extends Command {
	// TODO When actually testing, check to see if motor spins the proper way 
	public static final double POWER = 0.1; // 10% for safety testing
	private Dumper dumper;
	
	
    public DumpCube(Dumper dumper) {
        // Use requires() here to declare subsystem dependencies
    	this.dumper = dumper;
        requires(this.dumper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// Simple executable code for the dumper
    	dumper.setDumperMotorPower(POWER);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.dumper.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
