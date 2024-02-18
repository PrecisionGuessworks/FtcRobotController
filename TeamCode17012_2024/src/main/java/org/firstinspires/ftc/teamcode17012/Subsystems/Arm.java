package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {
    private DcMotor armMotor;
    Telemetry telemetry;

    public Arm(HardwareMap hardwareMap, Telemetry telemetry) {
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.telemetry = telemetry;
    }

    public void setArmPower(double pow){
        pow = deadband(pow);
        armMotor.setPower(pow);

        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public double deadband(double x) {
        //TODO: if x is greater than 0.1 or x is less than -0.1, return x otherwise return 0.0
        if (x > 0.1) {
            return x;
        } else if (x < -0.1) {
            return x;
        } else {
            return 0;
        }
    }   // deadband
}
