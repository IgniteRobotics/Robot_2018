package org.usfirst.frc.team6829.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeClaw extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private Command defaultCommand;
	
	private Solenoid intakeArm;
	
	public IntakeClaw(int pcmID, int intakeArmID) {
		
		intakeArm = new Solenoid(pcmID, intakeArmID);
		
	}
	
	public void setCommandDefault(Command command) {
		this.defaultCommand = command;
		initDefaultCommand();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(this.defaultCommand);
    }
    
    public void openClaw() {
    	intakeArm.set(true);
    }
    
    public void closeClaw() {
    	intakeArm.set(false);
    }
    
    public void stopClaw() {
    	intakeArm.set(false);
    }

}

