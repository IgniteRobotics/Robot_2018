package org.usfirst.frc.team6829.robot.commands;

import org.usfirst.frc.team6829.robot.commands.driveTrain.DriveToEncoderSetpoint;
import org.usfirst.frc.team6829.robot.commands.driveTrain.TurnToAngle;
import org.usfirst.frc.team6829.robot.commands.intake.CloseIntake;
import org.usfirst.frc.team6829.robot.commands.intake.MoveIntakeLiftToFullUp;
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
public class MiddleStartRightSwitch extends CommandGroup {

	private double distanceOne = 0;
	private double turnOne = 0;
	private double distanceTwo = 0;
	private double turnTwo = 0;
	private double distanceThree = 0;
	private double cubeEjectTime = 0;
	
	//TODO: set all parameters
	
    public MiddleStartRightSwitch(DriveTrain driveTrain, IntakeLift intakeLift, IntakeClaw intakeClaw, IntakeFlywheel intakeFlywheel) {
    	
    	addSequential(new DriveToEncoderSetpoint(driveTrain, distanceOne, -.5, 5));
    	/*addSequential(new TurnToAngle(driveTrain, turnOne));
    	addSequential(new DriveToEncoderSetpoint(driveTrain, distanceTwo, -.5, 5));
    	addSequential(new TurnToAngle(driveTrain, turnTwo));
    	addSequential(new DriveToEncoderSetpoint(driveTrain, distanceThree, -.5, 5));
    	addSequential(new OpenIntake(intakeClaw));
    	addSequential(new MoveIntakeLiftToFullUp(intakeLift));
    	addSequential(new CloseIntake(intakeClaw));
    	addSequential(new RollerOutTime(intakeFlywheel, cubeEjectTime));*/

    	
    }
}
