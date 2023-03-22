package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BotUtilities {
    Telemetry telemetry;

    public BotUtilities(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public int getEncoderValue(DcMotor motor) {
        return motor.getCurrentPosition();
    }

    public void printEncoder(DcMotor motor) {
        telemetry.addData(String.valueOf(motor), motor.getCurrentPosition());
    }

    public void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // no-op
        }
    }

}
