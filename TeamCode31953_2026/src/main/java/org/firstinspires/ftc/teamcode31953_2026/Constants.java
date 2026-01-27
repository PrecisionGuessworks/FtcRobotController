package org.firstinspires.ftc.teamcode31953_2026;

/**
 * Constants file for FTC Team 17012 - 2026 Season
 * Contains all hardware configuration names and tunable parameters
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

    public static class IntakeHardware {
        public static final String INTAKE_MOTOR = "intake";
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

    // ==================== INTAKE CONSTANTS ====================

    public static class IntakeConstants {
        // Intake motor power (matching sample code values)
        public static final double IN_POWER = 1.0;
        public static final double OUT_POWER = -0.9;
        public static final double OFF_POWER = 0.0;
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
}
