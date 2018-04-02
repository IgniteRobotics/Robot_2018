package org.usfirst.frc.team6829.robot.commands.driveTrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import team6829.common.DriveTrain;
import team6829.common.transforms.ITransform;
import team6829.vision.RaspberryPiCommms;

/**
 *
 */
public class TurnToCube extends Command {

	private RaspberryPiCommms vision;
	private DriveTrain driveTrain;
	private Joystick driverJoystick;
	private int THROTTLE_AXIS;
	private double kP = 0.0025;
	private double kD = 0.01;
	
	private double turnPower;
	private final ITransform noTransform;
	
	private double x1 = 0;
	private double x2 = 0;

	
    public TurnToCube(DriveTrain driveTrain, RaspberryPiCommms vision, Joystick driverJoystick, int throttleAxis, ITransform noTransform) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.vision = vision;
    	this.driverJoystick = driverJoystick;
    	this.driveTrain = driveTrain;
    	this.THROTTLE_AXIS = throttleAxis;
    	this.noTransform = noTransform;
    	requires(this.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
		double throttle = -driverJoystick.getRawAxis(THROTTLE_AXIS);    	
    	

    	x2 = vision.getX();
    	turnPower = (vision.getX()*kP) - (Math.abs(x2 - x1) * kD);
    	x1 = x2;
    	
    	if (Math.abs(turnPower) > 0.25) {
    		turnPower = Math.copySign(0.25, turnPower);
    	}
    	
		driveTrain.arcadeDrive(throttle, turnPower, 0, noTransform);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
