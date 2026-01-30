package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Gripper {
    Telemetry telemetry;
    
    Servo gripperLeft,gripperRight;

    public Gripper(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        gripperLeft = hardwareMap.get(Servo.class,"gripperLeft");
        gripperRight = hardwareMap.get(Servo.class,"gripperRight");
    }

    public void closeGripper() {
        gripperLeft.setPosition(.48);
        gripperRight.setPosition(.45);
    }

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
