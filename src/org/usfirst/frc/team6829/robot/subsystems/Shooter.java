package org.usfirst.frc.team6829.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 *
 */
public class Shooter extends Subsystem {

	// Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private boolean solenoid_0Enabled;
	private boolean solenoid_1Enabled;
	private boolean solenoid_2Enabled;
	private boolean solenoid_3Enabled;
	
	private Solenoid solenoid_0;
	private Solenoid solenoid_1;
	private Solenoid solenoid_2;
	private Solenoid solenoid_3;
	
	private Command defaultCommand;

	public Shooter(int pdpID, int[] solenoidIDs) {
		solenoid_0 = new Solenoid(pdpID, solenoidIDs[0]);
		solenoid_1 = new Solenoid(pdpID, solenoidIDs[1]);
		solenoid_2 = new Solenoid(pdpID, solenoidIDs[2]);
		solenoid_3 = new Solenoid(pdpID, solenoidIDs[3]);
		pollSolenoids();
		
	}
	
	public void setCommandDefault(Command defaultCommand) {
		this.defaultCommand = defaultCommand;
		initDefaultCommand();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(this.defaultCommand);
    }
    
    public void pollSolenoids() {
		solenoid_0Enabled = solenoid_0.get();
		solenoid_1Enabled = solenoid_1.get();
		solenoid_2Enabled = solenoid_2.get();
		solenoid_3Enabled = solenoid_3.get();
    }
    
    public boolean isRetracted() {
    	pollSolenoids();
    	
    	if (solenoid_0Enabled) {
    		return false;
    	}
    	if (solenoid_1Enabled) {
    		return false;
    	}
    	if (solenoid_2Enabled) {
    		return false;
    	}
    	if (solenoid_3Enabled) {
    		return false;
    	}
    	
    	return true;
    }
    
    public boolean isLeftRetracted() {
    	if (solenoid_0Enabled) {
    		return false;
    	}
    	if (solenoid_1Enabled) {
    		return false;
    	}
    	return true;

    }
    
    public boolean isRightRetracted() {
    	if (solenoid_2Enabled) {
    		return false;
    	}
    	if (solenoid_3Enabled) {
    		return false;
    	}
    	return true;

    }
    
    public void shoot() {
    	solenoid_0.set(true);
    	solenoid_1.set(true);
    	solenoid_2.set(true);
    	solenoid_3.set(true);
    }
    
    public void shootLeft() {
    	solenoid_0.set(true);
    	solenoid_3.set(true);
    }
    
    public void shootRight() {
    	solenoid_1.set(true);
    	solenoid_2.set(true);
    }
    
    
    public void retract() {
    	solenoid_0.set(false);
    	solenoid_1.set(false);
    	solenoid_2.set(false);
    	solenoid_3.set(false);
    }
    
    public void stop() {
    	retract();
    }
}

