package org.usfirst.frc.team6829.robot.commands.intake;

import org.usfirst.frc.team6829.robot.subsystems.IntakeFlywheel;

import edu.wpi.first.wpilibj.command.CommandGroup;
import team6829.vision.RaspberryPiComms;

/**
 *
 */
public class RollerInVision extends CommandGroup {

    public RollerInVision(RaspberryPiComms vision, IntakeFlywheel intake) {
    	addSequential(new RollerInArea(intake, vision));
    	addSequential(new RollerInTime(intake, 0.5));
    	
    }
}
