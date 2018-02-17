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
import org.usfirst.frc.team6829.robot.subsystems.Dumper;
import org.usfirst.frc.team6829.robot.subsystems.Intake;
import org.usfirst.frc.team6829.robot.subsystems.Shooter;
import org.usfirst.frc.team6829.robot.commands.PathFollower;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team6829.common.DriveTrain;
import team6829.common.LoggerParameters;
import team6829.common.Logger;
import team6829.common.transforms.ITransform;
import team6829.common.transforms.SlowTransform;
import team6829.common.transforms.SquaredInputTransform;


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
	public static Intake intake;
	public static Shooter shooter;

	public static ITransform arcadeDriveTransform;
	public static ITransform slowTransform;

	public static Command arcadeDrive;
	public static Command goStraightAuton;
	
	public static Command autonCommandToRun;

	public static GameStateReader gameStateReader;

	public static Logger logger;
	public static LoggerParameters loggerParameters;

	Command m_autonomousCommand;
	SendableChooser<String> m_chooser = new SendableChooser<>();

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

		autonCommandToRun = gameStateReader.gameStateReader(autonMap());
		try {
			autonCommandToRun.start();	
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
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {

	}


	private void initializeAll() {

		driveTrain = new DriveTrain(robotMap.leftRearMotor, robotMap.leftFrontMotor, robotMap.rightRearMotor, robotMap.rightFrontMotor);
		intializePathCommands();

		gameStateReader = new GameStateReader();		
		
		dumper = new Dumper(robotMap.dumperMotor);
		
		shooter = new Shooter(robotMap.PCMID, robotMap.solenoidIDs);
		
		intake = new Intake(robotMap.PCMID, robotMap.intakeArm,
				robotMap.intakeLiftMotor,
				robotMap.intakeLeftRoller, robotMap.intakeRightRoller);
		display = new SmartdashboardOut(intake);
		oi = new OI(driveTrain, dumper, intake, shooter);
		
		
		
		
		arcadeDriveTransform = new SquaredInputTransform();
		slowTransform = new SlowTransform();


		loggerParameters = new LoggerParameters(driveTrain);
		logger = new Logger();
		
		arcadeDrive = new ArcadeDrive(driveTrain, arcadeDriveTransform,
				oi.driverJoystick, oi.AXIS_LEFT_STICK_Y, oi.AXIS_RIGHT_STICK_X, oi.BUTTON_RIGHT_BUMPER, slowTransform);
		driveTrain.setCommandDefault(arcadeDrive);
	}



	public static Map<String, Command> autonMap() {

		Map<String, Command> autonCommands = new HashMap<String, Command>();

		autonCommands.put("Go Straight", goStraightAuton);

		return autonCommands;

	}

	private File R_GoStraightAuton;
	private File L_GoStraightAuton;	

	//Import all of our trajectories from the RoboRIO
	private void importTrajectories() throws FileNotFoundException {

		R_GoStraightAuton = new File("/home/lvuser/GoStraight_right_detailed.csv");
		L_GoStraightAuton = new File("/home/lvuser/GoStraight_left_detailed.csv");

	}

	private void checkNavX() {
		boolean isNavXCalibrating = driveTrain.isCalibrating();
		boolean isNavXConnected = driveTrain.isConnected();

		SmartDashboard.putBoolean("Is navX Calibrating", isNavXCalibrating);
		SmartDashboard.putBoolean("is navX Connected", isNavXConnected);
	}
 
	public void intializePathCommands(){

		try {

			importTrajectories();

			//assign paths to commands here
			goStraightAuton = new PathFollower(driveTrain, L_GoStraightAuton, R_GoStraightAuton);

		} catch (FileNotFoundException e) {

			DriverStation.reportError("Could not find trajectory!!! " + e.getMessage(), true);

		}
	}

}