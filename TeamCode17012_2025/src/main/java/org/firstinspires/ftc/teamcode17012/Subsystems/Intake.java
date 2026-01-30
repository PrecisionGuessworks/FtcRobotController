package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private CRServo intake4barLeft, intake4barRight;
    private DcMotorEx intakeExtension;

    private Servo intakeTurret;
    private Servo intakePincher;

    private final double V4BAR_ROTATION_SPEED = 0.125;

    public Intake(HardwareMap hardwareMap){
        intakeExtension = hardwareMap.get(DcMotorEx.class, "intakeExt");
        intakeExtension.setDirection(DcMotorEx.Direction.REVERSE);

        intake4barRight = hardwareMap.get(CRServo.class, "intake4barLeft");
        intake4barLeft = hardwareMap.get(CRServo.class, "intake4barRight");

        intakeTurret = hardwareMap.get(Servo.class, "intakeTurret");
        intakePincher = hardwareMap.get(Servo.class, "intakeGrab");
    }

    // Pincher
    public void closePincher(){
        intakePincher.setPosition(1);
    }
    public void openPincher(){
        intakePincher.setPosition(0);
    }

    // Turret
    public void rotateClawLong(){
        intakeTurret.setPosition(1);
    }
    public void rotateClawWide(){
        intakeTurret.setPosition(.5);
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

    // Virtual 4 Bar - Servo Mode
//    public void deployV4Bar(){
//        intake4barLeft.setPosition(0.45);
//        intake4barRight.setPosition(0.46);
//    }
//    public void retractV4Bar(){
//        rotateClawWide();
//        intake4barLeft.setPosition(0.0);
//        intake4barRight.setPosition(.75);
//    }
//    public void manualV4Bar(boolean forward){
//        double currentPositionL = intake4barLeft.getPosition();
//        double currentPositionR = intake4barRight.getPosition();
//        if (forward) {
//            intake4barLeft.setPosition(currentPositionL + 0.005);
//            intake4barRight.setPosition(currentPositionR - 0.005);
//        } else {
//            intake4barLeft.setPosition(currentPositionL - 0.005);
//            intake4barRight.setPosition(currentPositionR + 0.005);
//        }
//    }

    // Virtual 4 Bar - Continuous Mode
    public void rotate4BarForward(){
        intake4barRight.setPower(-V4BAR_ROTATION_SPEED);
        intake4barLeft.setPower(V4BAR_ROTATION_SPEED);
    }
    public void rotate4BarBackwards(){
        intake4barRight.setPower(V4BAR_ROTATION_SPEED);
        intake4barLeft.setPower(-V4BAR_ROTATION_SPEED);
    }
    public void stop4Bar(){
        intake4barLeft.setPower(0);
        intake4barRight.setPower(0);
    }
}
