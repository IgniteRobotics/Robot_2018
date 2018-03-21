package org.usfirst.frc.team6829.robot.commands;

import org.usfirst.frc.team6829.robot.Robot;
import org.usfirst.frc.team6829.robot.commands.intake.RollerOutTime;
import org.usfirst.frc.team6829.robot.commands.shooter.ShootCube;
import org.usfirst.frc.team6829.robot.subsystems.IntakeFlywheel;
import org.usfirst.frc.team6829.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import team6829.common.DriveTrain;
import team6829.motion_profiling.PathFollower;

/**
 *
 */
public class MPthenShoot extends CommandGroup {

    public MPthenShoot(DriveTrain driveTrain, IntakeFlywheel shooter) {
    	
    	addSequential(new PathFollower(driveTrain, Robot.L_1_2, Robot.R_1_2));
    	addSequential(new RollerOutTime(shooter, 4));
    	
    }
}
