package org.firstinspires.ftc.teamcode17012_2026.Subsystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode17012_2026.Constants;
import org.firstinspires.ftc.teamcode17012_2026.Constants.DrivetrainConstants;

/**
 * MecanumDrivetrain - Controls mecanum drivetrain with field-centric capability
 * Supports both robot-centric and field-centric drive modes
 *
 * Motor configuration:
 * - All 4 drive motors connected via Rev SparkMini motor controllers (PWM ports)
 */
public class MecanumDrivetrain {

    private CRServo frontLeftMotor;   // Connected via SparkMini
    private CRServo frontRightMotor;  // Connected via SparkMini
    private CRServo backLeftMotor;    // Connected via SparkMini
    private CRServo backRightMotor;   // Connected via SparkMini
    private IMU imu;
    private Telemetry telemetry;

    private double speedMultiplier = DrivetrainConstants.NORMAL_SPEED;
    private boolean fieldCentricEnabled = DrivetrainConstants.USE_FIELD_CENTRIC;
    private double headingOffset = 0.0; // For resetting field-centric heading

    public MecanumDrivetrain(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // Initialize CRServos (all 4 drive motors via SparkMini controllers on PWM ports)
        frontLeftMotor = hardwareMap.get(CRServo.class, Constants.DrivetrainHardware.FRONT_LEFT_MOTOR);
        frontRightMotor = hardwareMap.get(CRServo.class, Constants.DrivetrainHardware.FRONT_RIGHT_MOTOR);
        backLeftMotor = hardwareMap.get(CRServo.class, Constants.DrivetrainHardware.BACK_LEFT_MOTOR);
        backRightMotor = hardwareMap.get(CRServo.class, Constants.DrivetrainHardware.BACK_RIGHT_MOTOR);

        // Set motor directions (adjust based on your robot's wiring)
        // These are typical for mecanum drivetrains
        frontLeftMotor.setDirection(CRServo.Direction.FORWARD);
        backLeftMotor.setDirection(CRServo.Direction.FORWARD);
        frontRightMotor.setDirection(CRServo.Direction.REVERSE);
        backRightMotor.setDirection(CRServo.Direction.REVERSE);

        // Note: SparkMini controllers don't support brake mode or encoder modes

        // Initialize IMU
        imu = hardwareMap.get(IMU.class, Constants.DrivetrainHardware.IMU);

        // Define hub orientation
        // Control Hub is mounted with logo facing BACKWARD and USB ports facing UP
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
            RevHubOrientationOnRobot.UsbFacingDirection.UP));

        imu.initialize(parameters);
        imu.resetYaw(); // Reset heading to 0
    }

    /**
     * Main drive method - supports both field-centric and robot-centric control
     * @param x Strafe direction (left stick X)
     * @param y Forward direction (left stick Y, inverted because gamepad Y is negative up)
     * @param rotation Rotation (right stick X)
     */
    public void drive(double x, double y, double rotation) {
        // Apply deadband
        x = applyDeadband(x);
        y = applyDeadband(y);
        rotation = applyDeadband(rotation);

        // Apply cubic function for smoother control
        x = Math.pow(x, 3);
        y = Math.pow(y, 3);
        rotation = Math.pow(rotation, 3);

        // Field-centric transformation if enabled
        if (fieldCentricEnabled) {
            double heading = getHeading();
            double rotX = x * Math.cos(-heading) - y * Math.sin(-heading);
            double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

            // Counteract imperfect strafing (mecanum drift correction)
            rotX *= 1.1;

            x = rotX;
            y = rotY;
        }

        // Calculate wheel powers using mecanum kinematics
        double[] wheelSpeeds = new double[4];
        wheelSpeeds[0] = y + x + rotation; // Front Left
        wheelSpeeds[1] = y - x - rotation; // Front Right
        wheelSpeeds[2] = y - x + rotation; // Back Left
        wheelSpeeds[3] = y + x - rotation; // Back Right

        // Normalize wheel speeds
        wheelSpeeds = normalize(wheelSpeeds);

        // Apply speed multiplier
        for (int i = 0; i < wheelSpeeds.length; i++) {
            wheelSpeeds[i] *= speedMultiplier;
        }

        // Set motor powers
        frontLeftMotor.setPower(wheelSpeeds[0]);
        frontRightMotor.setPower(wheelSpeeds[1]);
        backLeftMotor.setPower(wheelSpeeds[2]);
        backRightMotor.setPower(wheelSpeeds[3]);
    }

    /**
     * Stops all drive motors
     */
    public void stop() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    /**
     * Resets the field-centric heading to 0 (current orientation becomes "forward")
     */
    public void resetHeading() {
        imu.resetYaw();
        headingOffset = 0.0;
    }

    /**
     * Gets the current robot heading in radians
     * @return Heading in radians (-PI to PI)
     */
    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS) - headingOffset;
    }

    /**
     * Gets the current robot heading in degrees
     * @return Heading in degrees (-180 to 180)
     */
    public double getHeadingDegrees() {
        return Math.toDegrees(getHeading());
    }

    /**
     * Sets the speed multiplier
     * @param multiplier Speed multiplier (0.0 to 1.0)
     */
    public void setSpeedMultiplier(double multiplier) {
        this.speedMultiplier = Math.max(0.0, Math.min(1.0, multiplier));
    }

    /**
     * Sets normal speed
     */
    public void setNormalSpeed() {
        setSpeedMultiplier(DrivetrainConstants.NORMAL_SPEED);
    }

    /**
     * Sets turbo speed
     */
    public void setTurboSpeed() {
        setSpeedMultiplier(DrivetrainConstants.TURBO_SPEED);
    }

    /**
     * Sets slow speed
     */
    public void setSlowSpeed() {
        setSpeedMultiplier(DrivetrainConstants.SLOW_SPEED);
    }

    /**
     * Enables or disables field-centric mode
     * @param enabled True to enable field-centric
     */
    public void setFieldCentric(boolean enabled) {
        this.fieldCentricEnabled = enabled;
    }

    /**
     * Checks if field-centric mode is enabled
     * @return True if field-centric is enabled
     */
    public boolean isFieldCentric() {
        return fieldCentricEnabled;
    }

    /**
     * Applies deadband to joystick input
     * @param value Input value
     * @return Value with deadband applied
     */
    private double applyDeadband(double value) {
        if (Math.abs(value) < DrivetrainConstants.JOYSTICK_DEADZONE) {
            return 0.0;
        }
        return value;
    }

    /**
     * Normalizes wheel speeds to keep them within -1.0 to 1.0
     * @param wheelSpeeds Array of wheel speeds
     * @return Normalized wheel speeds
     */
    private double[] normalize(double[] wheelSpeeds) {
        double maxMagnitude = 0.0;
        for (double speed : wheelSpeeds) {
            maxMagnitude = Math.max(maxMagnitude, Math.abs(speed));
        }

        if (maxMagnitude > 1.0) {
            for (int i = 0; i < wheelSpeeds.length; i++) {
                wheelSpeeds[i] /= maxMagnitude;
            }
        }

        return wheelSpeeds;
    }

    /**
     * Adds telemetry data for debugging
     */
    public void addTelemetry() {
        telemetry.addData("Drive Mode", fieldCentricEnabled ? "Field-Centric" : "Robot-Centric");
        telemetry.addData("Speed", String.format("%.0f%%", speedMultiplier * 100));
        telemetry.addData("Heading", "%.1f degrees", getHeadingDegrees());
        telemetry.addData("Motor Powers", "FL:%.2f FR:%.2f BL:%.2f BR:%.2f",
            frontLeftMotor.getPower(),
            frontRightMotor.getPower(),
            backLeftMotor.getPower(),
            backRightMotor.getPower());
    }
}
