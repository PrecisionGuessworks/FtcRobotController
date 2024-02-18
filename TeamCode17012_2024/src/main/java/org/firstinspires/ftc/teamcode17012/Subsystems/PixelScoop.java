package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode17012.Constants;

public class PixelScoop {
    Servo pixelScoop;
    Telemetry telemetry;

    public PixelScoop(HardwareMap hardwareMap, Telemetry telemetry){
        pixelScoop = hardwareMap.get(Servo.class, "pixelScoop");
        this.telemetry = telemetry;
    }

    public void holdPixel(){
        pixelScoop.setPosition(Constants.PIXEL_SCOOP_HOLD_POSITION);
    }
    public void deployPixel(){
        pixelScoop.setPosition(Constants.PIXEL_SCOOP_DEPLOY_POSITION);
    }

    public void scoopTelemetry(){
        telemetry.addData("Scoop position", pixelScoop.getPosition());
        telemetry.update();
    }
}
