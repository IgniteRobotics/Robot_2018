package org.usfirst.frc.team6829.robot.commands.dumper;

import org.usfirst.frc.team6829.robot.subsystems.Dumper;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DumpCube extends CommandGroup {

	private Dumper dumper;

	private double home_setpoint = 0;
	private double setpoint = 0;
	private double tolerance = 0;
	private double power = 0;

	public DumpCube(Dumper dumper) {

		this.dumper = dumper;
		requires(this.dumper);

		addSequential(new MoveToEncoderSetpoint(dumper, power, setpoint, tolerance));
		addSequential(new MoveToEncoderSetpoint(dumper, power, home_setpoint, tolerance));

	}
}
