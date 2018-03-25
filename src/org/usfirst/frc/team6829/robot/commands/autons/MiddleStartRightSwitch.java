package org.usfirst.frc.team6829.robot.commands.autons;

import org.usfirst.frc.team6829.robot.commands.driveTrain.DriveToEncoderSetpoint;
import org.usfirst.frc.team6829.robot.commands.driveTrain.TurnToAngleYaw;
import org.usfirst.frc.team6829.robot.commands.intake.MoveIntakeLiftToFullUp;
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

	private double maxPower = -.3;
	private double distanceOne = 0;
	private double turnOne = 0;
	private double distanceTwo = 0;
	private double turnTwo = 0;
	private double distanceThree = 0;
	private double cubeEjectTime = 0;
	
	//TODO: set all parameters
	
    public MiddleStartRightSwitch(DriveTrain driveTrain, IntakeLift intakeLift, IntakeClaw intakeClaw, IntakeFlywheel intakeFlywheel) {
    	
    	addSequential(new MoveIntakeLiftToFullUp(intakeLift));
    	addSequential(new DriveToEncoderSetpoint(driveTrain, 43, maxPower, 10));
    	addSequential(new TurnToAngleYaw(driveTrain, 75));
    	addSequential(new DriveToEncoderSetpoint(driveTrain, 59-12-6-4, maxPower, 10));    
    	addSequential(new TurnToAngleYaw(driveTrain, -70));
    	addSequential(new DriveToEncoderSetpoint(driveTrain, 63+8, maxPower, 2)); 
    	addSequential(new RollerOutTime(intakeFlywheel, 3));

    	
    }
}
