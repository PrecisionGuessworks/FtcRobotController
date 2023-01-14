package org.firstinspires.ftc.teamcode18638.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Gripper {
    Telemetry telemetry;

    private Servo leftServo, rightServo;

    public Gripper(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        leftServo = hardwareMap.get(Servo.class, "leftClaw");
        rightServo = hardwareMap.get(Servo.class, "rightClaw");
    }

    // TODO: Fix Values
    public void openClaw(){
        leftServo.setPosition(-0.5);
        rightServo.setPosition(-0.5);
    }
    // TODO: Fix values
    public void closeClaw(){
        leftServo.setPosition(.5);
        rightServo.setPosition(.5);
    }
    public void getGripperServoPositions(){
        telemetry.addData("Left servo", leftServo.getPosition());
        telemetry.addData("Right servo", rightServo.getPosition());
        telemetry.update();
    }


}
