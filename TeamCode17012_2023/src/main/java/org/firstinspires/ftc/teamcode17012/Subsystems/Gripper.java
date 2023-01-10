package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Gripper {
    Telemetry telemetry;

    // TODO: Declare the servo(s) used for the gripper.
    Servo gripperLeft,gripperRight;

    public Gripper(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // TODO: USe hardwareMap.get for each servo created above
        gripperLeft = hardwareMap.get(Servo.class,"gripperLeft");
        gripperRight = hardwareMap.get(Servo.class,"gripperRight");
    }

    // TODO: Write method to close the gripper
    public void closeGripper() {
        gripperLeft.setPosition(.48);
        gripperRight.setPosition(.39);
    }
    // TODO: Write method to open the gripper
    public void openGripper() {
        gripperLeft.setPosition(.6);
        gripperRight.setPosition(.25);
    }

    public double getLeftPos(){
        return gripperLeft.getPosition();
    }
    public double getRightPos(){
        return gripperRight.getPosition();
    }
}
