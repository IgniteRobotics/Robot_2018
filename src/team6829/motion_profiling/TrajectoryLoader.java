package team6829.motion_profiling;

import java.io.File;
import java.io.FileNotFoundException;

import org.usfirst.frc.team6829.robot.Robot;
import org.usfirst.frc.team6829.robot.commands.PathFollower;

import edu.wpi.first.wpilibj.DriverStation;
import team6829.common.DriveTrain;

public class TrajectoryLoader {

	//Declare trajectories to import
	private File R_GoStraightAuton;
	private File L_GoStraightAuton;	

	private DriveTrain driveTrain;

	public TrajectoryLoader(DriveTrain driveTrain) {

		this.driveTrain = driveTrain;
	
	}

	//Import all of our trajectories from the RoboRIO
	private void importTrajectories() throws FileNotFoundException {

		R_GoStraightAuton = new File("/home/lvuser/right_detailed.csv");
		L_GoStraightAuton = new File("/home/lvuser/left_detailed.csv");

	}



	//Load trajectories into commands, catch any potential exceptions
	public void intializePathCommands(){

		try {

			importTrajectories();
			
			//assign paths to commands here
			Robot.goStraightAuton = new PathFollower(driveTrain, L_GoStraightAuton, R_GoStraightAuton);

		} catch (FileNotFoundException e) {

			DriverStation.reportError("Could not find trajectory!!! " + e.getMessage(), true);

		}
	}

}
