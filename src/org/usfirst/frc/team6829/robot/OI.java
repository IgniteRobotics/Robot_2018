/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6829.robot;

import org.usfirst.frc.team6829.robot.commands.dumper.BurpCube;
import org.usfirst.frc.team6829.robot.commands.dumper.DumpCube;
import org.usfirst.frc.team6829.robot.commands.intake.CloseIntake;
import org.usfirst.frc.team6829.robot.commands.intake.OpenIntake;
import org.usfirst.frc.team6829.robot.commands.intake.RollerIn;
import org.usfirst.frc.team6829.robot.commands.intake.RollerOut;
import org.usfirst.frc.team6829.robot.commands.shooter.ShootCube;
import org.usfirst.frc.team6829.robot.subsystems.Dumper;
import org.usfirst.frc.team6829.robot.subsystems.IntakeClaw;
import org.usfirst.frc.team6829.robot.subsystems.IntakeFlywheel;
import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;
import org.usfirst.frc.team6829.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import team6829.common.DriveTrain;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	private final int DRIVER_JOYSTICK = 0;
	private final int MANIPULATOR_JOYSTICK = 1;

	public final int BUTTON_A = 1;
	public final int BUTTON_B = 2;
	public final int BUTTON_X = 3;
	public final int BUTTON_Y = 4;
	public final int BUTTON_LEFT_BUMPER = 5;
	public final int BUTTON_RIGHT_BUMPER = 6;
	public final int BUTTON_BACK = 7;
	public final int BUTTON_START = 8;
	public final int BUTTON_LEFT_STICK = 9;
	public final int BUTTON_RIGHT_STICK = 10;

	public final int AXIS_LEFT_STICK_X = 0;
	public final int AXIS_LEFT_STICK_Y = 1;
	public final int AXIS_LEFT_TRIGGER = 2;
	public final int AXIS_RIGHT_TRIGGER = 3;
	public final int AXIS_RIGHT_STICK_X = 4;
	public final int AXIS_RIGHT_STICK_Y = 5;

	public Joystick driverJoystick = new Joystick(DRIVER_JOYSTICK);
	public Joystick manipulatorJoystick = new Joystick(MANIPULATOR_JOYSTICK);
	
	public Button flywheelInButton = new JoystickButton(manipulatorJoystick, BUTTON_LEFT_BUMPER);
	public Button flywheelOutButton = new JoystickButton(manipulatorJoystick, BUTTON_RIGHT_BUMPER);
	
	public Button openIntakeButton = new JoystickButton(manipulatorJoystick, BUTTON_A);
	public Button shootButton = new JoystickButton(driverJoystick, BUTTON_A);

	public Button dumpButton = new JoystickButton(manipulatorJoystick, AXIS_RIGHT_TRIGGER);
	public Button quickDumpTrigger = new JoystickButton(manipulatorJoystick, BUTTON_B);
	
//	public Button shootWhileMove = new JoystickButton(driverJoystick, BUTTON_A);
	
	public OI(DriveTrain driveTrain, Dumper dumper, IntakeLift intake, Shooter shooter, 
			IntakeFlywheel intakeFlywheel, IntakeClaw intakeClaw) {
		
		flywheelInButton.whileHeld(new RollerIn(intakeFlywheel));
		flywheelOutButton.whileHeld(new RollerOut(intakeFlywheel));
		
		openIntakeButton.whenPressed(new OpenIntake(intakeClaw));
		openIntakeButton.whenReleased(new CloseIntake(intakeClaw));

		dumpButton.whileHeld(new DumpCube(dumper));
		quickDumpTrigger.whenPressed(new BurpCube(dumper));
		
		
		shootButton.whenPressed(new ShootCube(shooter));
//		shootWhileMove.whenPressed(new ShootWhileMoving(driveTrain, shooter));
		
	}
}
