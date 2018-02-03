package org.usfirst.frc.team6829.robot.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private Command defaultCommand;
	
	public Intake(int intakeID) {
		
	}
	
	public void setDefaultCommand(Command command) {
		this.defaultCommand = command;
		initDefaultCommand();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(this.defaultCommand);
    }
    
    //TODO Complete Intake subsystem
    
    public void openClaw() {
    	
    }
    
    public void closeClaw() {
    	
    }
    
    public void rollIn() {
    	
    }
    
    public void rollOut() {
    	
    }
    
    public void stopRollers() {
    	
    }
    
    public void stopClaw() {
    	
    }
    
    public void stopHinge() {
    	
    }
    
    public void stopAll() {
    	stopRollers();
    	stopClaw();
    	stopHinge();
    }
}

