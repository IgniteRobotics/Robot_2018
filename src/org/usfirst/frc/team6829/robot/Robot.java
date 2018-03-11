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

import org.usfirst.frc.team6829.robot.commands.MiddleStartLeftSwitch;
import org.usfirst.frc.team6829.robot.commands.MiddleStartRightSwitch;
import org.usfirst.frc.team6829.robot.commands.ZeroIntakeWhileGo;
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
		checkNavX();
		driveTrain.zeroAngle();
		driveTrain.zeroEncoders();
		
		autonCommandToRun = gameStateReader.gameStateReader(autonMap());
		
		try {
			new ZeroIntakeWhileGo(autonCommandToRun, intake).start(); //TODO: VERIFY FUNCTIONALITY
		} catch (NullPointerException e) {
			DriverStation.reportError("No Autonomous selected: " + e.getMessage(), true);
		}
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
		
		intake = new IntakeLift(robotMap.intakeLiftMotor);
		
		intakeFlywheel = new IntakeFlywheel(robotMap.intakeLeftRoller, robotMap.intakeRightRoller);
		intakeClaw = new IntakeClaw(robotMap.PCMID, robotMap.intakeArm);
		display = new SmartdashboardOut(intake, dumper, driveTrain);
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

		autonCommands.put("MiddleStartLeftSwitch", new MiddleStartLeftSwitch(driveTrain, intake, intakeClaw, intakeFlywheel));
		autonCommands.put("MiddleStartRightSwitch", new MiddleStartRightSwitch(driveTrain, intake, intakeClaw, intakeFlywheel));
		autonCommands.put("GoStraight", new DriveToEncoderSetpoint(driveTrain, 60, 10));
		
		return autonCommands;

	}

	private File R_turnLeft;
	private File L_turnLeft;
	
	private File R_turnRight;
	private File L_turnRight;
	
	//Import all of our trajectories from the RoboRIO
	private void importTrajectories() throws FileNotFoundException {

		R_turnLeft = new File("/home/lvuser/turnLeft_right_detailed.csv");
		L_turnLeft = new File("/home/lvuser/turnLeft_left_detailed.csv");

		R_turnRight = new File("/home/lvuser/turnRight_right_detailed.csv");
		L_turnRight = new File("/home/lvuser/turnRight_left_detailed.csv");
		
	}

	private void checkNavX() {
		boolean isNavXCalibrating = driveTrain.isCalibrating();
		boolean isNavXConnected = driveTrain.isConnected();

		SmartDashboard.putBoolean("Is navX Calibrating", isNavXCalibrating);
		SmartDashboard.putBoolean("is navX Connected", isNavXConnected);
	}
 	
	private Command turnLeft;
	private Command turnRight; 
	
	private void intializePathFollowers(){

		try {

			importTrajectories();
			
			turnLeft = new PathFollower(driveTrain, L_turnLeft, R_turnLeft);
			turnRight = new PathFollower(driveTrain, L_turnRight, R_turnRight);
	
		} catch (FileNotFoundException e) {

			DriverStation.reportError("!!!!!!!!!!!!!!!!!!!!!!!!!!Could not find trajectory!!!!!!!!!!!!!!!!!!!!!!!!!! " + e.getMessage(), true);

		}
	}
	

}