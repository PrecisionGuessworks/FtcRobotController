package org.firstinspires.ftc.teamcode17012.Subsystems;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Elevator {
    Telemetry telemetry;


    // TODO: Declare the motor for the elevator. This will be running of a SPARKmini motor contorller. As such, it must be considered a CRServo in the code
        private CRServo elevatorServo;

    public Elevator(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // TODO: Use hardwareMap.get to setup the elevator motor
        elevatorServo = hardwareMap.get(CRServo.class, "ES");

        }



    // TODO: Write method for running the elevator
    public void setElevatorServoPower(double power){
    elevatorServo.setPower(power);
    }
    // TODO: Write method for stopping elevator motion
    public void stopServo() { elevatorServo.setPower(0); }
    // TODO: (Long term) Write method to freeze elevator in place (reading encoder and making sure it doesn't slip)
}
