package team6829.motion_profiling;

import java.io.File;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import team6829.common.DriveTrain;

public class TrajectoryController {
	
	private DriveTrain driveTrain;

	private static final int TICKS_PER_REVOLUTION = 4096; // need to verify
	private static final int WHEEL_DIAMETER = 4; // need to verify

	private static final double LEFT_KP = 0;
	private static final double LEFT_KI = 0; // not used
	private static final double LEFT_KD = 0;
	private static final double LEFT_KA = 0; // acceleration gain

	private static final double RIGHT_KP = 0;
	private static final double RIGHT_KI = 0; // not used
	private static final double RIGHT_KD = 0;
	private static final double RIGHT_KA = 0; // acceleration gain

	private static final double MAXIMUM_VELOCITY = 12; //placeholder
		
	private EncoderFollower left;
	private EncoderFollower right;
		
	public TrajectoryController (DriveTrain driveTrain) {
		
		this.driveTrain = driveTrain;
		
	}

	public void configureFollow(File csvLeft, File csvRight) {
		
		Trajectory leftTrajectory = Pathfinder.readFromCSV(csvLeft);
		Trajectory rightTrajectory = Pathfinder.readFromCSV(csvRight);

		left = new EncoderFollower(leftTrajectory);
		right = new EncoderFollower(rightTrajectory);

		left.configureEncoder(driveTrain.readLeftEncoderPosition(), TICKS_PER_REVOLUTION, WHEEL_DIAMETER);

		right.configureEncoder(driveTrain.readRightEncoderPosition(), TICKS_PER_REVOLUTION, WHEEL_DIAMETER);

		left.configurePIDVA(LEFT_KP, LEFT_KI, LEFT_KD, 1 / MAXIMUM_VELOCITY, LEFT_KA);
		right.configurePIDVA(RIGHT_KP, RIGHT_KI, RIGHT_KD, 1 / MAXIMUM_VELOCITY, RIGHT_KA);
		

	}

	public void followTrajectory() {

		double l = left.calculate(driveTrain.readLeftEncoderPosition());
		double r = right.calculate(driveTrain.readRightEncoderPosition());

		double currentHeading = driveTrain.getAngle();
				
		double desiredHeading = Pathfinder.r2d(left.getHeading());

		double angleError = Pathfinder.boundHalfDegrees(desiredHeading - currentHeading);
		double turn = 0.8 * (-1.0 / 80.0) * angleError;

		driveTrain.setLeftDrivePower(-(l + turn));
		driveTrain.setRightDrivePower(r - turn);

	}

}
