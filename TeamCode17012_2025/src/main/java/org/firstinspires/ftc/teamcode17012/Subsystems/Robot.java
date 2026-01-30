package org.firstinspires.ftc.teamcode17012.Subsystems;

import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.ZYX;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Robot {

//    public static double ff = 0.09;
//    IntegratingGyroscope gyro;
//    NavxMicroNavigationSensor imu;

//    public CRServo boathookPivot;
//    public Servo hangFlipLeft;
//    public Servo hangFlipRight;
//    public DcMotorEx boathookExtension;

    public Servo intake4barLeft, intake4barRight;
    public DcMotorEx intakeExtension;

    public Servo intakeTurret;
    public Servo intakePincher;

//    public DcMotorEx liftLeft, liftRight;
//    public Servo specimenGrabberLeft, specimenGrabberRight;

    private DcMotorEx frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    DcMotorEx[] motors;

    public Robot(HardwareMap hardwareMap) {
        // Drive Train
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "FL");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "BL");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "FR");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "BR");

        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        imu = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
//        while (imu.isCalibrating()) {}
//        gyro = (IntegratingGyroscope) imu;

//        // Hang
//        boathookPivot = hardwareMap.get(CRServo.class, "bhPivot");
//        //boathookPivot.setDirection(DcMotorSimple.Direction.REVERSE);
//        boathookExtension = hardwareMap.get(DcMotorEx.class, "bhExt");
//        //boathookExtension.setDirection(DcMotorSimple.Direction.REVERSE);
//        hangFlipLeft = hardwareMap.get(Servo.class, "hangFlip1");
//        hangFlipRight = hardwareMap.get(Servo.class, "hangFlip2");

        // Intake
        intakeExtension = hardwareMap.get(DcMotorEx.class, "intakeExt");
        //intakeExtension.setDirection(DcMotorSimple.Direction.REVERSE);

        intake4barRight = hardwareMap.get(Servo.class, "intake4barLeft");
        intake4barLeft = hardwareMap.get(Servo.class, "intake4barRight");
        intakeTurret = hardwareMap.get(Servo.class, "intakeTurret");
        intakePincher = hardwareMap.get(Servo.class, "intakeGrab");

        // Lift
//        liftLeft= hardwareMap.get(DcMotorEx.class, "liftLeft");
//        liftRight = hardwareMap.get(DcMotorEx.class, "liftRight");
//        liftLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//
//
//        specimenGrabberLeft = hardwareMap.get(Servo.class, "specimenGrabberLeft");
//        specimenGrabberRight = hardwareMap.get(Servo.class, "specimenGrabberRight");

    }

//    public double getHeading() {
//        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, ZYX, AngleUnit.RADIANS);
//        return angles.firstAngle;
//    }
//
//
//    public void update() {
//
//    }
//
//    public double deadband(double x) {
//        //TODO: if x is greater than 0.1 or x is less than -0.1, return x otherwise return 0.0
//        if (x > 0.1) {
//            return x;
//        } else if (x < -0.1) {
//            return x;
//        } else {
//            return 0;
//        }
//    }   // deadband
//
//    private double[] normalize(double[] wheelSpeeds) {
//        double maxMagnitude = Math.abs(wheelSpeeds[0]);
//
//        for (int i = 1; i < wheelSpeeds.length; i++){
//            double magnitude = Math.abs(wheelSpeeds[i]);
//            if (magnitude > maxMagnitude){
//                maxMagnitude = magnitude;
//            }
//        }
//
//        if (maxMagnitude > 1.0) {
//            for (int i = 0; i < wheelSpeeds.length; i++){
//                wheelSpeeds[i] /= maxMagnitude;
//            }
//        }
//
//
//        return wheelSpeeds;
//    }   //normalize
//
//    public void setMotorPower(double FL, double BL, double FR, double BR) {
//        frontLeftMotor.setPower(FL);
//        backLeftMotor.setPower(BL);
//        frontRightMotor.setPower(FR);
//        backRightMotor.setPower(BR);
//        // See we take the frontLeftMotor and use the setPower method to set the power to the
//        // value of the FL variables. Do this for the all the other motors
//
//    }
//
//    public void drive(double x, double y, double h) {
//        double wheelSpeeds[] = new double[4];
//
//        // Deadband prevents controller movement for very small motions to prevent unintentional movements
//        x = deadband(x);
//        y = deadband(y);
//        h = deadband(h);
//
//        //Cubic funtion for controls
//        x = x * x * x;
//        y = y * y * y;
//        h = Math.pow(h, 3);
//
//        //Mecanum Math
//        wheelSpeeds[0] = x - y + h;
//        wheelSpeeds[1] = x + y - h;
//        wheelSpeeds[2] = x + y + h;
//        wheelSpeeds[3] = x - y - h;
//
//        // Remaping wheel speeds to be 0 to 1.
//        wheelSpeeds = normalize(wheelSpeeds);
//
//        //Set power to motors. Vroom vroom.
//        setMotorPower(wheelSpeeds[0], wheelSpeeds[1], wheelSpeeds[2], wheelSpeeds[3]);
//    }
//
//    public void driveFieldCentric(double x, double y, double h) {
//        double rotX = x * Math.cos(getHeading()) - y * Math.sin(getHeading());
//        double rotY = x * Math.sin(getHeading()) + y * Math.cos(getHeading());
//
//        rotX = rotX * 1.1;  // Counteract imperfect strafing
//
//        drive(rotX, rotY, h);
//    }

//    public void setLiftPower(double power) {
//        liftLeft.setPower(power + ff);
//        liftRight.setPower(power + ff);
//    }
}
