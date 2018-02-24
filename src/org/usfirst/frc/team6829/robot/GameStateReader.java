package org.usfirst.frc.team6829.robot;
import java.util.Map;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GameStateReader {

	private boolean LeftStartPositionAuto;
	private boolean MiddleStartPositionAuto;
	private boolean RightStartPositionAuto;

	public void setRobotPosition() {

		SmartDashboard.putBoolean("Left Start Position Auto", false);
		SmartDashboard.putBoolean("Middle Start Position Auto", false);
		SmartDashboard.putBoolean("Right Start Position Auto", false);

	}

	public Command gameStateReader(Map<String, Command> autonCommands) {

		LeftStartPositionAuto = SmartDashboard.getBoolean("Left Start Position Auto", false);
		MiddleStartPositionAuto = SmartDashboard.getBoolean("Middle Start Position Auto", false);
		RightStartPositionAuto = SmartDashboard.getBoolean("Right Start Position Auto", false);

		Command foo = null;

		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		System.out.println(gameData);

		if (LeftStartPositionAuto) {
			System.out.println("Left Auto");  
		}
		if (MiddleStartPositionAuto) {
			System.out.println("Middle Auto");  
		} 
		if (RightStartPositionAuto) {
			System.out.println("Right Auto");  
		}

		if(gameData.charAt(0) == 'L') {
			System.out.println("GameData: Left");
		} else {
			System.out.println("GameData: Right");
		}

		// Middle Start Position (generic drive forward) 
		if(gameData.charAt(0) == 'L' && MiddleStartPositionAuto || gameData.charAt(0) == 'R' && MiddleStartPositionAuto) {			
			foo = autonCommands.get("Go Straight");
			System.out.println("Forwards - Middle Start Position Auto");
			return foo;
		}

		// Right Switch Positions Auto
		if (gameData.charAt(0) == 'R') { 
			if(LeftStartPositionAuto) {
				// TODO: Put right-switch/left position auto code here
				System.out.println("Left start position (right switch)");
			} 
			
//			if(MiddleStartPositionAuto) {
//				// TODO: Put right-switch/middle position auto code here
//				System.out.println("Middle start position (right switch)");
//			}
			
			if(RightStartPositionAuto) {
				// TODO: Put right-switch/right position auto code here
				System.out.println("Right start position (right switch)");
			}
		}

		// Left Switch Positions Auto
		if (gameData.charAt(0) == 'L') {
			if(LeftStartPositionAuto) {
				// TODO: Put left-switch/left position auto code here
				System.out.println("Left start position (left switch)");
			} 
			
//			if(MiddleStartPositionAuto) {
//				// TODO: Put left-switch/middle position auto code here
//				System.out.println("Middle start position (left switch)");
//			}
			
			if(RightStartPositionAuto) {
				// TODO: Put left-switch/right position auto code here
				System.out.println("Right start position (left switch)");
			}
		}


		return foo;
	}

}
