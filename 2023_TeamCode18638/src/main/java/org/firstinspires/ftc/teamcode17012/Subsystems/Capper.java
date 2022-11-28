package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Capper {

    private Servo verticalServo;
    private CRServo extenderServo, horizontalServo;

    public Capper(HardwareMap hardwareMap, Telemetry telemetry){
        horizontalServo = hardwareMap.get(CRServo.class, "horizontalServo");
        verticalServo = hardwareMap.get(Servo.class, "verticalServo");
        extenderServo = hardwareMap.get(CRServo.class, "extenderServo");
        verticalServo.scaleRange(0.5, 0.95);
    }

    public void setPowerOfExtenderServo(double power){
        extenderServo.setPower(power);
    }

    public void setOrientation(double vertical){
        verticalServo.setPosition(vertical);
    }

    public void setPowerOfTurretServo(double power){
        horizontalServo.setPower(power);
    }

    public double getVerticalOrientation(){
        return verticalServo.getPosition();
    }

}
