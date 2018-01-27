/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6829.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	// Set hardware IDs
	
	public int leftFrontMotor = 1;
	public int leftRearMotor = 2;	
	public int rightFrontMotor = 4;
	public int rightRearMotor = 5;
	
	public static TalonSRX dumperMotor;

	// TODO add intake motors 
	
	public static void init() {
		
		dumperMotor = new TalonSRX(6);              // TODO Check if proper motor
	}
		
}
