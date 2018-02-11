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

		Command foo = null;

		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		System.out.println(gameData);

		if (LeftPositionAuto) {
			System.out.println("Left Auto");  
		}
		if (MiddlePositionAuto) {
			System.out.println("Middle Auto");  
		} 
		if (RightPositionAuto) {
			System.out.println("Right Auto");  
		}

		if(gameData.charAt(0) == 'L') {
			System.out.println("Left");
		} else {
			System.out.println("Right");
		}

		// Left Switch && Positioned Left 
		if(gameData.charAt(0) == 'L' && LeftPositionAuto) {			

			// TODO: Put left-switch auto code here

			foo = autonCommands.get("Go Straight");
			System.out.println("Forwards (s-left)");
			return foo;
		}

		// Right Switch && Positioned Left
		if (gameData.charAt(0) == 'R' && LeftPositionAuto) { 
			// TODO: Put right-switch auto code here
			System.out.println("Either Turn and go forwards or Loop around switch (s-right)");
		}

		// Left Switch && Positioned Right
		if (gameData.charAt(0) == 'L' && RightPositionAuto) {
			// TODO: Put right-switch auto code here
			System.out.println("Either Turn and go forwards or Loop around switch (s-left)");
		}

		// Right Switch && Positioned Right
		if (gameData.charAt(0) == 'R' && RightPositionAuto) {
			// TODO: Put right-switch auto code here
			System.out.println("Forwards (s-right)");
		}

		return foo;
	}

}
