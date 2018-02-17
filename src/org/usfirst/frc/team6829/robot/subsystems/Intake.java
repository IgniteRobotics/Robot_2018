package org.usfirst.frc.team6829.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private Command defaultCommand;
	
	private DoubleSolenoid leftArm;
	private DoubleSolenoid rightArm;
	
	private WPI_TalonSRX intakeLift;
	
	private WPI_VictorSPX leftRoller;
	private WPI_VictorSPX rightRoller;

	private static final double kF  = 0;
	private static final double kP = 0;
	private static final double kI = 0;
	private static final double kD = 0;
	
	private static final int CRUISE_VELOCITY = 0;
	private static final int MAX_ACCELERATION = 0;
	
	private double flywheelSpeed = 1.0;
	
	private double defaultPosition = 0.0; // TODO: PLEASE SET THIS
	
	public Intake(int pdpID, int leftArmForward, int leftArmReverse, int rightArmForward, int rightArmReverse, int intakeLiftID,
			int leftRollerID, int rightRollerID) {
		
		leftArm = new DoubleSolenoid(pdpID, leftArmForward, leftArmReverse);
		rightArm = new DoubleSolenoid(pdpID, rightArmForward, rightArmReverse);
		
		intakeLift = new WPI_TalonSRX(intakeLiftID);
		leftRoller = new WPI_VictorSPX(leftRollerID);
		rightRoller = new WPI_VictorSPX(rightRollerID);
	
		intakeLift.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 10);
		intakeLift.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 10);
		
		intakeLift.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);

		intakeLift.configNominalOutputForward(0, 10);
		intakeLift.configNominalOutputReverse(0, 10);
		
		intakeLift.configPeakOutputForward(1, 10);
		intakeLift.configPeakOutputReverse(1, 10);
		
		intakeLift.selectProfileSlot(0, 0);
		intakeLift.config_kF(0, kF, 10);
		intakeLift.config_kP(0, kP, 10);
		intakeLift.config_kI(0, kI, 10);
		intakeLift.config_kD(0, kD, 10);
		
		intakeLift.configMotionCruiseVelocity(CRUISE_VELOCITY, 10);
		intakeLift.configMotionAcceleration(MAX_ACCELERATION, 10);
		
		intakeLift.setInverted(false); //must verify
		
		intakeLift.setSensorPhase(false); //must verify
		
	}
	
	public void setCommandDefault(Command command) {
		this.defaultCommand = command;
		initDefaultCommand();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(this.defaultCommand);
    }
    
    public void openClaw() {
    	leftArm.set(DoubleSolenoid.Value.kForward);
    	rightArm.set(DoubleSolenoid.Value.kForward);
    }
    
    public void closeClaw() {
    	leftArm.set(DoubleSolenoid.Value.kReverse);
    	rightArm.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void rollIn() {
    	leftRoller.set(ControlMode.PercentOutput, flywheelSpeed);
    	rightRoller.set(ControlMode.PercentOutput, -flywheelSpeed);
    }
    
    public void rollOut() {
    	leftRoller.set(ControlMode.PercentOutput, -flywheelSpeed);
    	rightRoller.set(ControlMode.PercentOutput, flywheelSpeed);
    }
    
    public void stopRollers() {
    	leftRoller.stopMotor();
    	rightRoller.stopMotor();
    }
    
    public void stopClaw() {
    	leftArm.set(DoubleSolenoid.Value.kOff);
    	rightArm.set(DoubleSolenoid.Value.kOff);
    }
    
    public void moveIntakeLiftToSetpoint(double position) {
    	intakeLift.set(ControlMode.MotionMagic, position);
    }
    
    public double getIntakeLiftPosition() {
    	return intakeLift.getActiveTrajectoryPosition();
    }
    
    public void resetLift() {
    	intakeLift.set(ControlMode.MotionMagic, defaultPosition);
    }
    
    public void stopAll() {
    	stopRollers();
    	stopClaw();
    	resetLift();
    }
}

