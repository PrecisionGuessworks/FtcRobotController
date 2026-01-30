package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Climber {
    public CRServo boathookPivot;
    public CRServo hangFlipLeft;
    public CRServo hangFlipRight;
    public DcMotorEx boathookExtension;


    public Climber(HardwareMap hardwareMap) {
        boathookPivot = hardwareMap.get(CRServo.class, "bhPivot");
        //boathookPivot.setDirection(DcMotorSimple.Direction.REVERSE);

        boathookExtension = hardwareMap.get(DcMotorEx.class, "bhExt");
        //boathookExtension.setDirection(DcMotorSimple.Direction.REVERSE);

        hangFlipLeft = hardwareMap.get(CRServo.class, "hangFlipLeft");
        hangFlipRight = hardwareMap.get(CRServo.class, "hangFlipRight");
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
        hangFlipLeft.setPower(-0.4);
        hangFlipRight.setPower(0.4);
    }
    public void retractHangFlippers(){
        hangFlipLeft.setPower(0.4);
        hangFlipRight.setPower(-0.4);

    }

    public void stopHangFlippers() {
        hangFlipLeft.setPower(0);
        hangFlipRight.setPower(0);
    }
}
