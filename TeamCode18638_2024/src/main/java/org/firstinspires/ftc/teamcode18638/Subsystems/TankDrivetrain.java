package org.firstinspires.ftc.teamcode18638.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode18638.Constants;

public class TankDrivetrain {
    // TODO 1: Create the motor objects for the drivetrain only


    // Do not edit
    Telemetry telemetry;
    OnboardGyro imu;

    public TankDrivetrain(HardwareMap hardwareMap, Telemetry telemetry) {
        // TODO 2: Link these objects to their physcial counterparts on the robot


        // TODO 3: Set each motor objects default direction


        // TODO 4: Set each motor to function in brake mode


        // Do not edit
        imu = new OnboardGyro(hardwareMap, telemetry);
        this.telemetry = telemetry;
    }



    // TODO: Complete the setMotorPower method between the braces below
    public void setMotorPower(double left, double right) {


    }

    // TODO: Complete the arcadeDrive method between the code fragments below
    public double[] arcadeDrive(double throttle, double rotation){
        double[] motorPowers = new double[2];


        return motorPowers;
    }


    // TODO: Complete the deadband method between the braces below
    public double deadband(double x) {
        //if x is greater than 0.1 or x is less than -0.1, return x. Otherwise return 0.0

    }   // deadband


    // TODO: Complete the stopDriving method between the braces below
    public void stopDriving() {

    }

}

