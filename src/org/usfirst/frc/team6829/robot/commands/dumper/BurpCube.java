package org.usfirst.frc.team6829.robot.commands.dumper;

import org.usfirst.frc.team6829.robot.subsystems.Dumper;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BurpCube extends CommandGroup {

	private Dumper dumper;
	
    public BurpCube(Dumper dumper) {
    
    	this.dumper = dumper;
    	requires(this.dumper);
    	
    	addSequential(new BurpUp(dumper));
    	addSequential(new BurpDown(dumper));
      
    }
}
