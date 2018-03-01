package team6829.motion_profiling;

import java.io.File;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import team6829.common.DriveTrain;

public class TrajectoryController {

	private DriveTrain driveTrain;

	private static final int TICKS_PER_REVOLUTION = 2048; 
	private static final double WHEEL_DIAMETER = 0.5; //in feet

	private static final double LEFT_KP = 0.05;
	private static final double LEFT_KI = 0; // not used
	private static final double LEFT_KD = 0;
	private static final double LEFT_KA = 0; // acceleration gain

	private static final double RIGHT_KP = 0.05;
	private static final double RIGHT_KI = 0; // not used
	private static final double RIGHT_KD = 0;
	private static final double RIGHT_KA = 0; // acceleration gain

	private static final double MAXIMUM_VELOCITY = 10; //placeholder
	
	private Trajectory leftTrajectory ;
	private Trajectory rightTrajectory;

	private EncoderFollower left;
	private EncoderFollower right;
	
	public enum Direction {
		FORWARDS, BACKWARDS
	}
	
	private int reverse;
	
	private Direction direction;

	public TrajectoryController (DriveTrain driveTrain, File csvLeft, File csvRight, Direction direction) {

		this.driveTrain = driveTrain;
		this.direction = direction;

		leftTrajectory = Pathfinder.readFromCSV(csvLeft);
		rightTrajectory = Pathfinder.readFromCSV(csvRight);

		left = new EncoderFollower(leftTrajectory);
		right = new EncoderFollower(rightTrajectory);

	}

	public void configureFollow() {

		left.configureEncoder(driveTrain.getLeftEncoderPosition(), TICKS_PER_REVOLUTION, WHEEL_DIAMETER);

		right.configureEncoder(driveTrain.getRightEncoderPosition(), TICKS_PER_REVOLUTION, WHEEL_DIAMETER);

		left.configurePIDVA(LEFT_KP, LEFT_KI, LEFT_KD, 1 / MAXIMUM_VELOCITY, LEFT_KA);
		right.configurePIDVA(RIGHT_KP, RIGHT_KI, RIGHT_KD, 1 / MAXIMUM_VELOCITY, RIGHT_KA);

	}

	public void followTrajectory() {
				
		switch(direction) {
		case FORWARDS:
			reverse = 1;
			break;
		case BACKWARDS:
			reverse = -1;
			break;
		default:
			reverse = 0;
		}
		
		double l = left.calculate(driveTrain.getLeftEncoderPosition());
		double r = right.calculate(driveTrain.getRightEncoderPosition());

		double currentHeading = driveTrain.getAngle();

		double desiredHeading = Pathfinder.r2d(left.getHeading());

		double angleError = Pathfinder.boundHalfDegrees(desiredHeading - currentHeading);
		double turn = reverse * 0.8 * (-1.0 / 80.0) * angleError;

		driveTrain.setLeftDrivePower(reverse*(l + turn));
		driveTrain.setRightDrivePower(reverse*(r - turn));

	}

	public void resetFollowers() {

		left.reset();
		right.reset();

	}

	public boolean isTrajectoryDone() {
		return left.isFinished();
	}

}
