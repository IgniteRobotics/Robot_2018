package org.usfirst.frc.team6829.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Dumper extends Subsystem {

	private Command defaultCommand;
	
	private WPI_TalonSRX dumperMotor;
	
	private static final double kF  = 0;
	private static final double kP = 0;
	private static final double kI = 0;
	private static final double kD = 0;
	
	private static final int CRUISE_VELOCITY = 0;
	private static final int MAX_ACCELERATION = 0;
	
	public Dumper(int dumperMotorID) {
		
		dumperMotor = new WPI_TalonSRX(dumperMotorID);
		dumperMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 10);
		dumperMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 10);
		
		dumperMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		
		dumperMotor.configNominalOutputForward(0, 10);
		dumperMotor.configNominalOutputReverse(0, 10);
		
		dumperMotor.configPeakOutputForward(1, 10);
		dumperMotor.configPeakOutputReverse(1, 10);
		
		dumperMotor.selectProfileSlot(0, 0);
		dumperMotor.config_kF(0, kF, 10);
		dumperMotor.config_kP(0, kP, 10);
		dumperMotor.config_kI(0, kI, 10);
		dumperMotor.config_kD(0, kD, 10);
		
		dumperMotor.configMotionCruiseVelocity(CRUISE_VELOCITY, 10);
		dumperMotor.configMotionAcceleration(MAX_ACCELERATION, 10);
		
		dumperMotor.setInverted(false); //must verify
		
		dumperMotor.setSensorPhase(false); //must verify
	}
	
    
    public void setCommandDefault(Command defaultCommand) {
		this.defaultCommand = defaultCommand;
		initDefaultCommand();
	}

	public void initDefaultCommand() {
		setDefaultCommand(this.defaultCommand);
	}
    
	public void setMotionMagicTargetPosition(double position) {
		dumperMotor.set(ControlMode.MotionMagic, position);
	}
    
    public void setPercentOutput(double power) {
    	// TODO: Double check if PercentOutput is correct
    	dumperMotor.set(ControlMode.PercentOutput, power);
    }
    
    public int getEncoderPosition() {
    	return dumperMotor.getSensorCollection().getQuadraturePosition();
    }

	public void stop() {
		dumperMotor.stopMotor();
	}
}

