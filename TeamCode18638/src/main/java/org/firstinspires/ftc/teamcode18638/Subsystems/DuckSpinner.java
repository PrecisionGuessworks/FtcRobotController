package org.firstinspires.ftc.teamcode18638.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DuckSpinner {
    private CRServo duckSpinningMotor;

    private final double DUCK_MOTOR_SPEED = 0.4;

    public DuckSpinner(HardwareMap hardwareMap, Telemetry telemetry) {
        duckSpinningMotor = hardwareMap.get(CRServo.class, "DuckSpinner");
    }

    public void runDuckMotorForward(){
        setDuckMotorPower(DUCK_MOTOR_SPEED);
    }

    public void runDuckMotorBackwards(){
        setDuckMotorPower(-DUCK_MOTOR_SPEED);
    }

    public void stopMotor() {
        duckSpinningMotor.setPower(0);
    }

    public void setDuckMotorPower(double power){
        duckSpinningMotor.setPower(power);
    }

}
