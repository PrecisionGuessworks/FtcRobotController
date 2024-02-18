package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode17012.Constants;

public class Climber {
    private DcMotor climbMotor;
    Telemetry telemetry;

    public Climber(HardwareMap hardwareMap, Telemetry telemetry) {
        climbMotor = hardwareMap.get(DcMotor.class, "climbMotor");

        climbMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void climbUp(){
        climbMotor.setPower(Constants.CLIMB_UP_SPEED);
    }
    public void climbDown(){
        climbMotor.setPower(Constants.CLIMB_DOWN_SPEED);
    }
    public void holdClimb(){
        climbMotor.setPower(0);
    }
}
