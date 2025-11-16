package org.firstinspires.ftc.teamcode17012_2026;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode17012_2026.Constants.ScoringPattern;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.ColorSensorSubsystem;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.HopperSubsystem;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.LimelightSubsystem;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.PinpointOdometry;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.ScoringSequence;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.ShooterSubsystem;

/**
 * AutonomousMain - Template autonomous program for 2026 season
 *
 * Basic autonomous sequence:
 * 1. Scan for obelisk AprilTag to get scoring pattern
 * 2. Navigate to shooting position
 * 3. Shoot 3 artifacts in correct order
 * 4. (Optional) Navigate to artifact pickup location
 * 5. (Optional) Intake new artifacts
 * 6. (Optional) Return to shooting position and shoot again
 *
 * This is a TEMPLATE - customize for your specific strategy!
 */
@Autonomous(name="Autonomous Main 2026", group="Competition")
public class AutonomousMain extends LinearOpMode {

    // Subsystems
    private MecanumDrivetrain drivetrain;
    private ColorSensorSubsystem colorSensor;
    private IntakeSubsystem intake;
    private ShooterSubsystem shooter;
    private HopperSubsystem hopper;
    private LimelightSubsystem limelight;
    private PinpointOdometry odometry;
    private ScoringSequence scoringSequence;

    // Timing
    private ElapsedTime runtime = new ElapsedTime();

    // CUSTOMIZE THESE POSITIONS FOR YOUR FIELD SETUP
    private static final double SHOOTING_X = 48.0;  // X position for shooting (inches)
    private static final double SHOOTING_Y = 24.0;  // Y position for shooting (inches)
    private static final double PICKUP_X = 72.0;    // X position for artifact pickup
    private static final double PICKUP_Y = 12.0;    // Y position for artifact pickup

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize all subsystems
        initializeSubsystems();

        // Wait for start
        telemetry.addData("Status", "Ready to start!");
        telemetry.addData("", "Waiting for start command...");
        telemetry.update();

        waitForStart();
        runtime.reset();

        if (opModeIsActive()) {
            // Step 1: Scan for obelisk tag
            ScoringPattern pattern = scanObeliskTag();

            // Step 2: Navigate to shooting position
            navigateToShootingPosition();

            // Step 3: Shoot artifacts in correct order
            shootArtifacts(pattern);

            // OPTIONAL: Add more steps here for your specific strategy
            // - Navigate to pickup location
            // - Intake more artifacts
            // - Return to shooting position
            // - Shoot again

            telemetry.addData("Status", "Autonomous Complete!");
            telemetry.addData("Runtime", "%.1f seconds", runtime.seconds());
            telemetry.update();
        }
    }

    private void initializeSubsystems() {
        telemetry.addData("Status", "Initializing subsystems...");
        telemetry.update();

        colorSensor = new ColorSensorSubsystem(hardwareMap, telemetry);
        drivetrain = new MecanumDrivetrain(hardwareMap, telemetry);
        intake = new IntakeSubsystem(hardwareMap, colorSensor, telemetry);
        shooter = new ShooterSubsystem(hardwareMap, telemetry);
        hopper = new HopperSubsystem(hardwareMap, telemetry);
        limelight = new LimelightSubsystem(hardwareMap, telemetry);
        odometry = new PinpointOdometry(hardwareMap, telemetry);
        scoringSequence = new ScoringSequence(shooter, hopper, limelight, telemetry);

        // Set starting position (customize based on your starting location)
        odometry.setPosition(0, 0, 0);

        telemetry.addData("Status", "Initialization complete");
        telemetry.update();
    }

    private ScoringPattern scanObeliskTag() {
        telemetry.addData("Step 1", "Scanning for obelisk tag...");
        telemetry.update();

        ScoringPattern pattern = limelight.scanForObeliskTag(5000); // 5 second timeout

        if (pattern == ScoringPattern.UNKNOWN) {
            telemetry.addData("Warning", "Obelisk tag not found! Using default pattern.");
            pattern = ScoringPattern.PURPLE_GREEN_PURPLE; // Default fallback
        }

        telemetry.addData("Scoring Pattern", pattern.toString());
        telemetry.update();
        sleep(500);

        return pattern;
    }

    private void navigateToShootingPosition() {
        telemetry.addData("Step 2", "Navigating to shooting position...");
        telemetry.update();

        // Simple navigation - drive to position using odometry
        driveToPose(SHOOTING_X, SHOOTING_Y, 0);

        telemetry.addData("Step 2", "Arrived at shooting position");
        telemetry.update();
        sleep(250);
    }

    private void shootArtifacts(ScoringPattern pattern) {
        telemetry.addData("Step 3", "Shooting artifacts...");
        telemetry.update();

        // Update shooter velocity based on goal distance
        scoringSequence.updateShooterVelocity();

        // Start auto-launch sequence
        scoringSequence.startAutoLaunch(pattern);

        // Wait for sequence to complete
        while (opModeIsActive() && !scoringSequence.isComplete()) {
            odometry.update();
            limelight.update();
            scoringSequence.update();

            telemetry.addData("Shooting", "Shot %d / %d",
                scoringSequence.getShotsFired(),
                scoringSequence.getTotalShots());
            telemetry.update();

            sleep(50);
        }

        telemetry.addData("Step 3", "All artifacts shot!");
        telemetry.update();
        sleep(500);
    }

    /**
     * Drives to a target pose using odometry
     * Simple P controller - you can improve this with PID or more advanced path following
     *
     * @param targetX Target X position in inches
     * @param targetY Target Y position in inches
     * @param targetHeading Target heading in degrees
     */
    private void driveToPose(double targetX, double targetY, double targetHeading) {
        double positionTolerance = 2.0; // inches
        double headingTolerance = 5.0;  // degrees

        ElapsedTime timeout = new ElapsedTime();
        double timeoutSec = 5.0;

        while (opModeIsActive() && timeout.seconds() < timeoutSec) {
            odometry.update();

            double currentX = odometry.getX();
            double currentY = odometry.getY();
            double currentHeading = odometry.getHeadingDegrees();

            // Calculate errors
            double errorX = targetX - currentX;
            double errorY = targetY - currentY;
            double errorHeading = targetHeading - currentHeading;

            // Normalize heading error to -180 to 180
            while (errorHeading > 180) errorHeading -= 360;
            while (errorHeading < -180) errorHeading += 360;

            // Check if we've arrived
            double distance = Math.sqrt(errorX * errorX + errorY * errorY);
            if (distance < positionTolerance && Math.abs(errorHeading) < headingTolerance) {
                break;
            }

            // Simple proportional control
            double kP = 0.05; // Tune this value
            double kPHeading = 0.02; // Tune this value

            double driveX = errorX * kP;
            double driveY = errorY * kP;
            double driveRotation = errorHeading * kPHeading;

            // Limit speeds
            driveX = Math.max(-0.5, Math.min(0.5, driveX));
            driveY = Math.max(-0.5, Math.min(0.5, driveY));
            driveRotation = Math.max(-0.3, Math.min(0.3, driveRotation));

            // Drive
            drivetrain.setFieldCentric(false); // Use robot-centric for simple control
            drivetrain.drive(driveX, driveY, driveRotation);

            telemetry.addData("Target", "X:%.1f Y:%.1f", targetX, targetY);
            telemetry.addData("Current", "X:%.1f Y:%.1f", currentX, currentY);
            telemetry.addData("Error", "%.1f inches", distance);
            telemetry.update();

            sleep(20);
        }

        // Stop
        drivetrain.stop();
    }
}
