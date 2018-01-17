package org.usfirst.frc.team6829.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;

import org.usfirst.frc.team6829.robot.Robot;

import com.ctre.phoenix.motion.*;

import team6829.common.DriveTrain;
import team6829.common.motion_profiling.*;
import team6829.common.motion_profiling.pregen.GoStraightPath;

/**
 *
 */
public class GoStraightAuto extends Command {

	private DriveTrain driveTrain;

	TalonSRX leftMaster = Robot.driveTrain.getLeftMaster();
	TalonSRX rightMaster = Robot.driveTrain.getRightMaster();

	private MotionProfileController leftMotionProfileController = new MotionProfileController(leftMaster);
	private MotionProfileController rightMotionProfileController = new MotionProfileController(rightMaster);

	public GoStraightAuto(DriveTrain driveTrain) {

		requires(this.driveTrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
		////TODO All values need to be tuned properly

		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		leftMaster.setSensorPhase(true); // TODO need to check!
		leftMaster.configNeutralDeadband(MotionProfileController.kNeutralDeadband, MotionProfileController.kTimeoutMs);

		leftMaster.config_kF(0, 0.076, MotionProfileController.kTimeoutMs);
		leftMaster.config_kP(0, 0, MotionProfileController.kTimeoutMs);
		leftMaster.config_kI(0, 0.0, MotionProfileController.kTimeoutMs);
		leftMaster.config_kD(0, 20.0, MotionProfileController.kTimeoutMs);

		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		rightMaster.setSensorPhase(true); // TODO need to check!
		rightMaster.configNeutralDeadband(MotionProfileController.kNeutralDeadband, MotionProfileController.kTimeoutMs);

		rightMaster.config_kF(0, 0.076, MotionProfileController.kTimeoutMs);
		rightMaster.config_kP(0, 0, MotionProfileController.kTimeoutMs);
		rightMaster.config_kI(0, 0.0, MotionProfileController.kTimeoutMs);
		rightMaster.config_kD(0, 20.0, MotionProfileController.kTimeoutMs);

		leftMaster.configMotionProfileTrajectoryPeriod(10, MotionProfileController.kTimeoutMs);
		rightMaster.configMotionProfileTrajectoryPeriod(10, MotionProfileController.kTimeoutMs);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SetValueMotionProfile setOutputLeft = leftMotionProfileController.getSetValue();
		SetValueMotionProfile setOutputRight = rightMotionProfileController.getSetValue();

		leftMaster.set(ControlMode.MotionProfile, setOutputLeft.value);
		leftMaster.set(ControlMode.MotionProfile, setOutputRight.value);

		leftMotionProfileController.startMotionProfile(GoStraightPath.leftPoints, GoStraightPath.kNumPoints);
		rightMotionProfileController.startMotionProfile(GoStraightPath.leftPoints, GoStraightPath.kNumPoints);

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return MotionProfileController.isDone();
	}

	// Called once after isFinished returns true
	protected void end() {
		leftMotionProfileController.reset();
		rightMotionProfileController.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}
}
