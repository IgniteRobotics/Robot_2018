package org.usfirst.frc.team6829.robot.commands;

import org.usfirst.frc.team6829.robot.commands.shooter.ShootCube;
import org.usfirst.frc.team6829.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class WaitThenShoot extends CommandGroup {
		
    public WaitThenShoot(Shooter shooter, double WAIT_TIME) {
    	    	
    	addSequential(new DoNothing(WAIT_TIME));
    	addSequential(new ShootCube(shooter));
    	
    }
}
