package org.usfirst.frc.team6829.robot;
import java.util.Map;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GameStateReader {

	private boolean LeftPositionAuto;
	private boolean MiddlePositionAuto;
	private boolean RightPositionAuto;
	private boolean GoStraightAuto;
	
	
	public void setRobotPosition() {

		SmartDashboard.putBoolean("Left Position Auto", false);
		SmartDashboard.putBoolean("Middle Position Auto", false);
		SmartDashboard.putBoolean("Right Position Auto", false);
		SmartDashboard.putBoolean("Go straight auto", true);
	}

	public Command gameStateReader(Map<String, Command> autonCommands) {

		LeftPositionAuto = SmartDashboard.getBoolean("Left Position Auto", false);
		MiddlePositionAuto = SmartDashboard.getBoolean("Middle Position Auto", false);
		RightPositionAuto = SmartDashboard.getBoolean("Right Position Auto", false);
		GoStraightAuto = SmartDashboard.getBoolean("Go straight auto", true);
		
		Command foo = null;

		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		System.out.println(gameData);

		
		if (GoStraightAuto) {
			if (LeftPositionAuto) {
				System.out.println("Left Auto");
				foo = autonCommands.get("Go Straight Sides");
				return foo;
				
			}
			if (MiddlePositionAuto) {
				System.out.println("Middle Auto");
				foo = autonCommands.get("Go Straight Middle");
				return foo;
			} 
			if (RightPositionAuto) {
				System.out.println("Right Auto");
				foo = autonCommands.get("Go Straight Sides");
				return foo;
			}	
		}

		if(gameData.charAt(0) == 'L') {
			System.out.println("LeftSwitch");
		} else {
			System.out.println("RightSwitch");
		}

		// Left Switch && Positioned Left 
		if(gameData.charAt(0) == 'L' && LeftPositionAuto) {			

			// TODO: Put leftswitch auto code here

//			foo = autonCommands.get("Go Straight Motion Profile");
			System.out.println("LSwitch, lStart");
			System.out.println("!!!!!!!!!SHOOTING!!!!!!!!!!!!");
			return foo;
		}

		// Right Switch && Positioned Left
		if (gameData.charAt(0) == 'R' && LeftPositionAuto) { 
			// TODO: Put rightswitch auto code here
			System.out.println("RSwitch, LStart");
		}

		// Left Switch && Positioned Right
		if (gameData.charAt(0) == 'L' && RightPositionAuto) {
			// TODO: Put rightswitch auto code here
			System.out.println("LSwitch, RStart");
		}

		// Right Switch && Positioned Right
		if (gameData.charAt(0) == 'R' && RightPositionAuto) {
			// TODO: Put rightswitch auto code here
			System.out.println("RSwitch, RStart)");
			System.out.println("!!!!!!!!!SHOOTING!!!!!!!!!!!!");
		}

		return foo;
	}

}