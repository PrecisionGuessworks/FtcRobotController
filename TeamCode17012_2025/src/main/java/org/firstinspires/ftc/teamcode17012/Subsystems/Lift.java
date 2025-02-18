package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Lift {
    public static double ff = 0.09;

    public DcMotorEx liftLeft, liftRight;
    public Servo specimenGrabberLeft, specimenGrabberRight;

    public Lift(HardwareMap hardwareMap){
        liftLeft= hardwareMap.get(DcMotorEx.class, "liftLeft");
        liftRight = hardwareMap.get(DcMotorEx.class, "liftRight");
        liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);


        specimenGrabberLeft = hardwareMap.get(Servo.class, "specimenGrabberLeft");
        specimenGrabberRight = hardwareMap.get(Servo.class, "specimenGrabberRight");
    }

    public void setLiftPower(double power) {
        liftLeft.setPower(power + ff);
        liftRight.setPower(power + ff);
    }
    public String getPower(){
        return liftLeft.getPower() + " " + liftRight.getPower();
    }
}
