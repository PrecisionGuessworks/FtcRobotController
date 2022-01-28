package org.firstinspires.ftc.teamcode18638.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;



public class Intake {
    private CRServo leftIntakeServo;
    private CRServo rightIntakeServo;

    public Intake(hardwareMap hardwareMap, Telemetry telemetry){
        leftIntakeSevo = hardwareMap.get(CRservo.class, "LeftIntakeServo");
        rightIntakeServo = hardwareMap.get(CRservo.class, "RightIntakeServo");

        leftIntakeServo.setDirection(CRServo.Direction.Forword);
        rightIntakeServo.setDirection(CRServo.Direction.Backword);
    }

    public void intake(){
        setPower(0.5);
    }
    public void idle(){
        setpower(0);
    }
    public void spitout(){
        setPower(-0.5);

    }
    public void setPower(double power){
        leftIntakeServo.setPower(power);
        rightIntakeServo.setPower(power);
    }
}