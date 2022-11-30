package org.firstinspires.ftc.teamcode18638.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Gripper {
    Telemetry telemetry;

    // TODO: Declare the servo(s) used for the gripper.

    public Gripper(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // TODO: USe hardwareMap.get for each servo created above
    }

    // TODO: Write method to close the gripper
    // TODO: Write method to open the gripper
}
