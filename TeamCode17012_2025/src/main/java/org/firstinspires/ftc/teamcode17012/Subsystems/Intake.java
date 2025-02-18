package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public Servo intake4barLeft, intake4barRight;
    public DcMotorEx intakeExtension;

    public Servo intakeTurret;
    public Servo intakePincher;

    public Intake(HardwareMap hardwareMap){
        intakeExtension = hardwareMap.get(DcMotorEx.class, "intakeExt");
        //intakeExtension.setDirection(DcMotorSimple.Direction.REVERSE);

        intake4barRight = hardwareMap.get(Servo.class, "intake4barLeft");
        intake4barLeft = hardwareMap.get(Servo.class, "intake4barRight");

        intakeTurret = hardwareMap.get(Servo.class, "intakeTurret");
        intakePincher = hardwareMap.get(Servo.class, "intakeGrab");
    }

    // Pincher
    public void closePincher(){
        intakePincher.setPosition(.4);
    }
    public void openPincher(){
        intakePincher.setPosition(0);
    }

    // Turret
    public void rotateClawLong(){
        intakeTurret.setPosition(0.4);
    }
    public void rotateClawWide(){
        intakeTurret.setPosition(0);
    }

    // Extension
    public void extendIntake(){
        intakeExtension.setPower(0.65);
    }
    public void retractIntake(){
        intakeExtension.setPower(-1);
    }
    public void stopIntakeExtension(){
        intakeExtension.setPower(0);
    }

    // Virtual 4 Bar
    public void deployV4Bar(){
        intake4barLeft.setPosition(-0.3);
        intake4barRight.setPosition(0.3);
    }
    public void retractV4Bar(){
        intake4barLeft.setPosition(0.3);
        intake4barRight.setPosition(-0.3);
    }
    public void manualV4Bar(boolean forward){
        double currentPositionL = intake4barLeft.getPosition();
        double currentPositionR = intake4barRight.getPosition();
        if (forward) {
            intake4barLeft.setPosition(currentPositionL + 0.05);
            intake4barRight.setPosition(currentPositionR + 0.05);
        } else {
            intake4barLeft.setPosition(currentPositionL - 0.05);
            intake4barRight.setPosition(currentPositionR - 0.05);
        }
    }
}
