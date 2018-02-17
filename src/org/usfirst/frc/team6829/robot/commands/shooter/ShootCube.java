package org.usfirst.frc.team6829.robot.commands.shooter;

import org.usfirst.frc.team6829.robot.Robot;
import org.usfirst.frc.team6829.robot.subsystems.*;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShootCube extends Command {

	private Shooter shooter;
	
    public ShootCube(Shooter shooter) {
        // Use requires() here to declare subsystem dependencies
        this.shooter = shooter;
    	requires(this.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	shooter.shoot();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
