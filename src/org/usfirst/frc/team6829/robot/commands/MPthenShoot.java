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
    	
    	addSequential(new PathFollower(driveTrain, Robot.L_middleStartRightSwitch, Robot.R_middleStartRightSwitch));
    	addSequential(new RollerOutTime(shooter, 4));
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
