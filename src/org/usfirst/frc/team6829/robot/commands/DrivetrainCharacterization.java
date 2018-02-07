package org.usfirst.frc.team6829.robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import team6829.common.DriveTrain;

import java.io.FileWriter;
import java.io.IOException;

//Provided by team 6502

public class DrivetrainCharacterization extends TimedCommand {

	private FileWriter fw;
    private TestMode mode;
    
    private double speed = 0; 
    private double rampRate = 0;
    private double maxSpeed = 0;
    
    private DriveTrain driveTrain;

    public enum TestMode {
        QUASI_STATIC,
        STEP_VOLTAGE;
    }

    /**
     * Default constructor
     * @param timeout Duration this command is scheduled to run for (in seconds)
     * @param mode Characterization test type. Either QUASI_STATIC or STEP_VOLTAGE
     * @param rampRate Speed that QUASI_STATIC test ramps at. If null, defaults to 0
     * @param maxSpeed Max speed QUASI_STATIC test can reach, or constant speed that STEP_VOLTAGE runs at. If null,
     *                 defaults to 0.4
     */
    public DrivetrainCharacterization(TestMode mode, double timeout, DriveTrain driveTrain) {
    	
        super(timeout);

        this.mode = mode;
        this.driveTrain = driveTrain;
    
        requires(this.driveTrain);

    }

    /**
     * Called just before this Command runs the first time. If the characterizer is set to QUASI_STATIC, then the
     * speed controllers are set to characterizer's speed variable. If the characterizer is set to STEP_VOLTAGE,
     * then the characterizer sets the speed controllers to a constant speed.
     */
    @Override
    protected void initialize() {
        System.out.println("Chracterization starting...");
        speed = 0;
        try {
            switch (mode) {
                case QUASI_STATIC:
                    fw = new FileWriter("/U/DriveCharacterization/velFile.csv", false);
                    break;
                case STEP_VOLTAGE:
                    fw = new FileWriter("/U/DriveCharacterization/accelFile.csv", false);
                    speed = maxSpeed;
                    break;
            }
            fw.write("Drive.left_vel, Drive.right_vel, time, Drive.left_voltage, Drive.right_voltage\n");
        }
        catch(IOException e) {
            e.printStackTrace();
            System.out.println("Unable to create FileWriter");
        }
        System.out.println("Writing...");
    }

    /**
     * Called repeatedly when this Command is scheduled to run. If the characterizer is set to QUASI_STATIC mode, then
     * the speed controller input is incremented repeatedly.
     */
    @Override
    protected void execute() {
        driveTrain.setLeftRightDrivePower(speed, speed);
        try {
            fw.write(
                    driveTrain.getLeftEncoderPosition() + ", " +
                    driveTrain.getRightEncoderVelocity() + ", " +
                    timeSinceInitialized()*1000 + ", " +
                    driveTrain.getLeftVoltage() + ", " +
                    driveTrain.getRightVoltage() + "\n"
            );

            //If rampRate causes speed to exceed max speed, stop the command. Speed never exceeds maxSpeed in
            //STEP_VOLTAGE mode because rampRate is 0. Thus, no switch needed.
            if(speed > maxSpeed) {
                System.out.println("Max speed exceeded " + timeSinceInitialized() + " seconds after initialization");
                cancel();
            }
            speed += rampRate;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when command isFinished returns true. Closes FileWriter and sets speed controllers to 0
     */
    @Override
    protected void end() {
        //Ramp slowly back down after test to not destroy gearboxes
        for(double s = speed; s > 0; s -= 0.0003) {
            driveTrain.setLeftRightDrivePower(s, s);
        }
        try {
            fw.flush();
            fw.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done!");
    }
}
