package team6829.common;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import team6829.common.transforms.ITransform;

//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTrain extends Subsystem {

	private Command defaultCommand;

	private TalonSRX leftMaster;
	private TalonSRX leftFollower;
	private TalonSRX rightMaster;
	private TalonSRX rightFollower;

	public DriveTrain(int leftMasterCanId, int leftFollowerCanId, int rightMasterCanId, int rightFollowerCanId) {

		leftMaster = new TalonSRX(leftMasterCanId);
		leftFollower = new TalonSRX(leftFollowerCanId);
		rightMaster = new TalonSRX(rightMasterCanId);
		rightFollower = new TalonSRX(rightFollowerCanId);

		leftMaster.setInverted(true);
		leftFollower.setInverted(true);
		rightMaster.setInverted(true);
		rightFollower.setInverted(true);

		leftFollower.set(ControlMode.Follower, leftMasterCanId);
		rightFollower.set(ControlMode.Follower, rightMasterCanId);

	}

	public void setCommandDefault(Command defaultCommand) {
		this.defaultCommand = defaultCommand;
	}

	public void initDefaultCommand() {
		setDefaultCommand(this.defaultCommand);
	}

	public void arcadeDrive(double throttlePower, double turnPower, double deadband, ITransform transform) {
		double leftMotorOutput;
		double rightMotorOutput;
		
		throttlePower = transform.transform(throttlePower);
		turnPower = transform.transform(turnPower);
		
		double maxInput = Math.copySign(Math.max(Math.abs(throttlePower), Math.abs(turnPower)), throttlePower);

		if (throttlePower >= 0.0) {
			// First quadrant, else second quadrant
			if (turnPower >= 0.0) {
				leftMotorOutput = maxInput;
				rightMotorOutput = throttlePower - turnPower;
			} else {
				leftMotorOutput = throttlePower + turnPower;
				rightMotorOutput = maxInput;
			}
		} else {
			// Third quadrant, else fourth quadrant
			if (turnPower >= 0.0) {
				leftMotorOutput = throttlePower + turnPower;
				rightMotorOutput = maxInput;
			} else {
				leftMotorOutput = maxInput;
				rightMotorOutput = throttlePower - turnPower;
			}
		}
		
		throttlePower = limit(throttlePower);
		throttlePower = applyDeadband(throttlePower, deadband); //set throttle deadband here

		turnPower = limit(turnPower);
		turnPower = applyDeadband(turnPower, deadband); //set rotate deadband here

		leftMaster.set(ControlMode.PercentOutput, leftMotorOutput);
		rightMaster.set(ControlMode.PercentOutput, -rightMotorOutput);

	}

	public void setLeftDrivePower(double power) {
		leftMaster.set(ControlMode.PercentOutput, limit(power));
	}
	
	public void setRightDrivePower(double power) {
		rightMaster.set(ControlMode.PercentOutput, limit(power));
	}
	
	public void stop() {
		leftMaster.setNeutralMode(NeutralMode.Brake);
		rightMaster.setNeutralMode(NeutralMode.Brake);
	}
	
	public TalonSRX getRightMaster() {
		return rightMaster;
	}
	
	public TalonSRX getLeftMaster() {
		return leftMaster;
	}
	
	private double limit(double value) {
		if (value > 1.0) {
			return 1.0;
		}
		if (value < -1.0) {
			return -1.0;
		}
		return value;
	}

	private double applyDeadband(double value, double deadband) {
		if (Math.abs(value) > deadband) {
			if (value > 0.0) {
				return (value - deadband) / (1.0 - deadband);
			} else {
				return (value + deadband) / (1.0 - deadband);
			}
		} else {
			return 0.0;
		}
	}

}
