package org.usfirst.frc.team6829.robot.commands;

import org.usfirst.frc.team6829.robot.commands.driveTrain.DriveToEncoderSetpoint;
import org.usfirst.frc.team6829.robot.commands.shooter.ShootCube;
import org.usfirst.frc.team6829.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import team6829.common.DriveTrain;

/**
 *
 */
public class ShootWhileMove extends CommandGroup {

	private double distanceToShoot = 0;
	private double distanceTotal = 0; //TODO: set all parameters
	private double currentPosition;
	
    public ShootWhileMove(DriveTrain driveTrain, Shooter shooter) {
    	
    	addParallel(new DriveToEncoderSetpoint(driveTrain, distanceTotal, 10));
    	
    	currentPosition = (driveTrain.getLeftEncoderPosition() + driveTrain.getRightEncoderPosition())/2;
    	
    	if (currentPosition == distanceToShoot) {
    		addSequential(new ShootCube(shooter));
    	}
    	
    }
}
