/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6829.robot;

import org.usfirst.frc.team6829.robot.commands.ArcadeDrive;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import team6829.common.DriveTrain;
import team6829.common.transforms.ITransform;

import java.io.File;

import org.usfirst.frc.team6829.robot.commands.*;
import team6829.common.transforms.SquaredInputTransform;
import team6829.motion_profiling.TrajectoryController;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	public static OI oi = new OI();
	public static RobotMap robotMap = new RobotMap();
	
	public static DriveTrain driveTrain;
	public static ITransform arcadeDriveTransform;
	public static Command arcadeDrive;
	public static Command pathFollower;
	public static Command goStraightAuton;
	public static Command intake;
	public static TrajectoryController trajectoryController;
	
	private static File csvRight = new File("/home/lvuser/right_detailed.csv");
	private static File csvLeft = new File("/home/lvuser/left_detailed.csv");
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	@Override
	public void robotInit() {

		initializeAll();

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		
		driveTrain.zeroEncoders();
		driveTrain.zeroAngle();
//		trajectoryController.resetFollowers();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();

	}

	@Override
	public void autonomousInit() {
		System.out.println("Starting autonomous");
		goStraightAuton.start();
		
	}
	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (goStraightAuton != null) {
			goStraightAuton.cancel();
					
		}

	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
	}
	
	
	private void initializeAll() {

		driveTrain = new DriveTrain(robotMap.leftFrontMotor, robotMap.leftRearMotor, robotMap.rightFrontMotor, robotMap.rightRearMotor);
		arcadeDriveTransform = new SquaredInputTransform();
		arcadeDrive = new ArcadeDrive(driveTrain, arcadeDriveTransform,
				oi.driverJoystick, oi.AXIS_LEFT_STICK_Y, oi.AXIS_RIGHT_STICK_X, oi.BUTTON_RIGHT_BUMPER);
		driveTrain.setCommandDefault(arcadeDrive);
		trajectoryController = new TrajectoryController(driveTrain);
		
		if (csvLeft.exists() && csvRight.exists()) {
			
			goStraightAuton = new PathFollower(trajectoryController, driveTrain, csvLeft, csvRight);

		} else {
			
			System.out.println("CSV files don't exist!");
			
		}	
	}
}
