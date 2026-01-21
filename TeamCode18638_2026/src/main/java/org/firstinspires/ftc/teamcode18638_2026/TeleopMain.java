package org.firstinspires.ftc.teamcode18638_2026;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode18638_2026.Subsystems.AgitatorSubsystem;
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
 * - Square Button: Reset heading (field-centric)
 *
 * FLYWHEEL CONTROLS:
 * - Circle: Spin flywheel to bank velocity (near shots)
 * - Square: Spin flywheel to max velocity
 * - Options: Reverse flywheel (manual)
 *
 * AUTO SHOT CONTROLS (flywheel + feeder + agitator):
 * - Right Bumper: Bank shot auto (near)
 * - Left Bumper: Far shot auto
 *
 * MANUAL CONTROLS:
 * - Cross: Feeder forward (manual)
 * - Triangle: Feeder reverse (manual)
 * - D-Pad Left: Agitator forward
 * - D-Pad Right: Agitator reverse
 */
@TeleOp(name="TeleOp Main 2026", group="Competition")
public class TeleopMain extends OpMode {

    // Subsystems
    private MecanumDrivetrain drivetrain;
    private FlywheelSubsystem flywheel;
    private FeederSubsystem feeder;
    private AgitatorSubsystem agitator;

    // State tracking
    private ElapsedTime runtime = new ElapsedTime();
    private boolean lastSquareButton = false;

    @Override
    public void init() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        try {
            // Initialize all subsystems
            drivetrain = new MecanumDrivetrain(hardwareMap, telemetry);
            flywheel = new FlywheelSubsystem(hardwareMap, telemetry);
            feeder = new FeederSubsystem(hardwareMap, telemetry);
            agitator = new AgitatorSubsystem(hardwareMap, telemetry);

            telemetry.addData("Status", "Initialized - Ready to start!");
            telemetry.addData("", "");
            telemetry.addData("Drive", "Left stick=strafe, Right stick=rotate");
            telemetry.addData("Flywheel", "Circle=bank, Square=max, Options=reverse");
            telemetry.addData("Auto Shot", "RB=bank shot, LB=far shot");
            telemetry.addData("Manual", "Cross/Triangle=feeder, D-pad=agitator");

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

        // Reset heading (Square button on drive - use different button since square is flywheel)
        // Using touchpad or share button instead
        if (gamepad1.share && !lastSquareButton) {
            drivetrain.resetHeading();
            telemetry.addData("Heading", "RESET");
        }
        lastSquareButton = gamepad1.share;
    }

    private void handleFlywheelControls() {
        // Priority order for flywheel controls (matching REV Starter Bot logic)

        if (gamepad1.options) {
            // Manual reverse flywheel
            flywheel.reverse();
            feeder.stop();
            stopAgitatorIfNotManual();

        } else if (gamepad1.left_bumper) {
            // Far shot auto - flywheel + feeder + agitator when ready
            farShotAuto();

        } else if (gamepad1.right_bumper) {
            // Bank shot auto - flywheel + feeder + agitator when ready
            bankShotAuto();

        } else if (gamepad1.circle) {
            // Spin flywheel to bank velocity only
            flywheel.setBankVelocity();
            handleManualFeederAndAgitator();

        } else if (gamepad1.square) {
            // Spin flywheel to max velocity only
            flywheel.setMaxVelocity();
            handleManualFeederAndAgitator();

        } else {
            // No flywheel button pressed - stop flywheel, handle manual controls
            flywheel.stop();
            handleManualFeederAndAgitator();
        }
    }

    /**
     * Handles manual feeder and agitator controls when not in auto mode
     */
    private void handleManualFeederAndAgitator() {
        // Manual feeder control
        if (gamepad1.cross) {
            feeder.feedManual();
        } else if (gamepad1.triangle) {
            feeder.reverse();
        } else {
            feeder.stop();
        }

        // Manual agitator control
        if (gamepad1.dpad_left) {
            agitator.forward();
        } else if (gamepad1.dpad_right) {
            agitator.reverse();
        } else {
            agitator.stop();
        }
    }

    /**
     * Stops agitator only if not under manual control
     */
    private void stopAgitatorIfNotManual() {
        if (!gamepad1.dpad_left && !gamepad1.dpad_right) {
            agitator.stop();
        }
    }

    /**
     * Bank shot auto - near shots
     * Spins up flywheel, runs agitator, feeds when flywheel is ready
     */
    private void bankShotAuto() {
        flywheel.setBankVelocity();
        agitator.reverse(); // Agitator runs in reverse per sample code

        if (flywheel.isReadyForBankShot()) {
            feeder.feed();
        } else {
            feeder.stop();
        }
    }

    /**
     * Far shot auto - far shots
     * Spins up flywheel, runs agitator, feeds when flywheel is ready
     */
    private void farShotAuto() {
        flywheel.setFarVelocity();
        agitator.reverse(); // Agitator runs in reverse per sample code

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

        // Agitator
        telemetry.addData("=== AGITATOR ===", "");
        agitator.addTelemetry();

        telemetry.update();
    }

    @Override
    public void stop() {
        // Stop all subsystems
        drivetrain.stop();
        flywheel.stop();
        feeder.stop();
        agitator.stop();

        telemetry.addData("Status", "TeleOp Stopped");
        telemetry.addData("Final Runtime", "%.1f seconds", runtime.seconds());
        telemetry.update();
    }
}
