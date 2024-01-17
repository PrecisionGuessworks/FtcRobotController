package org.firstinspires.ftc.teamcode18638.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode18638.Constants;

public class TankDrivetrain {
    private double minDrivePowerToBreakStaticFriction = 0.2;

    // TODO 1: Create the motor objects for the drivetrain only
    private DcMotor rightDrive;
    private DcMotor leftDrive;





    // Do not edit
    Telemetry telemetry;
    OnboardGyro imu;

    public TankDrivetrain(HardwareMap hardwareMap, Telemetry telemetry) {
        // TODO 2: Link these objects to their physical counterparts on the robot
        leftDrive = hardwareMap.get(DcMotor.class,"leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class,"rightDrive");


        // TODO 3: Set each motor object's default direction
        leftDrive.setDirection(REVERSE);


        // TODO 4: Set each motor to function in brake mode
        leftDrive.setZeroPowerBehavior(BRAKE);
        rightDrive.setZeroPowerBehavior(BRAKE);
        // Do not edit
        imu = new OnboardGyro(hardwareMap, telemetry);
        this.telemetry = telemetry;
    }



    // TODO: Complete the setMotorPower method between the braces below
    public void setMotorPower(double left, double right) {
        leftDrive.setPower(left);
        rightDrive.setPower(right);

    }

    // TODO: Complete the arcadeDrive method between the code fragments below
    public double[] arcadeDrive(double throttle, double rotation){
        double leftPower, rightPower;

        // TODO: Assign throttle and rotation to the versions of themselves returned by the deadband method
        throttle = deadband(throttle);
        rotation = deadband(rotation);
        // TODO: Assign throttle and rotation the cube of themselves, divided by 2
        throttle = (throttle * throttle * throttle / 2);
        rotation = (rotation * rotation * rotation / 2);
        // TODO: Assign the sum of throttle and rotation to the variable leftPower, and assign the difference of throttle and rotation to rightPower
        leftPower = (throttle + rotation);
        rightPower = (throttle - rotation);
        // TODO: Assign leftPower and rightPower the standardized version of themselves

        // TODO: Set power to the motors using the setMotorPower method, with leftPower and rightPower as the parameters

        // TODO: Uncomment out the line below
        // double[] motorPowers = {leftPower, rightPower};
        return motorPowers;
    }


    // TODO: Complete the deadband method between the braces below
    public double deadband(double x) {
        // if x is greater than 0.1 or x is less than -0.1, return x. Otherwise return 0.0
        if (Math.abs(x) > 0.1){
            return x;
        } else {
            return 0.0;
        }
    }   // deadband


    // TODO: Complete the stopDriving method between the braces below
    public void stopDriving() {
        // Hint: Use your setMotorPower method
    }


    // Do not edit
    public double standardize(double val){
        if (val > 0.01){
            val = val * (1 - minDrivePowerToBreakStaticFriction) + minDrivePowerToBreakStaticFriction;
        }
        return val;
    }

}

