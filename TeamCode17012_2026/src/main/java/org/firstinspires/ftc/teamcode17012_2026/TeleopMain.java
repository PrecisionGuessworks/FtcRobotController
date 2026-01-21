package org.firstinspires.ftc.teamcode17012_2026;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode17012_2026.Constants.ArtifactColor;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.CatapultSubsystem;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.ColorSensorSubsystem;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.FootSubsystem;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.HopperSubsystem;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.LimelightSubsystem;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.PinpointOdometry;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.ScoringSequence;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.ShooterSubsystem;

/**
 * TeleOp Main - 2026 Season
 * Complete TeleOp mode with all subsystems integrated
 *
 * DRIVER (Gamepad1) Controls:
 * - Left Stick: Strafe (X/Y)
 * - Right Stick X: Rotate
 * - Square Button: Reset heading (field-centric)
 * - Cross (A): Lower foot (deploy for stability)
 * - Circle (B): Raise foot (stow)
 * - Right Bumper: Pivot catapult up
 * - Right Trigger: Pivot catapult down
 *
 * OPERATOR (Gamepad2) Controls:
 * - Triangle: Start Intake
 * - Cross: Outtake (reverse intake)
 * - Circle: Stop Intake
 * - Square: Auto-Launch Sequence
 * - Left Bumper: Manual Shoot Green
 * - Right Bumper: Manual Shoot Purple
 * - D-Pad Up: Scan for obelisk tag
 * - D-Pad Down: Update shooter velocity from goal distance
 */
@TeleOp(name="TeleOp Main 2026", group="Competition")
public class TeleopMain extends OpMode {

    // Subsystems
    private MecanumDrivetrain drivetrain;
    private ColorSensorSubsystem colorSensor;
    private IntakeSubsystem intake;
    private ShooterSubsystem shooter;
    private HopperSubsystem hopper;
    private LimelightSubsystem limelight;
    private PinpointOdometry odometry;
    private ScoringSequence scoringSequence;
    private CatapultSubsystem catapult;
    private FootSubsystem foot;

    // State tracking
    private ElapsedTime runtime = new ElapsedTime();
    private boolean lastSquareButton = false; // For driver heading reset
    private boolean lastTriangle = false;
    private boolean lastCross = false;
    private boolean lastCircle = false;
    private boolean lastSquare = false;
    private boolean lastLeftBumper = false;
    private boolean lastRightBumper = false;
    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;

    @Override
    public void init() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        try {
            // Initialize all subsystems
            colorSensor = new ColorSensorSubsystem(hardwareMap, telemetry);
            drivetrain = new MecanumDrivetrain(hardwareMap, telemetry);
            intake = new IntakeSubsystem(hardwareMap, colorSensor, telemetry);
            shooter = new ShooterSubsystem(hardwareMap, telemetry);
            hopper = new HopperSubsystem(hardwareMap, telemetry);
            limelight = new LimelightSubsystem(hardwareMap, telemetry);
            odometry = new PinpointOdometry(hardwareMap, telemetry);
            scoringSequence = new ScoringSequence(shooter, hopper, limelight, telemetry);
            catapult = new CatapultSubsystem(hardwareMap, telemetry);
            foot = new FootSubsystem(hardwareMap, telemetry);

            telemetry.addData("Status", "Initialized - Ready to start!");
            telemetry.addData("", "");
            telemetry.addData("Driver", "Left stick=strafe, Right stick=rotate, Square=reset heading");
            telemetry.addData("Driver", "Cross=foot down, Circle=foot up, RB=catapult up, RT=catapult down");
            telemetry.addData("Operator", "Triangle=intake, Cross=outtake, Square=auto-launch");
            telemetry.addData("", "LB/RB=manual shoot Green/Purple");

        } catch (Exception e) {
            telemetry.addData("INIT ERROR", e.getMessage());
        }

        telemetry.update();
    }

    @Override
    public void start() {
        runtime.reset();

        // Scan for obelisk tag at start of teleop
        telemetry.addData("Scanning", "Looking for obelisk AprilTag...");
        telemetry.update();

        limelight.scanForObeliskTag(2000); // 2 second scan

        telemetry.addData("Status", "TeleOp Started!");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Update subsystems that need periodic updates
        limelight.update();
        odometry.update();
        scoringSequence.update();

        // === DRIVER CONTROLS (Gamepad1) ===
        handleDriverControls();

        // === OPERATOR CONTROLS (Gamepad2) ===
        handleOperatorControls();

        // Update intake flipper based on color sensors
        intake.updateFlipper();

        // Update hopper state
        hopper.updateFeedState();

        // Display telemetry
        updateTelemetry();
    }

    private void handleDriverControls() {
        // Field-centric drive
        double strafe = gamepad1.left_stick_x;
        double forward = -gamepad1.left_stick_y; // Inverted
        double rotate = gamepad1.right_stick_x;

        drivetrain.drive(strafe, forward, rotate);

        // Reset heading (Square button - rising edge)
        if (gamepad1.square && !lastSquareButton) {
            drivetrain.resetHeading();
            telemetry.addData("Heading", "RESET");
        }
        lastSquareButton = gamepad1.square;

        // === FOOT CONTROLS ===
        // Cross (A) = lower foot, Circle (B) = raise foot
        // When both pressed, prioritize raising (safer default)
        boolean footDownButton = gamepad1.cross;
        boolean footUpButton = gamepad1.circle;
        if (footDownButton && footUpButton) {
            footDownButton = false; // Prioritize up when both pressed
        }

        if (footDownButton) {
            foot.lower();
        } else if (footUpButton) {
            foot.raise();
        } else {
            foot.stop();
        }

        // === CATAPULT CONTROLS ===
        // Right bumper = pivot up, Right trigger = pivot down
        // When both pressed, prioritize down (safer default)
        boolean catapultUpButton = gamepad1.right_bumper;
        boolean catapultDownButton = gamepad1.right_trigger > 0.2;
        if (catapultUpButton && catapultDownButton) {
            catapultUpButton = false; // Prioritize down when both pressed
        }

        if (catapultUpButton) {
            catapult.pivotUp();
        } else if (catapultDownButton) {
            catapult.pivotDown();
        } else {
            catapult.hold();
        }
    }

    private void handleOperatorControls() {
        // === INTAKE CONTROLS ===

        // Triangle - Start Intake
        if (gamepad2.triangle && !lastTriangle) {
            intake.startIntake();
        }
        lastTriangle = gamepad2.triangle;

        // Cross - Outtake
        if (gamepad2.cross && !lastCross) {
            intake.startOuttake();
        }
        lastCross = gamepad2.cross;

        // Circle - Stop Intake
        if (gamepad2.circle && !lastCircle) {
            intake.stop();
        }
        lastCircle = gamepad2.circle;

        // === SHOOTING CONTROLS ===

        // Square - Auto-Launch Sequence
        if (gamepad2.square && !lastSquare) {
            if (!scoringSequence.isRunning()) {
                scoringSequence.startAutoLaunch();
            } else {
                // Cancel if already running
                scoringSequence.cancel();
            }
        }
        lastSquare = gamepad2.square;

        // Left Bumper - Manual Shoot Green
        if (gamepad2.left_bumper && !lastLeftBumper) {
            manualShoot(ArtifactColor.GREEN);
        }
        lastLeftBumper = gamepad2.left_bumper;

        // Right Bumper - Manual Shoot Purple
        if (gamepad2.right_bumper && !lastRightBumper) {
            manualShoot(ArtifactColor.PURPLE);
        }
        lastRightBumper = gamepad2.right_bumper;

        // === LIMELIGHT CONTROLS ===

        // D-Pad Up - Scan for obelisk tag
        if (gamepad2.dpad_up && !lastDpadUp) {
            limelight.scanForObeliskTag(3000);
        }
        lastDpadUp = gamepad2.dpad_up;

        // D-Pad Down - Update shooter velocity from goal distance
        if (gamepad2.dpad_down && !lastDpadDown) {
            scoringSequence.updateShooterVelocity();
        }
        lastDpadDown = gamepad2.dpad_down;
    }

    /**
     * Manual shooting helper - spins up shooter and feeds one ball
     * @param color Color to shoot (GREEN or PURPLE)
     */
    private void manualShoot(ArtifactColor color) {
        // Update shooter velocity based on goal distance
        scoringSequence.updateShooterVelocity();

        // Spin up shooter
        if (!shooter.isSpinningUp() && !shooter.isReadyToShoot()) {
            shooter.setVelocityForDistance(60); // Default to medium range
        }

        // If shooter is ready, feed the ball
        if (shooter.isReadyToShoot()) {
            hopper.feed(color);
        }
    }

    private void updateTelemetry() {
        telemetry.addData("=== STATUS ===", "");
        telemetry.addData("Runtime", "%.1f sec", runtime.seconds());

        // Drivetrain
        telemetry.addData("=== DRIVETRAIN ===", "");
        drivetrain.addTelemetry();

        // Intake
        telemetry.addData("=== INTAKE ===", "");
        intake.addTelemetry();
        colorSensor.addTelemetry();

        // Shooter & Hopper
        telemetry.addData("=== SHOOTER ===", "");
        shooter.addTelemetry();
        hopper.addTelemetry();

        // Scoring Sequence
        telemetry.addData("=== AUTO-LAUNCH ===", "");
        scoringSequence.addTelemetry();

        // Catapult
        telemetry.addData("=== CATAPULT ===", "");
        catapult.addTelemetry();

        // Foot
        telemetry.addData("=== FOOT ===", "");
        foot.addTelemetry();

        // Limelight
        telemetry.addData("=== LIMELIGHT ===", "");
        limelight.addTelemetry();

        // Odometry (minimal - just position)
        telemetry.addData("=== ODOMETRY ===", "");
        telemetry.addData("Position", "X:%.1f Y:%.1f", odometry.getX(), odometry.getY());

        telemetry.update();
    }

    @Override
    public void stop() {
        // Stop all subsystems
        drivetrain.stop();
        intake.stop();
        shooter.stop();
        hopper.stopAll();
        limelight.stop();
        scoringSequence.cancel();
        catapult.stop();
        foot.stop();

        telemetry.addData("Status", "TeleOp Stopped");
        telemetry.addData("Final Runtime", "%.1f seconds", runtime.seconds());
        telemetry.update();
    }
}
