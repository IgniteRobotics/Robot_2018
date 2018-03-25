package org.usfirst.frc.team6829.robot.commands.autons;

import java.util.HashMap;

import org.usfirst.frc.team6829.robot.commands.shooter.ShootRight;
import org.usfirst.frc.team6829.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import team6829.motion_profiling.PathFollower;

/**
 *
 */
public class RightStartSwitchShoot extends CommandGroup {

    public RightStartSwitchShoot(HashMap<String, PathFollower> backwardsPathFollowers, Shooter shooter) {
    	
    	Command rightSwitch = (Command) backwardsPathFollowers.get("rightShootSwitchBack");
    	
    	addSequential(rightSwitch);
    	addSequential(new ShootRight(shooter));
    }
}
