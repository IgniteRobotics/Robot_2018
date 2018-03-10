package org.usfirst.frc.team6829.robot.commands.dumper;

import org.usfirst.frc.team6829.robot.subsystems.Dumper;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BurpCube extends CommandGroup {

	private Dumper dumper;
	
	private double home_setpoint = 0;
	private double setpoint = 0;
	
	//TODO: set all parameters
	
    public BurpCube(Dumper dumper) {
    
    	this.dumper = dumper;
    	requires(this.dumper);
    	
    	addSequential(new MoveDumperToPosition(dumper, setpoint));
    	addSequential(new MoveDumperToPosition(dumper, home_setpoint));
      
    }
}
