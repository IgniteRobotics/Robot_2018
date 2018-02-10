package org.usfirst.frc.team6829.robot.subsystems;

import org.usfirst.frc.team6829.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
/**
 *
 */
public class Shooter extends Subsystem {

	// Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private boolean solenoid_0Enabled;
	//public boolean solenoid_1Enabled; (intake)
	private boolean solenoid_2Enabled;
	private boolean solenoid_3Enabled;
	private boolean solenoid_4Enabled;
	private boolean solenoid_5Enabled;
	private boolean solenoid_6Enabled;
	private boolean solenoid_7Enabled;
	
	private Solenoid solenoid_0;
	//private Solenoid solenoid_1;
	private Solenoid solenoid_2;
	private Solenoid solenoid_3;
	private Solenoid solenoid_4;
	private Solenoid solenoid_5;
	private Solenoid solenoid_6;
	private Solenoid solenoid_7;
	
	private Command defaultCommand;

	public Shooter(int pdpID, int[] solenoidIDs) {
		
		solenoid_0 = new Solenoid(pdpID, solenoidIDs[0]);
		//solenoid_1 = new Solenoid(actuatorID, RobotMap.solenoid_1);
		solenoid_2 = new Solenoid(pdpID, solenoidIDs[2]);
		solenoid_3 = new Solenoid(pdpID, solenoidIDs[3]);
		solenoid_4 = new Solenoid(pdpID, solenoidIDs[4]);
		solenoid_5 = new Solenoid(pdpID, solenoidIDs[5]);
		solenoid_6 = new Solenoid(pdpID, solenoidIDs[6]);
		solenoid_7 = new Solenoid(pdpID, solenoidIDs[7]);
		
		solenoid_0Enabled = solenoid_0.get();
		//solenoid_1Enabled = solenoid_1.get();
		solenoid_2Enabled = solenoid_2.get();
		solenoid_3Enabled = solenoid_3.get();
		solenoid_4Enabled = solenoid_4.get();
		solenoid_5Enabled = solenoid_5.get();
		solenoid_6Enabled = solenoid_6.get();
		solenoid_7Enabled = solenoid_7.get();
	}
	
	public void setCommandDefault(Command defaultCommand) {
		this.defaultCommand = defaultCommand;
		initDefaultCommand();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(this.defaultCommand);
    }
    
    //TODO Complete Shooter subsystem
    public void shoot() {
    	if(solenoid_0Enabled) solenoid_0.set(false);
    	if(solenoid_4Enabled) solenoid_4.set(false);
    	if(solenoid_7Enabled) solenoid_7.set(false);
    	
    	solenoid_2.set(true);
    	solenoid_3.set(true);
    	solenoid_5.set(true);
    	solenoid_6.set(true);
    }
    
    public void retract() {
    	if(solenoid_2Enabled) solenoid_2.set(false);
    	if(solenoid_3Enabled) solenoid_3.set(false);
    	if(solenoid_5Enabled) solenoid_5.set(false);
    	if(solenoid_6Enabled) solenoid_6.set(false);
    	
    	solenoid_0.set(true);
    	solenoid_4.set(true);
    	solenoid_7.set(true);
    }
    
    
    public void stop() {
    	solenoid_0.set(false);
    	//solenoid_1.set(false);
    	solenoid_2.set(false);
    	solenoid_3.set(false);
    	solenoid_4.set(false);
    	solenoid_5.set(false);
    	solenoid_6.set(false);
    	solenoid_7.set(false);
    }
}

