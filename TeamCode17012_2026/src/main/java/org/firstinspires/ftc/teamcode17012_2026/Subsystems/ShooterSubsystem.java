package org.firstinspires.ftc.teamcode17012_2026.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode17012_2026.Constants;
import org.firstinspires.ftc.teamcode17012_2026.Constants.ShooterConstants;

/**
 * ShooterSubsystem - Controls dual flywheel shooter with velocity control
 * Mechanically linked flywheels powered by two DC motors
 * Uses DcMotorEx for precise velocity control via encoders
 */
public class ShooterSubsystem {

    private DcMotorEx leftMotor;
    private DcMotorEx rightMotor;
    private Telemetry telemetry;

    private double targetVelocity = 0; // Target velocity in ticks/sec
    private boolean isSpinningUp = false;
    private ElapsedTime spinUpTimer = new ElapsedTime();

    public ShooterSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // Initialize motors as DcMotorEx for velocity control
        leftMotor = hardwareMap.get(DcMotorEx.class, Constants.ShooterHardware.LEFT_MOTOR);
        rightMotor = hardwareMap.get(DcMotorEx.class, Constants.ShooterHardware.RIGHT_MOTOR);

        // Set motor modes to use encoders for velocity control
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set zero power behavior to float (flywheels should coast)
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Reverse one motor if needed (depends on physical mounting)
        // Uncomment and adjust based on your robot:
        // rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * Sets shooter velocity based on distance to target
     * @param distanceInches Distance to target in inches
     */
    public void setVelocityForDistance(double distanceInches) {
        // Clamp distance to valid range
        distanceInches = Math.max(ShooterConstants.MIN_DISTANCE_INCHES,
                         Math.min(ShooterConstants.MAX_DISTANCE_INCHES, distanceInches));

        // Linear interpolation between min and max velocities based on distance
        double distanceRange = ShooterConstants.MAX_DISTANCE_INCHES - ShooterConstants.MIN_DISTANCE_INCHES;
        double velocityRange = ShooterConstants.MAX_VELOCITY - ShooterConstants.MIN_VELOCITY;

        double normalizedDistance = (distanceInches - ShooterConstants.MIN_DISTANCE_INCHES) / distanceRange;
        targetVelocity = ShooterConstants.MIN_VELOCITY + (normalizedDistance * velocityRange);

        setTargetVelocity(targetVelocity);
    }

    /**
     * Sets a specific target velocity for the shooter
     * @param velocity Target velocity in ticks per second
     */
    public void setTargetVelocity(double velocity) {
        this.targetVelocity = velocity;
        isSpinningUp = true;
        spinUpTimer.reset();

        // Set velocity for both motors
        leftMotor.setVelocity(velocity);
        rightMotor.setVelocity(velocity);
    }

    /**
     * Stops the shooter motors
     */
    public void stop() {
        leftMotor.setVelocity(0);
        rightMotor.setVelocity(0);
        targetVelocity = 0;
        isSpinningUp = false;
    }

    /**
     * Checks if shooter is at target velocity and ready to shoot
     * @return True if both motors are within tolerance of target velocity
     */
    public boolean isReadyToShoot() {
        // Must have been spinning up for minimum time
        if (spinUpTimer.milliseconds() < ShooterConstants.SPINUP_TIME_MS) {
            return false;
        }

        // Check if both motors are within velocity tolerance
        double leftVelocity = leftMotor.getVelocity();
        double rightVelocity = rightMotor.getVelocity();

        boolean leftReady = Math.abs(leftVelocity - targetVelocity) <= ShooterConstants.VELOCITY_TOLERANCE;
        boolean rightReady = Math.abs(rightVelocity - targetVelocity) <= ShooterConstants.VELOCITY_TOLERANCE;

        return leftReady && rightReady;
    }

    /**
     * Gets the current target velocity
     * @return Target velocity in ticks/sec
     */
    public double getTargetVelocity() {
        return targetVelocity;
    }

    /**
     * Gets the current actual velocity (average of both motors)
     * @return Current velocity in ticks/sec
     */
    public double getCurrentVelocity() {
        return (leftMotor.getVelocity() + rightMotor.getVelocity()) / 2.0;
    }

    /**
     * Gets the left motor velocity
     * @return Left motor velocity in ticks/sec
     */
    public double getLeftVelocity() {
        return leftMotor.getVelocity();
    }

    /**
     * Gets the right motor velocity
     * @return Right motor velocity in ticks/sec
     */
    public double getRightVelocity() {
        return rightMotor.getVelocity();
    }

    /**
     * Checks if shooter is currently spinning up
     * @return True if shooter is actively spinning up
     */
    public boolean isSpinningUp() {
        return isSpinningUp && !isReadyToShoot();
    }

    /**
     * Manually sets shooter power (for testing - not recommended for competition)
     * @param power Power level (-1.0 to 1.0)
     */
    public void setManualPower(double power) {
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotor.setPower(power);
        rightMotor.setPower(power);
        isSpinningUp = false;
    }

    /**
     * Re-enables velocity control mode (after manual power mode)
     */
    public void enableVelocityControl() {
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Adds telemetry data for debugging
     */
    public void addTelemetry() {
        telemetry.addData("Shooter Target Velocity", "%.0f ticks/sec", targetVelocity);
        telemetry.addData("Shooter Left Velocity", "%.0f ticks/sec", getLeftVelocity());
        telemetry.addData("Shooter Right Velocity", "%.0f ticks/sec", getRightVelocity());
        telemetry.addData("Shooter Ready", isReadyToShoot() ? "YES" : "NO");

        if (isSpinningUp()) {
            telemetry.addData("Spin-up Time", "%.2f sec", spinUpTimer.seconds());
        }
    }
}
