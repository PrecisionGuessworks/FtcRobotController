package org.firstinspires.ftc.teamcode18638_2026;

/**
 * Constants file for FTC Team 18638 - 2026 Season
 * Contains all hardware configuration names and tunable parameters
 * Based on REV Starter Bot with Mecanum drivetrain
 */
public class Constants {

    // ==================== HARDWARE CONFIGURATION NAMES ====================

    public static class DrivetrainHardware {
        public static final String FRONT_LEFT_MOTOR = "left_front_drive";
        public static final String FRONT_RIGHT_MOTOR = "right_front_drive";
        public static final String BACK_LEFT_MOTOR = "left_back_drive";
        public static final String BACK_RIGHT_MOTOR = "right_back_drive";
        public static final String IMU = "imu";
    }

    public static class FlywheelHardware {
        public static final String FLYWHEEL_MOTOR = "flywheel";
    }

    public static class FeederHardware {
        public static final String FEEDER_MOTOR = "coreHex";
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

    // ==================== FLYWHEEL CONSTANTS ====================

    public static class FlywheelConstants {
        // Velocity targets in ticks per second
        public static final int BANK_VELOCITY = 1300;    // Near/bank shot velocity
        public static final int FAR_VELOCITY = 1900;     // Far shot velocity
        public static final int MAX_VELOCITY = 2200;     // Maximum velocity

        // Velocity tolerance for "ready to shoot" check
        public static final int BANK_TOLERANCE = 50;     // Tolerance for bank shots
        public static final int FAR_TOLERANCE = 100;     // Tolerance for far shots

        // Manual control power
        public static final double REVERSE_POWER = -0.5;
    }

    // ==================== FEEDER CONSTANTS ====================

    public static class FeederConstants {
        // Feeder motor power
        public static final double FEED_POWER = 1.0;
        public static final double REVERSE_POWER = -0.5;
        public static final double MANUAL_POWER = 0.5;
    }

}
