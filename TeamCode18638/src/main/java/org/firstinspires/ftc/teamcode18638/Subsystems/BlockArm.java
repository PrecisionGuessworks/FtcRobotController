package org.firstinspires.ftc.teamcode18638.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BlockArm {
    private Servo blockServo;

    public BlockArm(HardwareMap hardwareMap, Telemetry telemetry) {
        blockServo = hardwareMap.get(Servo.class, "blockServo");
    }

    public void setPosition(double pos) {
        blockServo.setPosition(pos);
    }
}
