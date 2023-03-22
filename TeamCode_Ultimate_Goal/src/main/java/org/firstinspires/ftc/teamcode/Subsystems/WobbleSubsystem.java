package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class WobbleSubsystem {
    private DcMotor wobbleMotor;
    private CRServo wobbleIntakeServos;
    Telemetry telemetry;

    public WobbleSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        // Wobble Mech Rotation Motor
        wobbleMotor = hardwareMap.get(DcMotor.class, "wobbleMotor");
        wobbleMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        wobbleMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Wobble Intake (Compliant Wheels)
        wobbleIntakeServos = hardwareMap.get(CRServo.class, "wobbleIntakeServos");
        wobbleIntakeServos.setDirection(CRServo.Direction.REVERSE);
        this.telemetry = telemetry;
    }

    public void runIntakeIn(){
        wobbleIntakeServos.setPower(-1);
    }
    public void runIntakeOut(){
        wobbleIntakeServos.setPower(1);
    }
    public void runIntakeIdle() { wobbleIntakeServos.setPower(0.0); }

    public void setArmPower(double speed) {
        wobbleMotor.setPower(speed);
    }

}
