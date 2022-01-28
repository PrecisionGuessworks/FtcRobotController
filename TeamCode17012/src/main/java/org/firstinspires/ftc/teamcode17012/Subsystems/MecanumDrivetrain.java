package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MecanumDrivetrain {
    private DcMotor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    final double DRIVE_SPEED_MODIFER = 0.75;

    public MecanumDrivetrain(HardwareMap hardwareMap, Telemetry telemtry) {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "FL");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BL");
        frontRightMotor = hardwareMap.get(DcMotor.class, "FR");
        backRightMotor = hardwareMap.get(DcMotor.class, "BR");

        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

    }

    public void stopDriving() {
        setMotorPower(0,0,0,0);
    }

    public void setMotorPower(double FL, double BL, double FR, double BR) {
        //TODO: create the setMotorPower function
        // Example:
        frontLeftMotor.setPower(FL);
        backLeftMotor.setPower(BL);
        frontRightMotor.setPower(FR);
        backRightMotor.setPower(BR);
        // See we take the frontLeftMotor and use the setPower method to set the power to the
        // value of the FL variables. Do this for the all the other motors

    }

    private double[] normalize(double[] wheelSpeeds) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);

        for (int i = 1; i < wheelSpeeds.length; i++){
            double magnitude = Math.abs(wheelSpeeds[i]);
            if (magnitude > maxMagnitude){
                maxMagnitude = magnitude;
            }
        }

        if (maxMagnitude > 1.0) {
            for (int i = 0; i < wheelSpeeds.length; i++){
                wheelSpeeds[i] /= maxMagnitude;
            }
        }


        return wheelSpeeds;
    }   //normalize

    public double deadband(double x) {
        //TODO: if x is greater than 0.1 or x is less than -0.1, return x otherwise return 0.0

        return x;
    }   // deadband

    public void mecanumDrive_Cartesian(double x, double y, double rotation) {
        double wheelSpeeds[] = new double[4];

        // Slow it down
        x *= DRIVE_SPEED_MODIFER;
        y *= DRIVE_SPEED_MODIFER;
        rotation *= DRIVE_SPEED_MODIFER;


        // Deadband prevents controller movement for very small motions to prevent unintentional movements
        x = deadband(x);
        y = deadband(y);
        rotation = deadband(rotation);

        //Cubic funtion for controls
        x = x * x * x;
        y = y * y * y;
        rotation = rotation * rotation * rotation;

        //Mecanum Math
        wheelSpeeds[0] = x - y + rotation;
        wheelSpeeds[1] = x + y - rotation;
        wheelSpeeds[2] = x + y + rotation;
        wheelSpeeds[3] = x - y - rotation;

        // Remaping wheel speeds to be 0 to 1.
        wheelSpeeds = normalize(wheelSpeeds);



        //Set power to motors. Vroom vroom.
        setMotorPower(wheelSpeeds[0], wheelSpeeds[1], wheelSpeeds[2], wheelSpeeds[3]);

    }   //mecanumDrive_Cartesian

}

