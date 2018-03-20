package org.usfirst.frc.team6829.robot.commands;

import org.usfirst.frc.team6829.robot.commands.driveTrain.DriveToEncoderSetpoint;
import org.usfirst.frc.team6829.robot.commands.driveTrain.TurnToAngleYaw;
import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;
import org.usfirst.frc.team6829.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import team6829.common.DriveTrain;

/**
 *
 */
public class MiddleStartLeftScale extends CommandGroup {

	private double maxPower = .3;
	
    public MiddleStartLeftScale(DriveTrain driveTrain, Shooter shooter, IntakeLift intakeLift) {
       
    	addSequential(new DriveToEncoderSetpoint(driveTrain, 43, maxPower, 10));
    	addSequential(new TurnToAngleYaw(driveTrain, -75));
    	addSequential(new DriveToEncoderSetpoint(driveTrain, 120, maxPower, 10));
    	addSequential(new TurnToAngleYaw(driveTrain, 70));
	    addSequential(new DriveToEncoderSetpoint(driveTrain, 23.5*12, maxPower, 10));

    	
    }
}
