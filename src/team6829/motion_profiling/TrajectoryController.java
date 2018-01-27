package team6829.motion_profiling;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import team6829.common.DriveTrain;

public class TrajectoryController {
	
	private DriveTrain driveTrain;

	private static final double WHEELBASE_WIDTH = 10; // need to verify
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

	private static final double TIME_STEP = 0.02;
	private static final double MAXIMUM_VELOCITY = 12; //placeholder
	private static final double MAXIMUM_ACCELERATION = 3; //placeholder
	private static final double MAXIMUM_JERK = 60; //placeholder
	
	private EncoderFollower left;
	private EncoderFollower right;
	
	public TrajectoryController (DriveTrain driveTrain) {
		
		this.driveTrain = driveTrain;
		
	}

	public static Trajectory.Config defaultConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
			Trajectory.Config.SAMPLES_HIGH, TIME_STEP, MAXIMUM_VELOCITY, MAXIMUM_ACCELERATION, MAXIMUM_JERK);

	public TankModifier generateTankTrajectory(Waypoint[] waypoints, Trajectory.Config config) {

		Trajectory t = Pathfinder.generate(waypoints, config);
		
		for (int i = 0; i < t.length(); i++) {
		    Trajectory.Segment seg = t.get(i);
		    
		    System.out.printf("%f,%f,%f,%f,%f,%f,%f,%f\n", 
		        seg.dt, seg.x, seg.y, seg.position, seg.velocity, 
		            seg.acceleration, seg.jerk, seg.heading);
		}

		TankModifier modifier = new TankModifier(t);
		modifier.modify(WHEELBASE_WIDTH);

		return modifier;

	}

	public void configureFollow(TankModifier trajectory) {

		left = new EncoderFollower(trajectory.getLeftTrajectory());
		right = new EncoderFollower(trajectory.getRightTrajectory());

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
