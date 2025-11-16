package org.firstinspires.ftc.teamcode17012_2026.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode17012_2026.Constants;

/**
 * PinpointOdometry - Wrapper for GoBilda Pinpoint Odometry Computer
 * Provides position tracking and heading information for autonomous navigation
 *
 * NOTE: This uses the GoBildaPinpointDriver library which must be included in your project
 * Download from: https://github.com/VulcanRobotics8375/goBILDA-Pinpoint-SDK
 */
public class PinpointOdometry {

    private GoBildaPinpointDriver pinpoint;
    private Telemetry telemetry;

    // Position tracking
    private double offsetX = Constants.OdometryConstants.PINPOINT_OFFSET_X;
    private double offsetY = Constants.OdometryConstants.PINPOINT_OFFSET_Y;

    public PinpointOdometry(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        try {
            // Initialize Pinpoint device
            pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, Constants.VisionHardware.PINPOINT);

            // Set offsets (distance from robot center to Pinpoint sensor)
            pinpoint.setOffsets(offsetX, offsetY);

            // Set encoder directions (adjust based on your setup)
            pinpoint.setEncoderDirections(
                GoBildaPinpointDriver.EncoderDirection.FORWARD,
                GoBildaPinpointDriver.EncoderDirection.FORWARD
            );

            // Reset position to origin
            pinpoint.resetPosAndIMU();

        } catch (Exception e) {
            telemetry.addData("Pinpoint Error", "Failed to initialize: " + e.getMessage());
            telemetry.update();
        }
    }

    /**
     * Updates the Pinpoint sensor readings
     * Should be called periodically (e.g., in loop)
     */
    public void update() {
        if (pinpoint != null) {
            pinpoint.update();
        }
    }

    /**
     * Gets the current robot position
     * @return Pose2D with X, Y coordinates and heading
     */
    public Pose2D getPosition() {
        if (pinpoint == null) {
            return new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.RADIANS, 0);
        }
        return pinpoint.getPosition();
    }

    /**
     * Gets the X position in inches
     * @return X coordinate in inches
     */
    public double getX() {
        return getPosition().getX(DistanceUnit.INCH);
    }

    /**
     * Gets the Y position in inches
     * @return Y coordinate in inches
     */
    public double getY() {
        return getPosition().getY(DistanceUnit.INCH);
    }

    /**
     * Gets the heading in radians
     * @return Heading in radians
     */
    public double getHeading() {
        return getPosition().getHeading(AngleUnit.RADIANS);
    }

    /**
     * Gets the heading in degrees
     * @return Heading in degrees
     */
    public double getHeadingDegrees() {
        return getPosition().getHeading(AngleUnit.DEGREES);
    }

    /**
     * Resets the position and IMU to origin (0, 0, 0)
     */
    public void resetPosition() {
        if (pinpoint != null) {
            pinpoint.resetPosAndIMU();
        }
    }

    /**
     * Sets a specific position (useful for starting positions in auto)
     * @param x X coordinate in inches
     * @param y Y coordinate in inches
     * @param headingDegrees Heading in degrees
     */
    public void setPosition(double x, double y, double headingDegrees) {
        if (pinpoint != null) {
            pinpoint.setPosition(new Pose2D(
                DistanceUnit.INCH, x, y,
                AngleUnit.DEGREES, headingDegrees
            ));
        }
    }

    /**
     * Gets the velocity of the robot
     * @return Pose2D with velocity components
     */
    public Pose2D getVelocity() {
        if (pinpoint == null) {
            return new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.RADIANS, 0);
        }
        return pinpoint.getVelocity();
    }

    /**
     * Gets the X velocity in inches/sec
     * @return X velocity
     */
    public double getVelocityX() {
        return getVelocity().getX(DistanceUnit.INCH);
    }

    /**
     * Gets the Y velocity in inches/sec
     * @return Y velocity
     */
    public double getVelocityY() {
        return getVelocity().getY(DistanceUnit.INCH);
    }

    /**
     * Calculates distance to a target point
     * @param targetX Target X coordinate in inches
     * @param targetY Target Y coordinate in inches
     * @return Distance in inches
     */
    public double distanceToPoint(double targetX, double targetY) {
        double currentX = getX();
        double currentY = getY();
        double dx = targetX - currentX;
        double dy = targetY - currentY;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Calculates angle to a target point
     * @param targetX Target X coordinate in inches
     * @param targetY Target Y coordinate in inches
     * @return Angle in radians
     */
    public double angleToPoint(double targetX, double targetY) {
        double currentX = getX();
        double currentY = getY();
        return Math.atan2(targetY - currentY, targetX - currentX);
    }

    /**
     * Adds telemetry data for debugging
     */
    public void addTelemetry() {
        if (pinpoint == null) {
            telemetry.addData("Pinpoint", "NOT INITIALIZED");
            return;
        }

        Pose2D pos = getPosition();
        Pose2D vel = getVelocity();

        telemetry.addData("Pinpoint Position", "X: %.2f, Y: %.2f",
            pos.getX(DistanceUnit.INCH),
            pos.getY(DistanceUnit.INCH));
        telemetry.addData("Pinpoint Heading", "%.1f degrees",
            pos.getHeading(AngleUnit.DEGREES));
        telemetry.addData("Pinpoint Velocity", "X: %.2f, Y: %.2f",
            vel.getX(DistanceUnit.INCH),
            vel.getY(DistanceUnit.INCH));
    }

    /**
     * Checks if Pinpoint is initialized and working
     * @return True if Pinpoint is ready
     */
    public boolean isInitialized() {
        return pinpoint != null;
    }

    // Inner class placeholder for GoBilda Pinpoint Driver
    // Replace this with the actual import when you add the library
    private static class GoBildaPinpointDriver {
        // This is a placeholder - you'll need to add the actual GoBilda library
        // Download from: https://github.com/VulcanRobotics8375/goBILDA-Pinpoint-SDK

        public enum EncoderDirection {
            FORWARD, REVERSED
        }

        public void setOffsets(double x, double y) {}
        public void setEncoderDirections(EncoderDirection dir1, EncoderDirection dir2) {}
        public void resetPosAndIMU() {}
        public void update() {}
        public Pose2D getPosition() { return new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.RADIANS, 0); }
        public void setPosition(Pose2D pose) {}
        public Pose2D getVelocity() { return new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.RADIANS, 0); }
    }
}
