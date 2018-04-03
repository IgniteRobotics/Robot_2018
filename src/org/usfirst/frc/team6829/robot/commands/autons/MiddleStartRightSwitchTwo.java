package org.usfirst.frc.team6829.robot.commands.autons;

import java.util.HashMap;

import org.usfirst.frc.team6829.robot.commands.driveTrain.DriveToEncoderSetpoint;
import org.usfirst.frc.team6829.robot.commands.driveTrain.TurnToAngleYaw;
import org.usfirst.frc.team6829.robot.commands.intake.CloseIntake;
import org.usfirst.frc.team6829.robot.commands.intake.MoveIntakeLiftToFullDown;
import org.usfirst.frc.team6829.robot.commands.intake.MoveIntakeLiftToFullUp;
import org.usfirst.frc.team6829.robot.commands.intake.OpenIntake;
import org.usfirst.frc.team6829.robot.commands.intake.RollerOutTime;
import org.usfirst.frc.team6829.robot.subsystems.IntakeClaw;
import org.usfirst.frc.team6829.robot.subsystems.IntakeFlywheel;
import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;

import edu.wpi.first.wpilibj.command.CommandGroup;
import team6829.common.DriveTrain;
import team6829.motion_profiling.PathFollower;

/**
 *
 */
public class MiddleStartRightSwitchTwo extends CommandGroup {

    public MiddleStartRightSwitchTwo(HashMap<String, PathFollower> forwardsPathFollowers, IntakeFlywheel intakeFlywheel, IntakeLift intakeLift, DriveTrain driveTrain, IntakeClaw intakeClaw) {
    	 	
    	addSequential(forwardsPathFollowers.get("centerRight"));
    	addSequential(new RollerOutTime(intakeFlywheel, 3));
    	addSequential(new DriveToEncoderSetpoint(driveTrain, 24, .5, 10));
    	addSequential(new TurnToAngleYaw(driveTrain, 90));
    	addSequential(new MoveIntakeLiftToFullDown(intakeLift));
    	addSequential(new OpenIntake(intakeClaw));
    	addSequential(new DriveToEncoderSetpoint(driveTrain, 24, 0.5, 10));
    	addSequential(new CloseIntake(intakeClaw));
    	addSequential(new MoveIntakeLiftToFullUp(intakeLift));
    	addSequential(new DriveToEncoderSetpoint(driveTrain, 24, -0.5, 10));
    	addSequential(new TurnToAngleYaw(driveTrain, -90));
    	addSequential(new DriveToEncoderSetpoint(driveTrain, 24, -.5, 10));
    	addSequential(new RollerOutTime(intakeFlywheel, 3));


    	

    	
    	
    }
}
