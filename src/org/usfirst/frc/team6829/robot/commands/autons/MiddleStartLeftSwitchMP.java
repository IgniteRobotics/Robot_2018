package org.usfirst.frc.team6829.robot.commands.autons;

import java.util.HashMap;

import org.usfirst.frc.team6829.robot.commands.intake.MoveIntakeLiftToFullUp;
import org.usfirst.frc.team6829.robot.commands.intake.RollerOutTime;
import org.usfirst.frc.team6829.robot.subsystems.IntakeFlywheel;
import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;

import edu.wpi.first.wpilibj.command.CommandGroup;
import team6829.motion_profiling.PathFollower;

/**
 *
 */
public class MiddleStartLeftSwitchMP extends CommandGroup {

    public MiddleStartLeftSwitchMP(HashMap<String, PathFollower> forwardsPathFollowers, IntakeFlywheel intakeFlywheel, IntakeLift intakeLift) {
    		
    	addSequential(new MoveIntakeLiftToFullUp(intakeLift));
    	addSequential(forwardsPathFollowers.get("centerLeft"));
    	addSequential(new RollerOutTime(intakeFlywheel, 3));
    	

    	
    }
}
