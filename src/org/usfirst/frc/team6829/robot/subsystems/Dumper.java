package org.usfirst.frc.team6829.robot.subsystems;

import org.usfirst.frc.team6829.robot.RobotMap;
import org.usfirst.frc.team6829.robot.commands.DumpCube;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Dumper extends Subsystem {

	private TalonSRX dumperMotor;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public Dumper(int dumperMotorID) {
		dumperMotor = new TalonSRX(dumperMotorID);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DumpCube());
    }
    
    public void setDumperMotorPower(double power) {
    	dumperMotor.set(ControlMode.PercentOutput,Math.abs(power));                            // TODO: Double check if PercentOutput is correct
    }
    
    public TalonSRX getDumperMotor() {
    	return dumperMotor;
    }
    
    public void stop() {
    	setDumperMotorPower(0.0);
    }
}

