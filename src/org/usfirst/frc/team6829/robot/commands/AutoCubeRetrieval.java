package org.usfirst.frc.team6829.robot.commands;

import org.usfirst.frc.team6829.robot.commands.driveTrain.PursueCube;
import org.usfirst.frc.team6829.robot.commands.intake.CloseIntake;
import org.usfirst.frc.team6829.robot.commands.intake.OpenIntake;
import org.usfirst.frc.team6829.robot.commands.intake.RollerInVision;
import org.usfirst.frc.team6829.robot.subsystems.IntakeClaw;
import org.usfirst.frc.team6829.robot.subsystems.IntakeFlywheel;

import edu.wpi.first.wpilibj.command.CommandGroup;
import team6829.common.DriveTrain;
import team6829.common.transforms.DummyTransform;
import team6829.common.transforms.ITransform;
import team6829.vision.RaspberryPiComms;

/**
 *
 */
public class AutoCubeRetrieval extends CommandGroup {

	private static ITransform noTransform = new DummyTransform();
	
    public AutoCubeRetrieval(DriveTrain driveTrain, IntakeClaw intakeClaw, IntakeFlywheel flywheel, RaspberryPiComms vision) {
    	addParallel(new RollerInVision(vision, flywheel));
    	addSequential(new OpenIntake(intakeClaw));
    	addSequential(new PursueCube(driveTrain, vision, noTransform));
    	addSequential(new CloseIntake(intakeClaw));

    }
}
