package org.firstinspires.ftc.teamcode17012.Subsystems;

import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.ZYX;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class MecanumDrivetrain {
    //IntegratingGyroscope gyro;
    //NavxMicroNavigationSensor imu;
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

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//        imu = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
//        while (imu.isCalibrating()) {}
//        gyro = (IntegratingGyroscope) imu;
    }

    public void stopDriving(){
        setMotorPower(0,0,0,0);
    }
    public double deadband(double x) {
        if (Math.abs(x) > 0.1) {
            return x;
        } else {
            return 0;
        }
    }   // deadband

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

    public void setMotorPower(double FL, double BL, double FR, double BR) {
        frontLeftMotor.setPower(FL);
        backLeftMotor.setPower(BL);
        frontRightMotor.setPower(FR);
        backRightMotor.setPower(BR);
        // See we take the frontLeftMotor and use the setPower method to set the power to the
        // value of the FL variables. Do this for the all the other motors

    }

    public void drive(double x, double y, double h) {
        double wheelSpeeds[] = new double[4];

        // Deadband prevents controller movement for very small motions to prevent unintentional movements
        x = deadband(x);
        y = deadband(y);
        h = deadband(h);

        //Cubic funtion for controls
        x = x * x * x;
        y = y * y * y;
        h = Math.pow(h, 3);

        //Mecanum Math
        wheelSpeeds[0] = x - y + h;
        wheelSpeeds[1] = x + y - h;
        wheelSpeeds[2] = x + y + h;
        wheelSpeeds[3] = x - y - h;

        // Remaping wheel speeds to be 0 to 1.
        wheelSpeeds = normalize(wheelSpeeds);

        //Set power to motors. Vroom vroom.
        setMotorPower(wheelSpeeds[0], wheelSpeeds[1], wheelSpeeds[2], wheelSpeeds[3]);
    }

//    public double getHeading() {
//        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, ZYX, AngleUnit.RADIANS);
//        return angles.firstAngle;
//    }
//    public void driveFieldCentric(double x, double y, double h) {
//        double rotX = x * Math.cos(getHeading()) - y * Math.sin(getHeading());
//        double rotY = x * Math.sin(getHeading()) + y * Math.cos(getHeading());
//
//        rotX = rotX * 1.1;  // Counteract imperfect strafing
//
//        drive(rotX, rotY, h);
//    }

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

    }

}

