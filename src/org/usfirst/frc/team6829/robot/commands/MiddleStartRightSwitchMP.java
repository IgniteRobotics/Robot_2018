package org.usfirst.frc.team6829.robot.commands;

import java.util.HashMap;

import org.usfirst.frc.team6829.robot.commands.intake.RollerOutTime;
import org.usfirst.frc.team6829.robot.subsystems.IntakeFlywheel;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import team6829.motion_profiling.PathFollower;

/**
 *
 */
public class MiddleStartRightSwitchMP extends CommandGroup {

    public MiddleStartRightSwitchMP(HashMap<String, PathFollower> forwardsPathFollowers, IntakeFlywheel intakeFlywheel) {
    	
		Command move = forwardsPathFollowers.get("centerRight");

    	addSequential(move);
    	addSequential(new RollerOutTime(intakeFlywheel, 3));
    	

    	
    }
}
