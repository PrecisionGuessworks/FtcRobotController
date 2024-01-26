package org.firstinspires.ftc.teamcode18638.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode18638.Constants;

public class Grabber {
    // TODO 1: Wrist mechanism variables here. They are Servos named gripper and wrist
    private Servo gripper;
    private Servo wrist;

    // No edit
    Telemetry telemetry;

    public Grabber(HardwareMap hardwareMap, Telemetry telemetry){
        // TODO 2: Hardware Map
        gripper = hardwareMap.get(Servo.class, "gripper");
        wrist = hardwareMap.get(Servo.class, "wrist");
        this.telemetry = telemetry;
    }

    // TODO: Set wrist positions
    public void setWristUp(){
        wrist.setPosition(Constants.WRIST_STOWED_POSITION);
    }
    public void setWristDown(){
        wrist.setPosition(Constants.WRIST_DEPLOYED_POSITION);
    }
    public double getWristPosition(){
        return wrist.getPosition();
    }
    public double getGripperPosition(){
        return gripper.getPosition();
    }
    public void openGripper(){
        gripper.setPosition(Constants.GRIPPER_OPEN_POSITION);
    }
    public void closeGripper(){
        gripper.setPosition(Constants.GRIPPER_CLOSED_POSITION);
    }
}
