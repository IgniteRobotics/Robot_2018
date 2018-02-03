package org.usfirst.frc.team6829.robot.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private Command defaultCommand;

	public Shooter(int actuatorID) {
		
	}
	
	public void setCommandDefault(Command command) {
		this.defaultCommand = command;
		initDefaultCommand();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(this.defaultCommand);
    }
    
    //TODO Complete Shooter subsystem
    public void shoot() {
    	
    }
    
    public void retract() {
    	
    }
    
    
    public void stop() {
    	//TODO Complete stop method
    }
}

