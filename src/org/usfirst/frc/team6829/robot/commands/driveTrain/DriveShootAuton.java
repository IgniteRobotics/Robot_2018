package org.usfirst.frc.team6829.robot.commands.driveTrain;

import org.usfirst.frc.team6829.robot.commands.shooter.ShootCube;
import org.usfirst.frc.team6829.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import team6829.common.DriveTrain;
import team6829.common.Util;

/**
 *This assumes you started from the center and are going to the right switch
 */
public class DriveShootAuton extends CommandGroup {
	
    public DriveShootAuton(DriveTrain driveTrain, Shooter shooter) {
    	
    	addSequential(new DriveToEncoderSetpoint(driveTrain, Util.inchesToNative(115),
				10, .3, 3.5));
    	
    	addSequential(new ShootCube(shooter));
    
    }
}
