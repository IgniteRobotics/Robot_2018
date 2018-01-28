package org.usfirst.frc.team6829.robot.commands;

import org.usfirst.frc.team6829.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
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
	private Waypoint[] points;

	public PathFollower(TrajectoryController trajectoryController, DriveTrain driveTrain, Waypoint[] points) {
		
		this.trajectoryController = trajectoryController;
		this.driveTrain = driveTrain;
		this.points = points;
				
	}


	protected void initialize() {
		
		driveTrain.zeroEncoders();
		driveTrain.zeroAngle();

		TankModifier trajectory = trajectoryController.generateTankTrajectory(points, TrajectoryController.defaultConfig);
		trajectoryController.configureFollow(trajectory);
	}

	protected void execute() {
		
		trajectoryController.followTrajectory();
		
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
