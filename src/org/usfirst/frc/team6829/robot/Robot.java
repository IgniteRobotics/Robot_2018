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
import team6829.common.LoggerParameters;
import team6829.common.Logger;
import team6829.common.transforms.ITransform;

import team6829.common.transforms.SquaredInputTransform;
import team6829.motion_profiling.TrajectoryLoader;


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
	public static TrajectoryLoader trajectoryLoader;
	
	public static Command goStraightAuton;

	public static Logger logger;
	public static LoggerParameters loggerParameters;


	@Override
	public void robotInit() {

		initializeAll();

	}

	@Override
	public void disabledInit() {

		driveTrain.zeroEncoders();
		driveTrain.zeroAngle();
		logger.close();
	}

	@Override
	public void disabledPeriodic() {
		
		Scheduler.getInstance().run();

	}

	@Override
	public void autonomousInit() {
		System.out.println("Starting autonomous");

		logger.init(loggerParameters.data_fields, loggerParameters.units_fields);
		
		goStraightAuton.start();

	}
	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {

		logger.writeData(loggerParameters.returnValues());
		
		Scheduler.getInstance().run();

	}

	@Override
	public void teleopInit() {
		
		logger.init(loggerParameters.data_fields, loggerParameters.units_fields);

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
		
		logger.writeData(loggerParameters.returnValues());


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
		
		loggerParameters = new LoggerParameters(driveTrain);
		logger = new Logger();

		trajectoryLoader = new TrajectoryLoader(driveTrain);
		trajectoryLoader.intializePathCommands();
	}
}
