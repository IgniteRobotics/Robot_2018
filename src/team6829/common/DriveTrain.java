package team6829.common;

import edu.wpi.first.wpilibj.command.Subsystem;
import team6829.common.transforms.ITransform;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTrain extends Subsystem {

	private Command defaultCommand;

	private WPI_TalonSRX leftMaster;
	private WPI_TalonSRX leftFollower;
	private WPI_TalonSRX rightMaster;
	private WPI_TalonSRX rightFollower;
	private AHRS navX;
	
	public DriveTrain(int leftMasterCanId, int leftFollowerCanId, int rightMasterCanId, int rightFollowerCanId) {

		leftMaster = new WPI_TalonSRX(leftMasterCanId);
		leftFollower = new WPI_TalonSRX(leftFollowerCanId);
		rightMaster = new WPI_TalonSRX(rightMasterCanId);
		rightFollower = new WPI_TalonSRX(rightFollowerCanId);

		leftMaster.setInverted(true);
		leftFollower.setInverted(true);
		rightMaster.setInverted(false);
		rightFollower.setInverted(false);
		
		leftFollower.set(ControlMode.Follower, leftMasterCanId);
		rightFollower.set(ControlMode.Follower, rightMasterCanId);
		
		leftMaster.setSensorPhase(false);
		rightMaster.setSensorPhase(true);

		leftMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 20, 10);
		rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 20, 10);
		
		navX = new AHRS(SPI.Port.kMXP);

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
		throttlePower = applyDeadband(throttlePower, deadband); // set throttle deadband here

		turnPower = limit(turnPower);
		turnPower = applyDeadband(turnPower, deadband); // set rotate deadband here

		leftMaster.set(ControlMode.PercentOutput, leftMotorOutput);
		rightMaster.set(ControlMode.PercentOutput, rightMotorOutput);

	}

	public void setLeftDrivePower(double power) {
		leftMaster.set(ControlMode.PercentOutput, limit(power));
	}

	public void setRightDrivePower(double power) {
		rightMaster.set(ControlMode.PercentOutput, limit(power));
	}
	
	public int getLeftEncoderPosition() {
		return leftMaster.getSensorCollection().getQuadraturePosition();
	}

	public int getRightEncoderPosition() {
		return rightMaster.getSensorCollection().getQuadraturePosition();
	}
	
	public int getLeftEncoderVelocity() {
		return leftMaster.getSensorCollection().getQuadratureVelocity();
	}

	public int getRightEncoderVelocity() {
		return rightMaster.getSensorCollection().getQuadratureVelocity();
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
	
	public void zeroEncoders() {
		rightMaster.getSensorCollection().setQuadraturePosition(0, 10);
		leftMaster.getSensorCollection().setQuadraturePosition(0, 10);

	}
	
	public double getAngle() {
		return navX.getAngle();
	}
	
	public void zeroAngle() {
		navX.zeroYaw();
	}
	
	public boolean isCalibrating() {
		return navX.isCalibrating();
	}
	
	public boolean isConnected() {
		return navX.isConnected();
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
