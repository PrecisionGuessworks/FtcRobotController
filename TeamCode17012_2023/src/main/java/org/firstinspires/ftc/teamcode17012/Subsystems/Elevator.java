package org.firstinspires.ftc.teamcode17012.Subsystems;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Elevator {
    Telemetry telemetry;


    // TODO: Declare the motor for the elevator. This will be running of a SPARKmini motor contorller. As such, it must be considered a CRServo in the code
        private CRServo elevatorServo;

    public Elevator(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // TODO: Use hardwareMap.get to setup the elevator motor
        elevatorServo = hardwareMap.get(CRServo.class, "elevator");

        }

    public void elevatorTelemetry() {
        telemetry.addData("lift motor", elevatorServo.getPower());
    }

    // TODO: Write method for running the elevator
    public void setElevatorPower(double power){
        power = deadband(power);
        if (power < 0) {
            power *= 0.85;
        } else {
            power *= 0.375;
        }
        elevatorServo.setPower(power);
    }
    // TODO: Write method for stopping elevator motion
    public void stopServo() { elevatorServo.setPower(0); }
    public double deadband(double x) {
        //TODO: if x is greater than 0.1 or x is less than -0.1, return x otherwise return 0.0
        if (x > 0.1) {
            return x;
        } else if (x < -0.1) {
            return x;
        } else {
            return 0;
        }
    }   // deadband


    // TODO: (Long term) Write method to freeze elevator in place (reading encoder and making sure it doesn't slip)
}
