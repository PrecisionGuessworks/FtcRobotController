package org.firstinspires.ftc.teamcode18638.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Elevator {
    private CRServo upIntakeServo;
    private CRServo downIntakeServo;

    public Elevator(HardwareMap hardwareMap , Telemetry telemetry){
        upIntakeServo = hardwareMap.get(CRServo.class, "upIntakeServo");
        downIntakeServo = hardwareMap.get(CRServo.class, "downIntakeSevo");

        upIntakeServo.setDirection(CRServo.Direction.FORWARD);
        downIntakeServo.setDirection(CRServo.Direction.REVERSE);
    }

    public void raise(){setPower(0.5);}

    public void idle(){setPower(0);}

    public void moveDown(){setPower(-0.5);}

    public void setPower(double power){
        upIntakeServo.setPower(power);
        downIntakeServo.setPower(power);
    }
    }
