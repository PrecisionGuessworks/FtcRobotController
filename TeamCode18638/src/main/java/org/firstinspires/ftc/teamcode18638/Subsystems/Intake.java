package org.firstinspires.ftc.teamcode18638.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    // Note: There might not be 2 programmed servos for the intake. It might be 2 paired electrically with one in reverse. Therefore, code may only be needed for one. Currently unknown.
    private CRServo leftIntakeServo;
    private CRServo rightIntakeServo;

    public Intake(HardwareMap hardwareMap , Telemetry telemetry){
        leftIntakeServo = hardwareMap.get(CRServo.class, "LeftIntakeServo");
        rightIntakeServo = hardwareMap.get(CRServo.class, "RightIntakeServo");

        leftIntakeServo.setDirection(CRServo.Direction.FORWARD);
        rightIntakeServo.setDirection(CRServo.Direction.REVERSE);
    }

    public void intake(){
        setIntakePower(0.5);
    }

    public void stopIntaking(){
        setIntakePower(0);
    }
    public void spitout(){
        setIntakePower(-0.5);
    }

    public void setIntakePower(double power){
        leftIntakeServo.setPower(power);
        rightIntakeServo.setPower(power);
    }
}