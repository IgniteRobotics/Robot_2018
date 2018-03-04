package org.usfirst.frc.team6829.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Dumper extends Subsystem {

	private Command defaultCommand;

	private WPI_TalonSRX dumperMotor;
	
	private double lowerEncoderLimit = 0;
	private double upperEncoderLimit = -1200;

	public Dumper(int dumperMotorID) {
		dumperMotor = new WPI_TalonSRX(dumperMotorID);
	}

	public void setCommandDefault(Command defaultCommand) {
		this.defaultCommand = defaultCommand;
		initDefaultCommand();
	}

	public void initDefaultCommand() {
		setDefaultCommand(this.defaultCommand);
	}


	public void moveToEncoderSetpoint(double power, double setpoint, double tolerance) {

		double currentEncoderPosition = getEncoderPosition();
		if (currentEncoderPosition < setpoint) {
			setPercentOutput(power);
		} else if (currentEncoderPosition > setpoint){
			setPercentOutput(-power); 
		} else if (Math.abs(currentEncoderPosition-setpoint) <= tolerance) {
			stop();
		}

	}

	public void setPercentOutput(double power) {
		power = limitDumperMotion(power);
		dumperMotor.set(ControlMode.PercentOutput, power);
	}
 	
	public boolean isAtSetpoint(double setpoint, double tolerance) {
		
		double currentEncoderPosition = getEncoderPosition();

		if (Math.abs(currentEncoderPosition-setpoint) <= tolerance) {
			return true;
		} else {
			return false;
		}
	}

	public int getEncoderPosition() {
		return dumperMotor.getSensorCollection().getQuadraturePosition();
	}
	
	public void zeroEncoder() {
		dumperMotor.getSensorCollection().setQuadraturePosition(0, 10);
	}
	
	public double limitDumperMotion(double input) {
		System.out.println(input);
		if (getEncoderPosition() < upperEncoderLimit) {
			if (input > 0) {
				return 0;
			}
		} else if (getEncoderPosition() > lowerEncoderLimit) {
			if (input < 0) {
				return 0;
			}
		}
		
		return input;
	}

	public void stop() {
		dumperMotor.stopMotor();
	}
}

