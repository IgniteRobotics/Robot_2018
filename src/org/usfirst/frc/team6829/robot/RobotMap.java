/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6829.robot;


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
	
	// TODO: Set actual ports and stuff
	public int dumperMotor = 6;
	public int shooterID = 0; 
	public int intakeID = 0;
		
	// TODO: Double check wiring/channels (they often change)
	// Pneumatics
	public int[] solenoidIDs = new int[] {0, 1, 2, 3, 4, 5, 6, 7};
	//public static int solenoid_1 = 1; // Intake
	
}
