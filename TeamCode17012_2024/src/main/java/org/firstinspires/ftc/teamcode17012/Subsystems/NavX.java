package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class NavX {
    Telemetry telemetry;

    public NavX(HardwareMap hardwareMap, Telemetry telemtry){
        this.telemetry = telemtry;
    }

    // TODO: Fix later and/or replace with navx library
    public double getHeadingInRad(){
        return 0.0;
    }
}
