package org.firstinspires.ftc.teamcode18638_2026.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode18638_2026.Constants;
import org.firstinspires.ftc.teamcode18638_2026.Constants.FlywheelConstants;

/**
 * FlywheelSubsystem - Controls velocity-based flywheel launcher
 * Uses DcMotorEx for velocity control in ticks per second
 */
public class FlywheelSubsystem {

    private DcMotorEx flywheelMotor;
    private Telemetry telemetry;

    private int targetVelocity = 0;

    public FlywheelSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // Initialize motor as DcMotorEx for velocity control
        flywheelMotor = hardwareMap.get(DcMotorEx.class, Constants.FlywheelHardware.FLYWHEEL_MOTOR);

        // Set motor direction and mode
        flywheelMotor.setDirection(DcMotor.Direction.REVERSE);
        flywheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set zero power behavior
        flywheelMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    /**
     * Sets flywheel to bank shot velocity (near shots)
     */
    public void setBankVelocity() {
        targetVelocity = FlywheelConstants.BANK_VELOCITY;
        flywheelMotor.setVelocity(targetVelocity);
    }

    /**
     * Sets flywheel to far shot velocity
     */
    public void setFarVelocity() {
        targetVelocity = FlywheelConstants.FAR_VELOCITY;
        flywheelMotor.setVelocity(targetVelocity);
    }

    /**
     * Sets flywheel to maximum velocity
     */
    public void setMaxVelocity() {
        targetVelocity = FlywheelConstants.MAX_VELOCITY;
        flywheelMotor.setVelocity(targetVelocity);
    }

    /**
     * Sets flywheel to a custom velocity
     * @param velocity Target velocity in ticks per second
     */
    public void setVelocity(int velocity) {
        targetVelocity = velocity;
        flywheelMotor.setVelocity(velocity);
    }

    /**
     * Runs flywheel in reverse (manual control)
     */
    public void reverse() {
        targetVelocity = 0;
        flywheelMotor.setPower(FlywheelConstants.REVERSE_POWER);
    }

    /**
     * Stops the flywheel
     */
    public void stop() {
        targetVelocity = 0;
        flywheelMotor.setVelocity(0);
    }

    /**
     * Gets current flywheel velocity
     * @return Current velocity in ticks per second
     */
    public double getVelocity() {
        return flywheelMotor.getVelocity();
    }

    /**
     * Gets the target velocity
     * @return Target velocity in ticks per second
     */
    public int getTargetVelocity() {
        return targetVelocity;
    }

    /**
     * Checks if flywheel is at bank shot velocity (within tolerance)
     * @return true if ready for bank shot
     */
    public boolean isReadyForBankShot() {
        return getVelocity() >= FlywheelConstants.BANK_VELOCITY - FlywheelConstants.BANK_TOLERANCE;
    }

    /**
     * Checks if flywheel is at far shot velocity (within tolerance)
     * @return true if ready for far shot
     */
    public boolean isReadyForFarShot() {
        return getVelocity() >= FlywheelConstants.FAR_VELOCITY - FlywheelConstants.FAR_TOLERANCE;
    }

    /**
     * Checks if flywheel is at target velocity (within given tolerance)
     * @param tolerance Velocity tolerance in ticks per second
     * @return true if at target velocity
     */
    public boolean isAtTargetVelocity(int tolerance) {
        return getVelocity() >= targetVelocity - tolerance;
    }

    /**
     * Adds telemetry data for debugging
     */
    public void addTelemetry() {
        telemetry.addData("Flywheel Velocity", "%.0f ticks/sec", getVelocity());
        telemetry.addData("Flywheel Target", "%d ticks/sec", targetVelocity);
        telemetry.addData("Flywheel Power", "%.2f", flywheelMotor.getPower());
    }
}
