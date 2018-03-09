/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6829.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team6829.common.DriveTrain;
import team6829.common.Logger;
import team6829.common.LoggerParameters;
import team6829.common.Util;
import team6829.common.transforms.ITransform;
import team6829.common.transforms.SlowTransform;
import team6829.common.transforms.SquaredInputTransform;
import team6829.motion_profiling.PathFollower;
import team6829.motion_profiling.TrajectoryController.Direction;


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
	
	public static Command RS_LS;
	
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

		Scheduler.getInstance().run();

	}

	@Override
	public void autonomousInit() {
		checkNavX();
		
//		autonCommandToRun = gameStateReader.gameStateReader(autonMap());
		
//		try {
//			autonCommandToRun.start();	
//		} catch (NullPointerException e) {
//			DriverStation.reportError("No Autonomous selected: " + e.getMessage(), true);
//		}
//		System.out.println("Starting autonomous");


		Command driveTwoFeet = new DriveToEncoderSetpoint(driveTrain, 24, 5);
		driveTwoFeet.start();
		
		logger.init(loggerParameters.data_fields, loggerParameters.units_fields);

	}
	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {

		checkNavX();

		logger.writeData(loggerParameters.returnValues());
		
		Scheduler.getInstance().run();

	}

	@Override
	public void teleopInit() {

		checkNavX();
		
//		driveTrain.defaultLeftRight();
//		driveTrain.defaultDirection();
		
		logger.init(loggerParameters.data_fields, loggerParameters.units_fields);

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

		driveTrain = new DriveTrain(robotMap.leftRearMotor, robotMap.leftFrontMotor, robotMap.rightRearMotor, robotMap.rightFrontMotor);
		intializePathFollowers();

		gameStateReader = new GameStateReader();		
		
		dumper = new Dumper(robotMap.dumperMotor);
		
		shooter = new Shooter(robotMap.PCMID, robotMap.solenoidIDs);
		
		intake = new IntakeLift(robotMap.intakeLiftMotor, robotMap.hallEffectSensorID);
		
		intakeFlywheel = new IntakeFlywheel(robotMap.intakeLeftRoller, robotMap.intakeRightRoller);
		intakeClaw = new IntakeClaw(robotMap.PCMID, robotMap.intakeArm);
		display = new SmartdashboardOut(intake, dumper);
		oi = new OI(driveTrain, dumper, intake, shooter, intakeFlywheel, intakeClaw);
		
		arcadeDriveTransform = new SquaredInputTransform();
		slowTransform = new SlowTransform();

		loggerParameters = new LoggerParameters(driveTrain);
		logger = new Logger();
		
		arcadeDrive = new ArcadeDrive(driveTrain, arcadeDriveTransform,
				oi.driverJoystick, oi.AXIS_LEFT_STICK_Y, oi.AXIS_RIGHT_STICK_X, oi.AXIS_LEFT_TRIGGER, oi.AXIS_RIGHT_TRIGGER ,slowTransform);
		driveTrain.setCommandDefault(arcadeDrive);
		
		joystickLift = new JoystickIntakeLift(intake, oi.manipulatorJoystick, oi.AXIS_LEFT_STICK_Y);
		intake.setCommandDefault(joystickLift);
		
	}



	public static Map<String, Command> autonMap() {

		Map<String, Command> autonCommands = new HashMap<String, Command>();

		autonCommands.put("RS LS", RS_LS);
		return autonCommands;

	}

	private File R_RS_LS;
	private File L_RS_LS;	
	
	//Import all of our trajectories from the RoboRIO
	private void importTrajectories() throws FileNotFoundException {
		
//		R_RS_LS = new File("/home/lvuser/RS-LS_right_detailed.csv");
//		L_RS_LS = new File("/home/lvuser/RS-LS_left_detailed.csv");

		R_RS_LS = new File("/home/lvuser/asdf_right_detailed.csv");
		L_RS_LS = new File("/home/lvuser/asdf_left_detailed.csv");

		
	}

	private void checkNavX() {
		boolean isNavXCalibrating = driveTrain.isCalibrating();
		boolean isNavXConnected = driveTrain.isConnected();

		SmartDashboard.putBoolean("Is navX Calibrating", isNavXCalibrating);
		SmartDashboard.putBoolean("is navX Connected", isNavXConnected);
	}
 
	
	public void intializePathFollowers(){

		try {

			importTrajectories();

			//assign paths to commands here
			RS_LS = new PathFollower(driveTrain, L_RS_LS, R_RS_LS, Direction.BACKWARDS);
			
		} catch (FileNotFoundException e) {

			DriverStation.reportError("!!!!!!!!!!!!!!!!!!!!!!!!!!Could not find trajectory!!!!!!!!!!!!!!!!!!!!!!!!!! " + e.getMessage(), true);

		}
	}
	

}