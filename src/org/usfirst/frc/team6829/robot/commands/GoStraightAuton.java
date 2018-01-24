package org.usfirst.frc.team6829.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import team6829.common.DriveTrain;
import team6829.motion_profiling.TrajectoryController;
import team6829.motion_profiling.trajectories.GoStraightPath;

/**
 *
 */
public class GoStraightAuton extends Command {

	private EncoderFollower left;
	private EncoderFollower right;
	
	private TrajectoryController trajectoryController;
	
	private DriveTrain driveTrain;

	public GoStraightAuton(TrajectoryController trajectoryController, DriveTrain driveTrain) {
		
		this.trajectoryController = trajectoryController;
		this.driveTrain = driveTrain;
		
//		requires(this.driveTrain);
		
	}

	protected void initialize() {
		driveTrain.zeroEncoders();
		driveTrain.zeroAngle();

		TankModifier trajectory = trajectoryController.generateTankTrajectory(GoStraightPath.points, TrajectoryController.defaultConfig);
		trajectoryController.configureFollow(left, right, trajectory);
	}

	protected void execute() {
		
		trajectoryController.followTrajectory(left, right);
		System.out.println("Is executing");
		
	}

	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
