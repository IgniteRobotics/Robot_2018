package org.usfirst.frc.team6829.robot.commands.dumper;

import org.usfirst.frc.team6829.robot.subsystems.Dumper;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveToEncoderSetpoint extends Command {

	private Dumper dumper;

	private double power;
	private double setpoint;
	private double tolerance;
	
    public MoveToEncoderSetpoint(Dumper dumper, double power, double setpoint, double tolerance) {
    	
    	this.dumper = dumper;
		this.power = power;
		this.setpoint = setpoint;
		this.tolerance = tolerance;
		
		requires(this.dumper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
		dumper.moveToEncoderSetpoint(power, setpoint, tolerance);
		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
		return dumper.isAtSetpoint(setpoint, tolerance);
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
