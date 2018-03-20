package org.usfirst.frc.team6829.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeFlywheel extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private Command defaultCommand;
	
	private WPI_VictorSPX leftRoller;
	private WPI_VictorSPX rightRoller;

	private double flywheelSpeed = 1.0;
	
	
	public IntakeFlywheel(int leftRollerID, int rightRollerID) {
		
		leftRoller = new WPI_VictorSPX(leftRollerID);
		rightRoller = new WPI_VictorSPX(rightRollerID);
		
	}
	
	public void setCommandDefault(Command command) {
		this.defaultCommand = command;
		initDefaultCommand();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(this.defaultCommand);
    }
    
    public void rollIn() {
    	leftRoller.set(ControlMode.PercentOutput, -flywheelSpeed);
    	rightRoller.set(ControlMode.PercentOutput, -flywheelSpeed);
    }
    
    public void rollIn(double speed) {
    	leftRoller.set(ControlMode.PercentOutput, -speed);
    	rightRoller.set(ControlMode.PercentOutput, -speed);
    }
    
    public void rollOut() {
    	leftRoller.set(ControlMode.PercentOutput, flywheelSpeed);
    	rightRoller.set(ControlMode.PercentOutput, flywheelSpeed);
    }
    
    public void rollOut(double speed){
    	leftRoller.set(ControlMode.PercentOutput, speed);
    	rightRoller.set(ControlMode.PercentOutput, speed);
    }
    
    public void stopRollers() {
    	leftRoller.set(ControlMode.PercentOutput, -.15);
    	rightRoller.set(ControlMode.PercentOutput, -.15);
    }
}

