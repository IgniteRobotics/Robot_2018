package org.usfirst.frc.team6829.robot.commands;

import org.usfirst.frc.team6829.robot.commands.intake.ZeroIntake;
import org.usfirst.frc.team6829.robot.subsystems.IntakeLift;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ZeroIntakeWhileGo extends CommandGroup {

    public ZeroIntakeWhileGo(Command toRun, IntakeLift intakeLift) {
    	
    	//addParallel(new ZeroIntake(intakeLift));
    	//addSequential(toRun);
    	
    }
}
