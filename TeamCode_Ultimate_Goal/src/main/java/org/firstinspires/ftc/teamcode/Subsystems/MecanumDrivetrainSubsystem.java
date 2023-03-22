package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.BotUtilities;

public class MecanumDrivetrainSubsystem {
    private CRServo frontLeft, frontRight, backLeft, backRight;
    BotUtilities botStuff;

    public MecanumDrivetrainSubsystem(HardwareMap hardwareMap, Telemetry telemtry) {
        frontLeft  = hardwareMap.get(CRServo.class, "FL");
        backLeft  = hardwareMap.get(CRServo.class, "BL");
        frontRight = hardwareMap.get(CRServo.class, "FR");
        backRight = hardwareMap.get(CRServo.class, "BR");

        frontLeft.setDirection(CRServo.Direction.REVERSE);
        backLeft.setDirection(CRServo.Direction.FORWARD);
        frontRight.setDirection(CRServo.Direction.REVERSE);
        backRight.setDirection(CRServo.Direction.FORWARD);

        botStuff = new BotUtilities(telemtry);
    }

    public void setMotors(double FL, double BL, double FR, double BR) {
        frontLeft.setPower(FL);
        backLeft.setPower(BL);
        frontRight.setPower(FR);
        backRight.setPower(BR);
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

    public void stopDriving(){
        setMotors(0,0,0,0);
    }


    public double deadband(double x) {
        double deadBand = 0.1;
        if (Math.abs(x) < deadBand) {
            x = 0.0;
        }
        return x;
    }   // deadband

    public void mecanumDrive_Cartesian(double x, double y, double rotation) {
        double wheelSpeeds[] = new double[4];

        // Deadband prevents controller movement for very small motions to prevent unintentional movements
        x = deadband(x);
        y = deadband(y);
        rotation = deadband(rotation);

        //Cubic funtion for controls
        x = x * x * x;
        y = y * y * y;
        rotation = rotation * rotation * rotation;

        //Mecanum Math
        wheelSpeeds[0] = -x + y + rotation;
        wheelSpeeds[1] = x + y - rotation;
        wheelSpeeds[2] = x + y + rotation;
        wheelSpeeds[3] = -x + y - rotation;

        // Remaping wheel speeds to be 0 to 1.
        wheelSpeeds = normalize(wheelSpeeds);

        //Set power to motors. Vroom vroom.
        setMotors(wheelSpeeds[0], wheelSpeeds[1], wheelSpeeds[2], wheelSpeeds[3]);

    }   //mecanumDrive_Cartesian
}
