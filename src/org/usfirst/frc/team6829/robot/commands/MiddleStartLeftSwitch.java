package org.usfirst.frc.team6829.robot.commands;

import org.usfirst.frc.team6829.robot.commands.driveTrain.DriveToEncoderSetpoint;
import org.usfirst.frc.team6829.robot.commands.driveTrain.TurnToAngle;
import org.usfirst.frc.team6829.robot.commands.driveTrain.TurnToAngleYaw;
import org.usfirst.frc.team6829.robot.commands.intake.CloseIntake;
import org.usfirst.frc.team6829.robot.commands.intake.MoveIntakeLiftToFullUp;
import org.usfirst.frc.team6829.robot.commands.intake.MoveIntakeLiftToLaunchPos;
import org.usfirst.frc.team6829.robot.commands.intake.OpenIntake;
import org.usfirst.frc.team6829.robot.commands.intake.RollerOutTime;
import org.usfirst.frc.team6829.robot.subsystems.IntakeClaw;
import org.usfirst.frc.team6829.robot.subsystems.IntakeFlywheel;
import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;

import edu.wpi.first.wpilibj.command.CommandGroup;
import team6829.common.DriveTrain;

/**
 *
 */
public class MiddleStartLeftSwitch extends CommandGroup {

	private double maxPower = .3;
	private double distanceOne = 48;
	private double turnOne = 0;
	private double distanceTwo = 24;
	private double turnTwo = 0;
	private double distanceThree = 0;
	private double cubeEjectTime = 0;
	
	//TODO: set all parameters
	
    public MiddleStartLeftSwitch(DriveTrain driveTrain, IntakeLift intakeLift, IntakeClaw intakeClaw, IntakeFlywheel intakeFlywheel) {

    	addSequential(new MoveIntakeLiftToFullUp(intakeLift));
    	addSequential(new DriveToEncoderSetpoint(driveTrain, 43, maxPower, 10));
    	addSequential(new TurnToAngleYaw(driveTrain, -75));
    	addSequential(new DriveToEncoderSetpoint(driveTrain, 59, maxPower, 10));    // 5Ft to the side
    	addSequential(new TurnToAngleYaw(driveTrain, 75));
    	addSequential(new DriveToEncoderSetpoint(driveTrain, 63+6, maxPower, 10));  //63" + an additional 6" in case we are off due to turns.
    	addSequential(new RollerOutTime(intakeFlywheel, 3));
    	
//    	addSequential(new TurnToAngleYaw(driveTrain, 90));
//    	addSequential(new DriveToEncoderSetpoint(driveTrain, distanceOne, maxPower,  10));
//    	addSequential(new TurnToAngle(driveTrain, turnTwo));
//    	addSequential(new DriveToEncoderSetpoint(driveTrain, distanceThree, -.5, 5));
//    	addSequential(new OpenIntake(intakeClaw));
//    	addSequential(new MoveIntakeLiftToFullUp(intakeLift));
//    	addSequential(new CloseIntake(intakeClaw));
//    	addSequential(new RollerOutTime(intakeFlywheel, cubeEjectTime));


    }
}
