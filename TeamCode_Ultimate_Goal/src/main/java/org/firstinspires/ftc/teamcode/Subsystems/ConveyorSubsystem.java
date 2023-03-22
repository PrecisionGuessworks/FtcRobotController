package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ConveyorSubsystem {
    private DcMotor conveyorMotor;
    Telemetry telemetry;
    private static final double POWERFORSHOOTING = 1;
    private static final double POWERFORINTAKING = 0.25;

    public ConveyorSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        conveyorMotor = hardwareMap.get(DcMotor.class, "conveyorMotor");
        conveyorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.telemetry = telemetry;
    }

    public void runConveyorUpIntaking(){
        conveyorMotor.setPower(POWERFORINTAKING);
    }
    public void runConveyorUpShooting(){
        conveyorMotor.setPower(POWERFORSHOOTING);
    }
    public void flushConveyorDown(){
        conveyorMotor.setPower(-1);
    }

    public void setBrakeMode() {conveyorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);}
    public void setCoastMode() {conveyorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);}

    public void idle(){
        conveyorMotor.setPower(0.0);
    }
}
