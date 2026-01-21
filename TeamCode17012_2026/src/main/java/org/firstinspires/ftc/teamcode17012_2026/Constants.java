package org.firstinspires.ftc.teamcode17012_2026;

/**
 * Constants file for FTC Team 17012 - 2026 Season
 * Contains all hardware configuration names and tunable parameters
 */
public class Constants {

    // ==================== HARDWARE CONFIGURATION NAMES ====================

    public static class DrivetrainHardware {
        public static final String FRONT_LEFT_MOTOR = "front_left_drive";
        public static final String FRONT_RIGHT_MOTOR = "front_right_drive";
        public static final String BACK_LEFT_MOTOR = "back_left_drive";
        public static final String BACK_RIGHT_MOTOR = "back_right_drive";
        public static final String IMU = "imu";
    }

    public static class ShooterHardware {
        public static final String LEFT_MOTOR = "shooter_motor_left";
        public static final String RIGHT_MOTOR = "shooter_motor_right";
    }

    public static class IntakeHardware {
        public static final String INTAKE_MOTOR = "intake_motor";
        public static final String FLIPPER_MOTOR = "flipper_motor";
    }

    public static class SensorHardware {
        public static final String COLOR_SENSOR_1 = "color_sensor_1";
        public static final String COLOR_SENSOR_2 = "color_sensor_2";
        public static final String COLOR_SENSOR_3 = "color_sensor_3";
    }

    public static class HopperHardware {
        public static final String GREEN_SERVO = "hopper_servo_green";
        public static final String PURPLE_SERVO = "hopper_servo_purple";
    }

    public static class KickstandHardware {
        public static final String LEFT_SERVO = "kickstand_servo_left";
        public static final String RIGHT_SERVO = "kickstand_servo_right";
    }

    public static class VisionHardware {
        public static final String LIMELIGHT = "limelight";
        public static final String PINPOINT = "pinpoint"; // GoBilda Pinpoint Odometry Computer
    }

    public static class CatapultHardware {
        public static final String CATAPULT_MOTOR_1 = "catapult1";
        public static final String CATAPULT_MOTOR_2 = "catapult2";
    }

    public static class FootHardware {
        public static final String FOOT_MOTOR = "foot";
    }

    // ==================== DRIVETRAIN CONSTANTS ====================

    public static class DrivetrainConstants {
        // Speed multipliers (0.0 to 1.0)
        public static final double NORMAL_SPEED = 0.8;
        public static final double TURBO_SPEED = 1.0;
        public static final double SLOW_SPEED = 0.4;

        // Field-centric drive settings
        public static final boolean USE_FIELD_CENTRIC = true;

        // Joystick deadzone
        public static final double JOYSTICK_DEADZONE = 0.1;
    }

    // ==================== SHOOTER CONSTANTS ====================

    public static class ShooterConstants {
        // Flywheel specifications
        public static final double FLYWHEEL_DIAMETER_MM = 72.0;
        public static final double FLYWHEEL_CIRCUMFERENCE_MM = Math.PI * FLYWHEEL_DIAMETER_MM;

        // Velocity control (in ticks per second - adjust based on your motor/encoder)
        // These are starting values - tune on the actual robot
        public static final double MIN_VELOCITY = 1500; // Minimum shooting velocity (close range ~2 feet)
        public static final double MAX_VELOCITY = 3000; // Maximum shooting velocity (far range ~10 feet)

        // Distance-based velocity calculation
        public static final double MIN_DISTANCE_INCHES = 24.0; // 2 feet
        public static final double MAX_DISTANCE_INCHES = 120.0; // 10 feet

        // Spin-up time
        public static final double SPINUP_TIME_MS = 250.0; // Time to reach target velocity

        // Velocity tolerance for "ready to shoot"
        public static final double VELOCITY_TOLERANCE = 50.0; // ticks/sec
    }

    // ==================== HOPPER CONSTANTS ====================

    public static class HopperConstants {
        // Servo positions/speeds for continuous rotation servos
        public static final double FEED_SPEED = 1.0; // Full speed forward to feed ball
        public static final double STOP_SPEED = 0.5; // Stopped position
        public static final double REVERSE_SPEED = 0.0; // Full reverse (if needed)

        // Timing for ball feeding
        public static final double FEED_TIME_MS = 500.0; // Time to feed one ball
        public static final double SHOT_DELAY_MS = 200.0; // Delay between shots in auto-launch
    }

    // ==================== INTAKE CONSTANTS ====================

    public static class IntakeConstants {
        // Intake motor power
        public static final double INTAKE_POWER = 0.8;
        public static final double OUTTAKE_POWER = -0.8;

        // Flipper motor power/direction
        public static final double FLIPPER_LEFT_POWER = -0.6; // CCW for green
        public static final double FLIPPER_RIGHT_POWER = 0.6; // CW for purple
        public static final double FLIPPER_STOP_POWER = 0.0;
    }

    // ==================== COLOR SENSOR CONSTANTS ====================

    public static class ColorSensorConstants {
        // HSV thresholds for color detection (tune these on actual robot)
        // Green artifact thresholds
        public static final double GREEN_HUE_MIN = 80.0;
        public static final double GREEN_HUE_MAX = 160.0;
        public static final double GREEN_SAT_MIN = 0.3;

        // Purple artifact thresholds
        public static final double PURPLE_HUE_MIN = 250.0;
        public static final double PURPLE_HUE_MAX = 310.0;
        public static final double PURPLE_SAT_MIN = 0.3;

        // Minimum value threshold (brightness) to consider a valid reading
        public static final double MIN_VALUE = 0.2;

        // Voting logic: minimum sensors that must agree
        public static final int MIN_SENSORS_REQUIRED = 2; // 2 out of 3
    }

    // ==================== LIMELIGHT CONSTANTS ====================

    public static class LimelightConstants {
        // AprilTag IDs
        public static final int OBELISK_TAG_21 = 21; // Green-Purple-Purple
        public static final int OBELISK_TAG_22 = 22; // Purple-Green-Purple
        public static final int OBELISK_TAG_23 = 23; // Purple-Purple-Green
        public static final int BLUE_GOAL_TAG = 20;
        public static final int RED_GOAL_TAG = 24;

        // Pipeline settings
        public static final int APRILTAG_PIPELINE = 0; // Use built-in AprilTag pipeline

        // Camera settings for Limelight 3A
        // Balanced resolution/fps: 960x720 @ 30fps is a good middle ground
        // These may need to be set via Limelight web interface

        // Distance calculation - will use Limelight's built-in 3D positioning
        // Tag size in inches (FTC standard AprilTags are 2 inches)
        public static final double APRILTAG_SIZE_INCHES = 2.0;
    }

    // ==================== PINPOINT ODOMETRY CONSTANTS ====================

    public static class OdometryConstants {
        // These will depend on your Pinpoint mounting and calibration
        // Start with defaults from GoBilda documentation

        // Offset of Pinpoint from robot center (in inches)
        public static final double PINPOINT_OFFSET_X = 0.0; // Forward/backward from center
        public static final double PINPOINT_OFFSET_Y = 0.0; // Left/right from center

        // Encoder resolution (from GoBilda specs)
        public static final double TICKS_PER_MM = 1.0; // Update based on Pinpoint specs
    }

    // ==================== KICKSTAND CONSTANTS ====================

    public static class KickstandConstants {
        // Continuous rotation servo speeds
        public static final double RAISE_SPEED = 1.0;
        public static final double LOWER_SPEED = -1.0;
        public static final double STOP_SPEED = 0.5;
    }

    // ==================== CATAPULT CONSTANTS ====================

    public static class CatapultConstants {
        // Catapult motor power settings
        public static final double UP_POWER = -1.0;    // Power to pivot up (launch position)
        public static final double DOWN_POWER = 1.0;   // Power to pivot down (stowed position)
        public static final double HOLD_POWER = 0.2;   // Feed-forward power to hold position
    }

    // ==================== FOOT CONSTANTS ====================

    public static class FootConstants {
        // Foot motor power settings
        public static final double UP_POWER = 1.0;     // Power to raise foot (stowed)
        public static final double DOWN_POWER = -0.85; // Power to lower foot (deployed)
    }

    // ==================== AUTONOMOUS CONSTANTS ====================

    public static class AutoConstants {
        // Path following tolerances
        public static final double POSITION_TOLERANCE_INCHES = 2.0;
        public static final double HEADING_TOLERANCE_DEGREES = 3.0;

        // Movement speeds
        public static final double AUTO_DRIVE_SPEED = 0.6;
        public static final double AUTO_TURN_SPEED = 0.4;

        // Timeout values
        public static final double MOVE_TIMEOUT_MS = 5000.0;
        public static final double TURN_TIMEOUT_MS = 3000.0;
    }

    // ==================== SCORING PATTERNS ====================

    public enum ArtifactColor {
        GREEN,
        PURPLE,
        UNKNOWN
    }

    public enum ScoringPattern {
        GREEN_PURPLE_PURPLE(new ArtifactColor[]{ArtifactColor.GREEN, ArtifactColor.PURPLE, ArtifactColor.PURPLE}),
        PURPLE_GREEN_PURPLE(new ArtifactColor[]{ArtifactColor.PURPLE, ArtifactColor.GREEN, ArtifactColor.PURPLE}),
        PURPLE_PURPLE_GREEN(new ArtifactColor[]{ArtifactColor.PURPLE, ArtifactColor.PURPLE, ArtifactColor.GREEN}),
        UNKNOWN(new ArtifactColor[]{ArtifactColor.UNKNOWN, ArtifactColor.UNKNOWN, ArtifactColor.UNKNOWN});

        private final ArtifactColor[] sequence;

        ScoringPattern(ArtifactColor[] sequence) {
            this.sequence = sequence;
        }

        public ArtifactColor[] getSequence() {
            return sequence;
        }

        public static ScoringPattern fromAprilTagId(int tagId) {
            switch (tagId) {
                case LimelightConstants.OBELISK_TAG_21:
                    return GREEN_PURPLE_PURPLE;
                case LimelightConstants.OBELISK_TAG_22:
                    return PURPLE_GREEN_PURPLE;
                case LimelightConstants.OBELISK_TAG_23:
                    return PURPLE_PURPLE_GREEN;
                default:
                    return UNKNOWN;
            }
        }
    }
}
