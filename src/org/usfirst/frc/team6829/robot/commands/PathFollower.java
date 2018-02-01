package org.usfirst.frc.team6829.robot.commands;

import java.io.File;

import org.usfirst.frc.team6829.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;
import team6829.common.DriveTrain;
import team6829.motion_profiling.TrajectoryController;

/**
 *
 */
public class PathFollower extends Command {

	private TrajectoryController trajectoryController;
	private DriveTrain driveTrain;
	private File csvLeft;
	private File csvRight;
	
	public PathFollower(TrajectoryController trajectoryController, DriveTrain driveTrain, File csvLeft, File csvRight) {
		
		this.trajectoryController = trajectoryController;
		this.driveTrain = driveTrain;
		this.csvLeft = csvLeft;
		this.csvRight = csvRight;
				
	}


	protected void initialize() {
		
		driveTrain.zeroEncoders();
		driveTrain.zeroAngle();

		trajectoryController.configureFollow(csvLeft, csvRight);
		
	}

	protected void execute() {
		trajectoryController.followTrajectory();
		
		int encoderPositionRight = driveTrain.readRightEncoderPosition();
		int encoderPositionLeft = driveTrain.readLeftEncoderPosition();
		double gyroHeading = driveTrain.getAngle();
		
		System.out.println("RightEnc: " + encoderPositionRight + "LeftEnc: " + encoderPositionLeft + "heading: " + gyroHeading);
		
		
	}

	protected boolean isFinished() {
		return trajectoryController.isTrajectoryDone();
	}

	// Called once after isFinished returns true
	protected void end() {
		
		driveTrain.stop();
		
		trajectoryController.resetFollowers();
		driveTrain.zeroEncoders();
		driveTrain.zeroAngle();
		
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
