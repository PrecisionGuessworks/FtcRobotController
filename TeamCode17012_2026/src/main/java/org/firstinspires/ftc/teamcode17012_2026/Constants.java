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
}
