package org.firstinspires.ftc.teamcode18638.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode18638.Constants;

public class Wrist {
    // TODO 1: Wrist mechanism variables here. They are Servos named gripper and wrist

    // No edit
    Telemetry telemetry;

    public Wrist(HardwareMap hardwareMap, Telemetry telemetry){
        // TODO 2: Hardware Map


        this.telemetry = telemetry;
    }

    // TODO: Set wrist positions
    public void setWristUp(){

    }
    public void setWristDown(){

    }
    public void openGripper(){
        gripper.setPosition(Constants.GRIPPER_OPEN_POSITION);
    }
    public void closeGripper(){
        gripper.setPosition(Constants.GRIPPER_CLOSED_POSITION);
    }
}
