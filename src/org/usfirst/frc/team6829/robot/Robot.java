/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6829.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team6829.robot.commands.LeftStartScaleShoot;
import org.usfirst.frc.team6829.robot.commands.MiddleStartLeftSwitch;
import org.usfirst.frc.team6829.robot.commands.MiddleStartLeftSwitchMP;
import org.usfirst.frc.team6829.robot.commands.MiddleStartRightSwitch;
import org.usfirst.frc.team6829.robot.commands.MiddleStartRightSwitchMP;
import org.usfirst.frc.team6829.robot.commands.RightStartScaleShoot;
import org.usfirst.frc.team6829.robot.commands.RightStartSwitchShoot;
import org.usfirst.frc.team6829.robot.commands.driveTrain.ArcadeDrive;
import org.usfirst.frc.team6829.robot.commands.driveTrain.DriveToEncoderSetpoint;
import org.usfirst.frc.team6829.robot.commands.intake.JoystickIntakeLift;
import org.usfirst.frc.team6829.robot.subsystems.Dumper;
import org.usfirst.frc.team6829.robot.subsystems.IntakeClaw;
import org.usfirst.frc.team6829.robot.subsystems.IntakeFlywheel;
import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;
import org.usfirst.frc.team6829.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team6829.common.DriveTrain;
import team6829.common.Logger;
import team6829.common.LoggerParameters;
import team6829.common.transforms.ITransform;
import team6829.common.transforms.SlowTransform;
import team6829.common.transforms.SquaredInputTransform;
import team6829.motion_profiling.PathFollower;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	public static OI oi;
	public static SmartdashboardOut display;
	public static RobotMap robotMap = new RobotMap();

	public static DriveTrain driveTrain;
	public static Dumper dumper;
	public static Shooter shooter;

	public static IntakeLift intake;
	public static IntakeFlywheel intakeFlywheel;
	public static IntakeClaw intakeClaw;

	public static ITransform arcadeDriveTransform;
	public static ITransform slowTransform;

	public static Command arcadeDrive;

	public static Command driveToEncoderSetpoint;

	public static Command joystickLift;

	public static Command autonCommandToRun;

	public static GameStateReader gameStateReader;

	public static Logger logger;
	public static LoggerParameters loggerParameters;

	@Override
	public void robotInit() {
		initializeAll();
		checkNavX();

		gameStateReader.setRobotPosition();

	}

	@Override
	public void disabledInit() {
		checkNavX();

		driveTrain.zeroEncoders();
		driveTrain.zeroAngle();

		logger.close();
	}

	@Override
	public void disabledPeriodic() {
		checkNavX();
		display.displaySmartDashboard();

		Scheduler.getInstance().run();

	}

	@Override
	public void autonomousInit() {
		System.out.println("Autonomous Init");
		checkNavX();
		driveTrain.zeroAngle();
		driveTrain.zeroEncoders();

		autonCommandToRun = gameStateReader.gameStateReader(autonMap());

		try { 
			autonCommandToRun.start();
		} catch (NullPointerException e) {
			DriverStation.reportError("No Autonomous selected: " +e.getMessage(), true);
		}

		//CommandGroup asdf = new LeftStartScaleShoot(pathFollowers, driveTrain, shooter, intake, intakeClaw);
		//asdf.start();
		//pathFollowers.get("leftScaleFixed").start();
		//pathFollowers.get("").start();
		
		System.out.println("Starting autonomous");

		logger.init(loggerParameters.data_fields, loggerParameters.units_fields);

	}

	/**
	 * This function is called periodically during autonomous.
	 */

	@Override
	public void autonomousPeriodic() {

		checkNavX();

		logger.writeData(loggerParameters.returnValues());
		display.displaySmartDashboard();

		Scheduler.getInstance().run();

	}

	@Override
	public void teleopInit() {

		checkNavX();

		logger.init(loggerParameters.data_fields, loggerParameters.units_fields);

		driveTrain.defaultDirection();

		if (autonCommandToRun != null) {
			autonCommandToRun.cancel();

		}

	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {

		Scheduler.getInstance().run();

		logger.writeData(loggerParameters.returnValues());

		checkNavX();
		display.displaySmartDashboard();


	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {

	}


	private void initializeAll() {

		driveTrain = new DriveTrain(robotMap.leftRearMotor, robotMap.leftFrontMotor, robotMap.rightRearMotor, robotMap.rightFrontMotor, robotMap.pressureSensorID);
		loadTrajectories();

		gameStateReader = new GameStateReader();		

		dumper = new Dumper(robotMap.dumperMotor);

		shooter = new Shooter(robotMap.PCMID, robotMap.solenoidIDs);

		intake = new IntakeLift(robotMap.intakeLiftMotor);

		intakeFlywheel = new IntakeFlywheel(robotMap.intakeLeftRoller, robotMap.intakeRightRoller);
		intakeClaw = new IntakeClaw(robotMap.PCMID, robotMap.intakeArm);
		display = new SmartdashboardOut(intake, dumper, driveTrain);
		oi = new OI(driveTrain, dumper, intake, shooter, intakeFlywheel, intakeClaw);

		arcadeDriveTransform = new SquaredInputTransform();
		slowTransform = new SlowTransform();

		loggerParameters = new LoggerParameters(driveTrain, shooter);
		logger = new Logger();

		arcadeDrive = new ArcadeDrive(driveTrain, arcadeDriveTransform,
				oi.driverJoystick, oi.AXIS_LEFT_STICK_Y, oi.AXIS_RIGHT_STICK_X, oi.AXIS_LEFT_TRIGGER, oi.AXIS_RIGHT_TRIGGER ,slowTransform);
		driveTrain.setCommandDefault(arcadeDrive);

		joystickLift = new JoystickIntakeLift(intake, oi.manipulatorJoystick, oi.AXIS_LEFT_STICK_Y);
		intake.setCommandDefault(joystickLift);

	}


	private static HashMap<String, PathFollower> pathFollowers = new HashMap<String, PathFollower>();

	private ArrayList<String> pathNames = new ArrayList<String>();


	private void loadTrajectories() {

		pathNames.add("centerRight");
		pathNames.add("centerLeft");
		pathNames.add("rightScaleFixed");
		pathNames.add("leftScaleFixed");
//		pathNames.add("forwardTest");
//		pathNames.add("backwardTest");

		try {

			importTrajectories();

		} catch (FileNotFoundException e) {

			DriverStation.reportError("!!!!!!!!!!!!!!!!!!!!!!!!!!Could not find trajectory!!!!!!!!!!!!!!!!!!!!!!!!!! " + e.getMessage(), true);

		}

	}

	private void importTrajectories() throws FileNotFoundException {

		for (int i = 0; i < pathNames.size(); i++) {

			String pathName = pathNames.get(i);

			File rightTraj = new File("/home/lvuser/" + pathName + "_right_detailed.csv");
			File leftTraj = new File("/home/lvuser/" + pathName + "_left_detailed.csv");

			pathFollowers.put(pathName, new PathFollower(driveTrain, leftTraj, rightTraj));
		}
	}

	public static Map<String, Command> autonMap() {

		Map<String, Command> autonCommands = new HashMap<String, Command>();

		autonCommands.put("MiddleStartLeftSwitch", new MiddleStartLeftSwitchMP(pathFollowers, intakeFlywheel, intake));
		autonCommands.put("MiddleStartRightSwitch", new MiddleStartRightSwitchMP(pathFollowers, intakeFlywheel, intake));
		autonCommands.put("GoStraight", new DriveToEncoderSetpoint(driveTrain, 100, -.5, 10));
		autonCommands.put("RightStartScaleShoot", new RightStartScaleShoot(pathFollowers, driveTrain, shooter, intake, intakeClaw));
		autonCommands.put("LeftStartScaleShoot", new LeftStartScaleShoot(pathFollowers, driveTrain, shooter, intake, intakeClaw));
		
		return autonCommands;

	}

	private void checkNavX() {
		boolean isNavXCalibrating = driveTrain.isCalibrating();
		boolean isNavXConnected = driveTrain.isConnected();

		SmartDashboard.putBoolean("Is navX Calibrating", isNavXCalibrating);
		SmartDashboard.putBoolean("is navX Connected", isNavXConnected);
	}


}