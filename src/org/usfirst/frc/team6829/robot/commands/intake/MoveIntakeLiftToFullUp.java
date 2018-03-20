package org.usfirst.frc.team6829.robot.commands.intake;

import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveIntakeLiftToFullUp extends Command {

	private IntakeLift intake;
	private double maxSpeed = -0.3; // TODO: PLEASE REVERSE THIS ON B
	private double timeOut = 0.5;
	
    public MoveIntakeLiftToFullUp(IntakeLift intake) {
        
    	this.intake = intake;
    	requires(this.intake);
    	setTimeout(timeOut);
    	//addSequential(new MoveIntakeLiftToPosition(intake, fullUpPosition));
    }

    protected void initialize() {
    	intake.setLiftPower(maxSpeed);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }
    
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
    	System.out.println("is arm up:" + intake.isArmUp());
		return intake.isArmUp() || isTimedOut();
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
