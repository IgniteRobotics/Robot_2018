package org.usfirst.frc.team6829.robot.commands.intake;

import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoveIntakeLiftToLaunchPos extends CommandGroup {

	private IntakeLift intake;
	private double launchPosition = 0.0; // TODO: set parameter (45 degree configuration)
	
    public MoveIntakeLiftToLaunchPos(IntakeLift intake) {
        
    	this.intake = intake;
    	requires(this.intake);
    	
    	addSequential(new MoveIntakeLiftToPosition(intake, launchPosition));
    }
}
