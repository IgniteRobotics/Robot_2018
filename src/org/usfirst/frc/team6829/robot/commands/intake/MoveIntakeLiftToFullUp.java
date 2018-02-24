package org.usfirst.frc.team6829.robot.commands.intake;

import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoveIntakeLiftToFullUp extends CommandGroup {

	private IntakeLift intake;
	private double fullUpPosition = 0.0; // TODO: Set this
	
    public MoveIntakeLiftToFullUp(IntakeLift intake) {
        
    	this.intake = intake;
    	requires(this.intake);
    	
    	addSequential(new MoveIntakeLiftToPosition(intake, fullUpPosition));
    }
}
