package team6829.motion_profiling;

import java.io.File;
import java.io.FileNotFoundException;

import org.usfirst.frc.team6829.robot.commands.PathFollower;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import team6829.common.DriveTrain;

public class TrajectoryLoader {

	//Declare trajectories to import
	File R_GoStraightAuton;
	File L_GoStraightAuton;

	private DriveTrain driveTrain;

	public TrajectoryLoader(DriveTrain driveTrain) {

		this.driveTrain = driveTrain;

	}

	//Import all of our trajectories from the RoboRIO
	private void importTrajectories() throws FileNotFoundException {

		R_GoStraightAuton = new File("/home/lvuser/right_detailed.csv");
		L_GoStraightAuton = new File("/home/lvuser/left_detailed.csv");

	}

	//Declare all of our commands that path-follow
	public Command goStraightAuton;

	//Load trajectories into commands, catch any potential exceptions
	public void intializePathCommands(){

		try {

			importTrajectories();
			goStraightAuton = new PathFollower(driveTrain, L_GoStraightAuton, R_GoStraightAuton);

		} catch (FileNotFoundException e) {

			DriverStation.reportError("Could not find trajectory!!!" + e.getMessage(), true);

		}
	}

}
