package team6829.common;

import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import team6829.common.DifferentialDriveTalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTrain extends Subsystem {
	
	private DifferentialDriveTalonSRX differentialDrive;
	private Command defaultCommand;

	public DriveTrain(int leftMasterCanId, int leftFollowerCanId, int rightMasterCanId, int rightFollowerCanId) {
		TalonSRX leftMaster = new TalonSRX(leftMasterCanId);
		TalonSRX leftFollower = new TalonSRX(leftFollowerCanId);
		TalonSRX rightMaster = new TalonSRX(rightMasterCanId);
		TalonSRX rightFollower = new TalonSRX(rightFollowerCanId);
		
		leftMaster.setInverted(true);
		leftFollower.setInverted(true);
		rightMaster.setInverted(true);
		rightFollower.setInverted(true);
		
		leftFollower.set(ControlMode.Follower, leftMasterCanId);
		rightFollower.set(ControlMode.Follower, rightMasterCanId);
		
		differentialDrive = new DifferentialDriveTalonSRX(leftMaster, rightMaster);
	}
	
	public DriveTrain(DifferentialDriveTalonSRX differentialDrive) {
		this.differentialDrive = differentialDrive;
	}
	
	public void setCommandDefault(Command defaultCommand) {
		this.defaultCommand = defaultCommand;
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(this.defaultCommand);	
    }
    
    public void arcadeDrive(double throttle, double turn) {
    	differentialDrive.arcadeDrive(throttle, turn);
    }
    	
    public void stop() {
    	differentialDrive.stopMotor();
    }
}

