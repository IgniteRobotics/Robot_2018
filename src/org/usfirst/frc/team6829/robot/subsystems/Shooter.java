package org.usfirst.frc.team6829.robot.subsystems;

import org.usfirst.frc.team6829.robot.commands.ShootCube;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ShootCube());
    }
    
    //TODO Complete Shooter subsystem
    
    
    public void stop() {
    	//TODO Complete stop method
    }
}

