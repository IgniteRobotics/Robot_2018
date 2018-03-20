package org.usfirst.frc.team6829.robot.commands;

import org.usfirst.frc.team6829.robot.commands.driveTrain.DriveToEncoderSetpoint;
import org.usfirst.frc.team6829.robot.commands.shooter.ShootCube;
import org.usfirst.frc.team6829.robot.subsystems.IntakeClaw;
import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;
import org.usfirst.frc.team6829.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import team6829.common.DriveTrain;
import team6829.common.Util;

/**
 *
 */
public class ShootWhileMove extends Command {

	private double distanceToShoot = Util.getEncoderTicksFromInches(8);
	private double distanceTotal = Util.getEncoderTicksFromInches(30); //TODO: set all parameters
	private double currentPosition;
	private double initialPosition;
	private double power = -0.5;
	
	private DriveTrain driveTrain;
	private Shooter shooter;
	private IntakeLift intakeLift;
	private IntakeClaw intakeClaw;
	
	private double liftSpeed = 0.5;
	
    public ShootWhileMove(DriveTrain driveTrain, Shooter shooter, IntakeLift intakeLift, IntakeClaw intakeClaw) {
    	
    	this.driveTrain = driveTrain;
    	this.shooter = shooter;
    	this.intakeLift = intakeLift;
    	this.intakeClaw = intakeClaw;
    	
    	requires(this.intakeClaw);
      	requires(this.driveTrain);
    	requires(this.shooter);
    }
    
    protected void initialize() {
//    	driveTrain.zeroEncoders();
    	intakeClaw.openClaw();
    	initialPosition = (Math.abs(driveTrain.getLeftEncoderPosition())+Math.abs(driveTrain.getRightEncoderPosition()))/2;
    	//intakeLift.setLiftPower(liftSpeed);
    }
    
    protected void execute() {
    	currentPosition = (Math.abs(driveTrain.getLeftEncoderPosition())+Math.abs(driveTrain.getRightEncoderPosition()))/2;
    	
    	if (currentPosition >= initialPosition+distanceToShoot) {
    		shooter.shoot();
    	}
    	
    	if (intakeLift.isArmUp()) {
    		intakeLift.stopLift();
    	}
    	
    	driveTrain.setLeftRightDrivePower(power, power);
    }
    
  	protected boolean isFinished() {
		return currentPosition >= distanceTotal+initialPosition;
	}
	
	protected void end() {
		driveTrain.stop();
    	shooter.stop();
    	intakeLift.stopLift();
    	intakeClaw.stopClaw();
		
	}
	
	protected void interrupted() {
    	end();
    }
}
 