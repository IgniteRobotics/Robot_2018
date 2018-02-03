package org.usfirst.frc.team6829.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Dumper extends Subsystem {

	private Command defaultCommand;
	private TalonSRX dumperMotor;
	
	public Dumper(int dumperMotorID) {
		dumperMotor = new TalonSRX(dumperMotorID);
	}
	
    
    public void setCommandDefault(Command defaultCommand) {
		this.defaultCommand = defaultCommand;
		initDefaultCommand();
	}

	public void initDefaultCommand() {
		setDefaultCommand(this.defaultCommand);
	}
    
    
    public void setDumperMotorPower(double power) {
    	// TODO: Double check if PercentOutput is correct
    	dumperMotor.set(ControlMode.PercentOutput,Math.abs(power));
    }
    
    public TalonSRX getDumperMotor() {
    	return dumperMotor;
    }

	public void stop() {
		dumperMotor.setNeutralMode(NeutralMode.Brake);
	}
}

