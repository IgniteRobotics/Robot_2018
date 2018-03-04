package org.usfirst.frc.team6829.robot.commands.dumper;

import org.usfirst.frc.team6829.robot.subsystems.Dumper;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickDumper extends Command {
	
	private Dumper dumper;
	private double maxPow = -0.5;
	

	private final int ARM_AXIS;
	private Joystick manipulatorJoystick;

    public JoystickDumper(Dumper dumper, Joystick mJoystick,  int ID) {
    	this.dumper = dumper;
    	
    	this.ARM_AXIS = ID;
		this.manipulatorJoystick = mJoystick;
		
		requires(this.dumper);
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println(dumper.getEncoderPosition());
    	dumper.setPercentOutput(maxPow * manipulatorJoystick.getRawAxis(ARM_AXIS));
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
