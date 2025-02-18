package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Climber {
    public CRServo boathookPivot;
    public Servo hangFlipLeft;
    public Servo hangFlipRight;
    public DcMotorEx boathookExtension;


    public Climber(HardwareMap hardwareMap) {
        boathookPivot = hardwareMap.get(CRServo.class, "bhPivot");
        //boathookPivot.setDirection(DcMotorSimple.Direction.REVERSE);

        boathookExtension = hardwareMap.get(DcMotorEx.class, "bhExt");
        //boathookExtension.setDirection(DcMotorSimple.Direction.REVERSE);

        hangFlipLeft = hardwareMap.get(Servo.class, "hangFlipLeft");
        hangFlipRight = hardwareMap.get(Servo.class, "hangFlipRight");
    }

    public void extendBoatHook(){
        boathookExtension.setPower(1);
    }
    public void retractBoatHook(){
        boathookExtension.setPower(-1);
    }
    public void stopBoatHook(){
        boathookExtension.setPower(0);
    }

    public void pivotBoatHookDown(){
        boathookPivot.setPower(1);
    }
    public void pivotBoatHookUp(){
        boathookPivot.setPower(-1);
    }
    public void stopBoatHookPivot() {
        boathookPivot.setPower(0);
    }

    public void deployHangFlippers(){
        hangFlipLeft.setPosition(1);
        hangFlipRight.setPosition(1);
    }
    public void retractHangFlippers(){
        hangFlipLeft.setPosition(-1);
        hangFlipRight.setPosition(1);
    }
}
