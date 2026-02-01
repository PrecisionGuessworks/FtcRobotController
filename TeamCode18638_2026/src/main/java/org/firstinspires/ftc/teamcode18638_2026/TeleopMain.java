package org.firstinspires.ftc.teamcode18638_2026;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode18638_2026.Subsystems.FeederSubsystem;
import org.firstinspires.ftc.teamcode18638_2026.Subsystems.FlywheelSubsystem;
import org.firstinspires.ftc.teamcode18638_2026.Subsystems.MecanumDrivetrain;

/**
 * TeleOp Main - 2026 Season (Team 18638)
 * REV Starter Bot with Mecanum Drivetrain
 *
 * DRIVER (Gamepad1) Controls:
 * - Left Stick: Strafe (X/Y movement)
 * - Right Stick X: Rotate
 * - D-Pad Up: Reset heading (field-centric)
 *
 * FLYWHEEL CONTROLS:
 * - Circle: Spin flywheel to bank velocity (near shots)
 * - Square: Spin flywheel to max velocity
 * - Options: Reverse flywheel (manual)
 *
 * AUTO SHOT CONTROLS (flywheel + feeder):
 * - Right Bumper: Bank shot auto (near)
 * - Left Bumper: Far shot auto
 *
 * MANUAL CONTROLS:
 * - Cross: Feeder forward (manual)
 * - Triangle: Feeder reverse (manual)
 */
@TeleOp(name="TeleOp Main 2026", group="Competition")
public class TeleopMain extends OpMode {

    // Subsystems
    private MecanumDrivetrain drivetrain;
    private FlywheelSubsystem flywheel;
    private FeederSubsystem feeder;

    // State tracking
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime autoShotReleaseTimer = new ElapsedTime();
    private boolean lastSquareButton = false;

    // Grace period (seconds) to keep flywheel spinning after releasing auto-shot button
    private static final double AUTO_SHOT_GRACE_PERIOD = 0.5;

    @Override
    public void init() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        try {
            // Initialize all subsystems
            drivetrain = new MecanumDrivetrain(hardwareMap, telemetry);
            flywheel = new FlywheelSubsystem(hardwareMap, telemetry);
            feeder = new FeederSubsystem(hardwareMap, telemetry);

            telemetry.addData("Status", "Initialized - Ready to start!");
            telemetry.addData("", "");
            telemetry.addData("Drive", "Left stick=strafe, Right stick=rotate");
            telemetry.addData("Flywheel", "Circle=bank, Square=max, Options=reverse");
            telemetry.addData("Auto Shot", "RB=bank shot, LB=far shot");
            telemetry.addData("Manual", "Cross/Triangle=feeder");

        } catch (Exception e) {
            telemetry.addData("INIT ERROR", e.getMessage());
        }

        telemetry.update();
    }

    @Override
    public void start() {
        runtime.reset();
        telemetry.addData("Status", "TeleOp Started!");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Handle all controls
        handleDriveControls();
        handleFlywheelControls();

        // Display telemetry
        updateTelemetry();
    }

    private void handleDriveControls() {
        // Mecanum drive
        double strafe = gamepad1.left_stick_x;
        double forward = -gamepad1.left_stick_y; // Inverted
        double rotate = gamepad1.right_stick_x;

        drivetrain.drive(strafe, forward, rotate);

        // Reset heading (D-Pad Up - rising edge)
        if (gamepad1.dpad_up && !lastSquareButton) {
            drivetrain.resetHeading();
            telemetry.addData("Heading", "RESET");
        }
        lastSquareButton = gamepad1.dpad_up;
    }

    private void handleFlywheelControls() {
        // Reset grace period timer while auto-shot buttons are held
        if (gamepad1.left_bumper || gamepad1.right_bumper) {
            autoShotReleaseTimer.reset();
        }

        // Priority order for flywheel controls (matching REV Starter Bot logic)
        if (gamepad1.options) {
            // Manual reverse flywheel
            flywheel.reverse();
            feeder.stop();

        } else if (gamepad1.left_bumper) {
            // Far shot auto - flywheel + feeder when ready
            farShotAuto();

        } else if (gamepad1.right_bumper) {
            // Bank shot auto - flywheel + feeder when ready
            bankShotAuto();

        } else if (gamepad1.circle) {
            // Spin flywheel to bank velocity only
            flywheel.setBankVelocity();
            handleManualFeeder();

        } else if (gamepad1.square) {
            // Spin flywheel to max velocity only
            flywheel.setMaxVelocity();
            handleManualFeeder();

        } else if (autoShotReleaseTimer.seconds() < AUTO_SHOT_GRACE_PERIOD) {
            // Grace period: keep flywheel spinning after auto-shot release
            // so a quick re-press doesn't require full spin-up
            handleManualFeeder();

        } else {
            // No flywheel button pressed - stop flywheel, handle manual controls
            flywheel.stop();
            handleManualFeeder();
        }
    }

    /**
     * Handles manual feeder controls when not in auto mode
     */
    private void handleManualFeeder() {
        // Manual feeder control
        if (gamepad1.cross) {
            feeder.feedManual();
        } else if (gamepad1.triangle) {
            feeder.reverse();
        } else {
            feeder.stop();
        }
    }

    /**
     * Bank shot auto - near shots
     * Spins up flywheel, feeds when flywheel is ready
     */
    private void bankShotAuto() {
        flywheel.setBankVelocity();

        if (flywheel.isReadyForBankShot()) {
            feeder.feed();
        } else {
            feeder.stop();
        }
    }

    /**
     * Far shot auto - far shots
     * Spins up flywheel, feeds when flywheel is ready
     */
    private void farShotAuto() {
        flywheel.setFarVelocity();

        if (flywheel.isReadyForFarShot()) {
            feeder.feed();
        } else {
            feeder.stop();
        }
    }

    private void updateTelemetry() {
        telemetry.addData("=== STATUS ===", "");
        telemetry.addData("Runtime", "%.1f sec", runtime.seconds());

        // Drivetrain
        telemetry.addData("=== DRIVETRAIN ===", "");
        drivetrain.addTelemetry();

        // Flywheel
        telemetry.addData("=== FLYWHEEL ===", "");
        flywheel.addTelemetry();

        // Feeder
        telemetry.addData("=== FEEDER ===", "");
        feeder.addTelemetry();

        telemetry.update();
    }

    @Override
    public void stop() {
        // Stop all subsystems
        drivetrain.stop();
        flywheel.stop();
        feeder.stop();

        telemetry.addData("Status", "TeleOp Stopped");
        telemetry.addData("Final Runtime", "%.1f seconds", runtime.seconds());
        telemetry.update();
    }
}
