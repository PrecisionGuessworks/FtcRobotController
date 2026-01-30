package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode17012.Constants;

public class DroneLauncher {
    Servo droneServo;
    Telemetry telemetry;

    public DroneLauncher(HardwareMap hardwareMap, Telemetry telemetry) {
        droneServo = hardwareMap.get(Servo.class, "droneServo");
        this.telemetry = telemetry;
    }

    public void launchDrone(){
        droneServo.setPosition(Constants.DRONE_SERVO_LAUNCH_POSITION);
    }

    public void holdDrone(){
        droneServo.setPosition(Constants.DRONE_SERVO_HOLD_POSITION);
    }
}
