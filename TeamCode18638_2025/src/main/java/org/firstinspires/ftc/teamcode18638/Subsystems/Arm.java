package org.firstinspires.ftc.teamcode18638.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {
    private CRServo shoulder, wrist, intake;
    private Servo claw;


    public Arm(HardwareMap hardwareMap, Telemetry telemetry) {
        shoulder = hardwareMap.get(CRServo.class, "arm");
        wrist = hardwareMap.get(CRServo.class, "wrist");
        claw = hardwareMap.get(Servo.class, "claw");
        intake = hardwareMap.get(CRServo.class, "intake");
    }

    public void intaking(){
        intake.setPower(1);
    }
    public void outtaking(){
        intake.setPower(-1);
    }
    public void stopIntake(){
        intake.setPower(0);
    }

    public void openClaw(){
        claw.setPosition(.15);
    }
    public void closeClaw(){
        claw.setPosition(0.3);
    }
    public double getServoPosition(){
        return claw.getPosition();
    }

    public void moveShoulder(double speed){
        if(Math.abs(speed) < 0.1){
            speed = 0;
        } else {
            speed *= 0.75;
        }
        shoulder.setPower(speed);
    }

    public void moveWrist(double speed) {
        if(Math.abs(speed) < 0.1){
            speed = 0;
        } else {
            speed *= 0.75;
        }
        wrist.setPower(speed);
    }
}
