package team6829.motion_profiling;

import org.usfirst.frc.team6829.robot.Robot;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class TrajectoryController {

	public static final double WHEELBASE_WIDTH = 10; //need to verify
	public static final int TICKS_PER_REVOLUTION = 4096; //need to verify
	public static final int WHEEL_DIAMETER = 4; //need to verify

	public static final double LEFT_KP = 0;
	public static final double LEFT_KI = 0; //not used
	public static final double LEFT_KD = 0;
	public static final double LEFT_KA = 0; //max accel
	public static final double LEFT_KV = 0; //cruise velocity

	public static final double RIGHT_KP = 0;
	public static final double RIGHT_KI = 0; //not used
	public static final double RIGHT_KD = 0;
	public static final double RIGHT_KA = 0; //max accel
	public static final double RIGHT_KV = 0; //cruise velocity
	
	public static Trajectory.Config defaultConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);

	public static TankModifier generateTankTrajectory(Waypoint[] waypoints, Trajectory.Config config) {

		Trajectory t = Pathfinder.generate(waypoints, config);

		TankModifier modifier = new TankModifier(t);
		modifier.modify(WHEELBASE_WIDTH);

		return modifier;

	}
	
	public static void configureFollow(EncoderFollower left, EncoderFollower right, TankModifier trajectory) {

		left = new EncoderFollower(trajectory.getLeftTrajectory());
		right = new EncoderFollower(trajectory.getRightTrajectory());

		left.configureEncoder(Robot.driveTrain.readLeftEncoderPosition(), TICKS_PER_REVOLUTION, WHEEL_DIAMETER);

		right.configureEncoder(Robot.driveTrain.readRightEncoderPosition(), TICKS_PER_REVOLUTION, WHEEL_DIAMETER);

		left.configurePIDVA(LEFT_KP, LEFT_KI, LEFT_KD, 1 / LEFT_KV, LEFT_KA);
		right.configurePIDVA(RIGHT_KP, RIGHT_KI, RIGHT_KD, 1 / RIGHT_KV, RIGHT_KA);

	}

	public static void followTrajectory(EncoderFollower left, EncoderFollower right) {

		double l = left.calculate(Robot.driveTrain.readLeftEncoderPosition());
		double r = right.calculate(Robot.driveTrain.readRightEncoderPosition());

		double currentHeading = Robot.driveTrain.getAngle();
		System.out.println(currentHeading);

		double desiredHeading = Pathfinder.r2d(left.getHeading());

		double angleError = Pathfinder.boundHalfDegrees(desiredHeading - currentHeading);
		double turn = 0.8 * (-1.0/80.0) * angleError;

		Robot.driveTrain.setLeftDrivePower(l + turn);
		Robot.driveTrain.setRightDrivePower(r - turn);

	}


}
