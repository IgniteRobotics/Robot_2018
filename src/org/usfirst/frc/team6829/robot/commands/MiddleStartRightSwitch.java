package org.usfirst.frc.team6829.robot.commands;

import org.usfirst.frc.team6829.robot.commands.driveTrain.DriveToEncoderSetpoint;
import org.usfirst.frc.team6829.robot.commands.driveTrain.TurnToAngle;

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
	
	
    public MiddleStartRightSwitch(DriveTrain driveTrain) {
    	
    	addSequential(new DriveToEncoderSetpoint(driveTrain, distanceOne, 5));
    	addSequential(new TurnToAngle(driveTrain, turnOne));
    	addSequential(new DriveToEncoderSetpoint(driveTrain, distanceTwo, 5));
    	addSequential(new TurnToAngle(driveTrain, turnTwo));
    	addSequential(new DriveToEncoderSetpoint(driveTrain, distanceThree, 5));

    	
    }
}
