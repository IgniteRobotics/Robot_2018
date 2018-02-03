/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6829.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team6829.robot.commands.driveTrain.ArcadeDrive;
import org.usfirst.frc.team6829.robot.subsystems.*;

import team6829.common.DriveTrain;
import team6829.common.transforms.ITransform;
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
	public static RobotMap robotMap = new RobotMap();
	
	public static Dumper dumper;
	public static Intake intake;
	public static Shooter shooter;
	
	public static DriveTrain driveTrain;
	public static ITransform arcadeDriveTransform;
	public static Command arcadeDrive;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	boolean LeftPositionAuto;
	boolean MiddlePositionAuto;
	boolean RightPositionAuto;
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//m_chooser.addDefault("Left Position Auto", null);
		//m_chooser.addObject("Right Position Auto", null);
		//SmartDashboard.putData("Auto mode", m_chooser);
			
		SmartDashboard.putBoolean("Left Position Auto", false);
		SmartDashboard.putBoolean("Middle Position Auto", false);
		SmartDashboard.putBoolean("Right Position Auto", false);
		
		initializeAll();
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		//m_autonomousCommand = m_chooser.getSelected();

		LeftPositionAuto = SmartDashboard.getBoolean("Left Position Auto", false);
		MiddlePositionAuto = SmartDashboard.getBoolean("Middle Position Auto", false);
		RightPositionAuto = SmartDashboard.getBoolean("Right Position Auto", false);
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		System.out.println(gameData);
		
		if (LeftPositionAuto) {
			System.out.println("Left Auto");  
		}
		if (MiddlePositionAuto) {
			System.out.println("Middle Auto");  
		} 
		if (RightPositionAuto) {
			System.out.println("Right Auto");  
		}
		
		if(gameData.charAt(0) == 'L') {
			System.out.println("Left");
		} else {
			System.out.println("Right");
		}
		
		// Left Switch && Positioned Left 
		if(gameData.charAt(0) == 'L' && LeftPositionAuto) {
			 	// TODO: Put left-switch auto code here
			System.out.println("Forwards (s-left)");
		}
		
		// Right Switch && Positioned Left
		if (gameData.charAt(0) == 'R' && LeftPositionAuto) { 
			 	// TODO: Put right-switch auto code here
			System.out.println("Either Turn and go forwards or Loop around switch (s-right)");
		}
		
		// Left Switch && Positioned Right
		if (gameData.charAt(0) == 'L' && RightPositionAuto) {
			 	// TODO: Put right-switch auto code here
			System.out.println("Either Turn and go forwards or Loop around switch (s-left)");
		}
		
		// Right Switch && Positioned Right
		if (gameData.charAt(0) == 'R' && RightPositionAuto) {
			// TODO: Put right-switch auto code here
			System.out.println("Forwards (s-right)");
		}

		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		/* schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		} */
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
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
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
		
		dumper = new Dumper(robotMap.dumperMotor);
		
		shooter = new Shooter(robotMap.shooterID);
		
		intake = new Intake(robotMap.intakeID);
		
		oi = new OI(driveTrain, dumper, intake, shooter);
	}
	
}
