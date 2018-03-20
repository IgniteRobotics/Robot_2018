package org.usfirst.frc.team6829.robot.commands.intake;

import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickIntakeLift extends Command {


	
	private IntakeLift intake;
	private double maxPow = 0.4;
	

	private final int ARM_AXIS;
	private Joystick manipulatorJoystick;
	
    public JoystickIntakeLift(IntakeLift intake, Joystick mJoystick,  int armID) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		
		this.ARM_AXIS = armID;
		this.manipulatorJoystick = mJoystick;
		
		this.intake = intake;
		requires(this.intake);
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	intake.setLiftPower(-maxPow * manipulatorJoystick.getRawAxis(ARM_AXIS));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.stopLift();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
