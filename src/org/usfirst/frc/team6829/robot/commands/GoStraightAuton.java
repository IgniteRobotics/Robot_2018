package org.usfirst.frc.team6829.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import team6829.motion_profiling.TrajectoryController;
import team6829.motion_profiling.trajectories.GoStraightPath;

/**
 *
 */
public class GoStraightAuton extends Command {

	EncoderFollower left;
	EncoderFollower right;

	public GoStraightAuton() {

	}

	protected void initialize() {

		TankModifier trajectory = TrajectoryController.generateTankTrajectory(GoStraightPath.points, TrajectoryController.defaultConfig);
		TrajectoryController.configureFollow(left, right, trajectory);
	}

	protected void execute() {
		
		TrajectoryController.followTrajectory(left, right);
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
