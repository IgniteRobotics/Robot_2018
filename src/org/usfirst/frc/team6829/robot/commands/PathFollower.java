package org.usfirst.frc.team6829.robot.commands;

import java.io.File;

import edu.wpi.first.wpilibj.command.Command;
import team6829.common.DriveTrain;
import team6829.motion_profiling.TrajectoryController;

/**
 *
 */
public class PathFollower extends Command {

	private DriveTrain driveTrain;
	private TrajectoryController trajectoryController;

	public PathFollower(DriveTrain driveTrain, File csvLeft, File csvRight) {

		this.driveTrain = driveTrain;
		trajectoryController = new TrajectoryController(driveTrain, csvLeft, csvRight);

	}


	protected void initialize() {

		driveTrain.zeroEncoders();
		driveTrain.zeroAngle();
		trajectoryController.resetFollowers();
		trajectoryController.configureFollow();

	}

	protected void execute() {
		trajectoryController.followTrajectory();

//		int encoderPositionRight = driveTrain.getRightEncoderPosition();
//		int encoderPositionLeft = driveTrain.getLeftEncoderPosition();
//		double gyroHeading = driveTrain.getAngle();
//
//		System.out.println("RightEnc: " + encoderPositionRight + "LeftEnc: " + encoderPositionLeft + "heading: " + gyroHeading);

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

		end();

	}
}
