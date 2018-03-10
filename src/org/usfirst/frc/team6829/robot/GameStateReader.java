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

		// Left Switch && Positioned Left 
		if(gameData.charAt(0) == 'L' && LeftPositionAuto) {			
			
			toRun = autonCommands.get("GoStraight");
			System.out.println("Left Switch & Positioned Left");
			return toRun;
		}

		// Right Switch && Positioned Left
		if (gameData.charAt(0) == 'R' && LeftPositionAuto) { 
			toRun = autonCommands.get("GoStraight");
			System.out.println("Right Switch & Positioned Left");
			return toRun;
		}
		
		// Left Switch && Positioned Right
		if (gameData.charAt(0) == 'L' && RightPositionAuto) {
			toRun = autonCommands.get("GoStraight");
			System.out.println("Left Switch & Positioned Right");
			return toRun;
		}

		// Right Switch && Positioned Right
		if (gameData.charAt(0) == 'R' && RightPositionAuto) {
			toRun = autonCommands.get("GoStraight");
			System.out.println("Right Switch & Positioned Right");
			return toRun;
		}
		
		// Left Switch && Positioned Middle
		if (gameData.charAt(0) == 'L' && MiddlePositionAuto) { 
			toRun = autonCommands.get("MiddleStartLeftSwitch");
			System.out.println("Left Switch & Positioned Middle");
			return toRun;
		}
		
		// Right Switch && Positioned Middle
		if (gameData.charAt(0) == 'R' && MiddlePositionAuto) {
			toRun = autonCommands.get("MiddleStartRightSwitch");
			System.out.println("Right Switch & Positioned Middle");
			return toRun;
		}

		return toRun;
	}

}