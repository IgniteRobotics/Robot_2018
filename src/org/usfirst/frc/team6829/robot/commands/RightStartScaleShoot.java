package org.usfirst.frc.team6829.robot.commands;

import java.util.HashMap;

import org.usfirst.frc.team6829.robot.commands.shooter.ShootLeft;
import org.usfirst.frc.team6829.robot.commands.shooter.ShootRight;
import org.usfirst.frc.team6829.robot.subsystems.IntakeClaw;
import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;
import org.usfirst.frc.team6829.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import team6829.common.DriveTrain;
import team6829.motion_profiling.PathFollower;

/**
 *
 */
public class RightStartScaleShoot extends CommandGroup {

    public RightStartScaleShoot(HashMap<String, PathFollower> backwardsPathFollowers, DriveTrain driveTrain, Shooter shooter, IntakeLift intakeLift, IntakeClaw intakeClaw) {
    	
    	Command rightScale = (Command) backwardsPathFollowers.get("rightScaleFixed");
    	
    	addSequential(rightScale);
    	addSequential(new ShootWhileMove(driveTrain, shooter, intakeLift, intakeClaw));
    }
}
