package org.usfirst.frc.team6829.robot.commands.autons;

import java.util.HashMap;

import org.usfirst.frc.team6829.robot.commands.ShootWhileMove;
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
public class LeftStartScaleShoot extends CommandGroup {

    public LeftStartScaleShoot(HashMap<String, PathFollower> backwardsPathFollowers, DriveTrain driveTrain, Shooter shooter, IntakeLift intakeLift, IntakeClaw intakeClaw) {
    	
    	Command leftScale = (Command) backwardsPathFollowers.get("leftScaleFixed");
    	
    	addSequential(leftScale);
    	addSequential(new ShootWhileMove(driveTrain, shooter, intakeLift, intakeClaw));
    }
}
