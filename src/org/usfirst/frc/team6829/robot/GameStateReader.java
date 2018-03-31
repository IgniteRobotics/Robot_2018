package org.usfirst.frc.team6829.robot;
import java.util.Map;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GameStateReader {

	private boolean LeftPositionAuto;
	private boolean MiddlePositionAuto;
	private boolean RightPositionAuto;

	public void setRobotPosition() {

		SmartDashboard.putBoolean("Left Position Auto", false);
		SmartDashboard.putBoolean("Middle Position Auto", false);
		SmartDashboard.putBoolean("Right Position Auto", false);

	}

	public Command gameStateReader(Map<String, Command> autonCommands) {

		LeftPositionAuto = SmartDashboard.getBoolean("Left Position Auto", false);
		MiddlePositionAuto = SmartDashboard.getBoolean("Middle Position Auto", false);
		RightPositionAuto = SmartDashboard.getBoolean("Right Position Auto", false);

		Command toRun = null;

		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		System.out.println(gameData);

		if (LeftPositionAuto) {
			System.out.println("Positioned Left");  
		}
		if (MiddlePositionAuto) {
			System.out.println("Positioned Middle");  
		} 
		if (RightPositionAuto) {
			System.out.println("Positioned Right");  
		}

		if(gameData.charAt(0) == 'L') {
			System.out.println("Left Switch");
		} else {
			System.out.println("Right Right");
		}

		toRun = autonCommands.get("GoStraight");
		
		if(MiddlePositionAuto && gameData.charAt(0) == 'L') {
			toRun = autonCommands.get("MiddleStartLeftSwitch");
			return toRun;
		}
		
		if(MiddlePositionAuto && gameData.charAt(0) == 'R') {
			toRun = autonCommands.get("MiddleStartRightSwitch");
			return toRun;
		}
		
		
		if(gameData.charAt(1) == 'L' && LeftPositionAuto) {			
			toRun = autonCommands.get("LeftStartScaleShoot");
			return toRun;
		}
		
		if(gameData.charAt(1) == 'R' && RightPositionAuto) {
			toRun = autonCommands.get("RightStartScaleShoot");
			return toRun;
		}
		
		return toRun;
	}

}