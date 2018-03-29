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

import org.usfirst.frc.team6829.robot.commands.autons.MiddleStartLeftSwitch;
import org.usfirst.frc.team6829.robot.commands.autons.MiddleStartRightSwitch;
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
import edu.wpi.first.wpilibj.command.Scheduler;
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
		gameStateReader.setRobotPosition();

	}

	@Override
	public void disabledInit() {
		driveTrain.zeroEncoders();
		driveTrain.zeroAngle();

		logger.close();
	}

	@Override
	public void disabledPeriodic() {
		display.displaySmartDashboard();

		Scheduler.getInstance().run();

	}

	@Override
	public void autonomousInit() {
		System.out.println("Autonomous Init");
		driveTrain.zeroAngle();
		driveTrain.zeroEncoders();

		//		autonCommandToRun = gameStateReader.gameStateReader(autonMap());
		//		
		//		try { 
		//			autonCommandToRun.start();
		//		} catch (NullPointerException e) {
		//			DriverStation.reportError("No Autonomous selected: " +e.getMessage(), true);
		//		}

		System.out.println("Starting autonomous");

		forwardsPathFollowers.get("rightSwitch").start();
		//backwardsPathFollowers.get("leftSwitch").start();


		logger.init(loggerParameters.data_fields, loggerParameters.units_fields);

	}

	/**
	 * This function is called periodically during autonomous.
	 */

	@Override
	public void autonomousPeriodic() {

		logger.writeData(loggerParameters.returnValues());
		display.displaySmartDashboard();

		Scheduler.getInstance().run();

	}

	@Override
	public void teleopInit() {

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


	public static HashMap<String, PathFollower> forwardsPathFollowers = new HashMap<String, PathFollower>();	
	public static HashMap<String, PathFollower> backwardsPathFollowers = new HashMap<String, PathFollower>();

	private ArrayList<String> forwardsPathNames = new ArrayList<String>();
	private ArrayList<String> backwardsPathNames = new ArrayList<String>();


	private void loadTrajectories() {

		//forwardsPathNames.add("1-2");
		//forwardsPathNames.add("2");
		//forwardsPathNames.add("3");
		//forwardsPathNames.add("4");
		//forwardsPathNames.add("rightShootSwitch");

		forwardsPathNames.add("rightSwitch");
//		forwardsPathNames.add("leftSwitch");

		//forwardsPathNames.add("3");
		//backwardsPathNames.add("3");

		try {

			importTrajectories();

		} catch (FileNotFoundException e) {

			DriverStation.reportError("!!!!!!!!!!!!!!!!!!!!!!!!!!Could not find trajectory!!!!!!!!!!!!!!!!!!!!!!!!!! " + e.getMessage(), true);

		}

	}

	private void importTrajectories() throws FileNotFoundException {

		for (int i = 0; i < forwardsPathNames.size(); i++) {

			String pathName = forwardsPathNames.get(i);

			File rightTraj = new File("/home/lvuser/" + pathName + "_right_detailed.csv");
			File leftTraj = new File("/home/lvuser/" + pathName + "_left_detailed.csv");

			forwardsPathFollowers.put(pathName, new PathFollower(driveTrain, leftTraj, rightTraj, true));
		}

		for (int i = 0; i < backwardsPathNames.size(); i++) {

			String pathName = backwardsPathNames.get(i);

			File rightTraj = new File("/home/lvuser/" + pathName + "_right_detailed.csv");
			File leftTraj = new File("/home/lvuser/" + pathName + "_left_detailed.csv");

			backwardsPathFollowers.put(pathName, new PathFollower(driveTrain, leftTraj, rightTraj, false));
		}
	}

	public static Map<String, Command> autonMap() {

		Map<String, Command> autonCommands = new HashMap<String, Command>();

		autonCommands.put("MiddleStartLeftSwitch", new MiddleStartLeftSwitch(driveTrain, intake, intakeClaw, intakeFlywheel));
		autonCommands.put("MiddleStartRightSwitch", new MiddleStartRightSwitch(driveTrain, intake, intakeClaw, intakeFlywheel));
		autonCommands.put("GoStraight", new DriveToEncoderSetpoint(driveTrain, 100, .5, 10));

		return autonCommands;

	}

}