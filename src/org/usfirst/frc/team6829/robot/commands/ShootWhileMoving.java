package org.usfirst.frc.team6829.robot.commands;

import org.usfirst.frc.team6829.robot.commands.driveTrain.DriveToEncoderSetpoint;
import org.usfirst.frc.team6829.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import team6829.common.DriveTrain;
import team6829.common.Util;

/**
 *
 */
public class ShootWhileMoving extends CommandGroup {
	
	private double encoderSetpoint = Util.inchesToNative(12.0);
	private double tolerance = 5;
	private double power = .75;
	
	private double waitTime = 0.5;

    public ShootWhileMoving(DriveTrain driveTrain, Shooter shooter) {
    	
    	addParallel(new DriveToEncoderSetpoint(driveTrain, encoderSetpoint, tolerance, power, 10));
    	addSequential(new WaitThenShoot(shooter, waitTime));
    	
    }
}
