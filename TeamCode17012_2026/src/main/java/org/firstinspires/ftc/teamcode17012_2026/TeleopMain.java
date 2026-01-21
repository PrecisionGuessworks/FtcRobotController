package org.firstinspires.ftc.teamcode17012_2026;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode17012_2026.Subsystems.MecanumDrivetrain;

/**
 * TeleOp Main - 2026 Season
 *
 * DRIVER (Gamepad1) Controls:
 * - Left Stick: Strafe (X/Y)
 * - Right Stick X: Rotate
 * - Square Button: Reset heading (field-centric)
 *
 * OPERATOR (Gamepad2) Controls:
 * - (To be developed)
 */
@TeleOp(name="TeleOp Main 2026", group="Competition")
public class TeleopMain extends OpMode {

    // Subsystems
    private MecanumDrivetrain drivetrain;

    // State tracking
    private ElapsedTime runtime = new ElapsedTime();
    private boolean lastSquareButton = false; // For driver heading reset

    @Override
    public void init() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        try {
            // Initialize subsystems
            drivetrain = new MecanumDrivetrain(hardwareMap, telemetry);

            telemetry.addData("Status", "Initialized - Ready to start!");
            telemetry.addData("", "");
            telemetry.addData("Driver", "Left stick=strafe, Right stick=rotate, Square=reset heading");

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
        // === DRIVER CONTROLS (Gamepad1) ===
        handleDriverControls();

        // === OPERATOR CONTROLS (Gamepad2) ===
        // TODO: Add operator controls

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
    }

    private void updateTelemetry() {
        telemetry.addData("=== STATUS ===", "");
        telemetry.addData("Runtime", "%.1f sec", runtime.seconds());

        // Drivetrain
        telemetry.addData("=== DRIVETRAIN ===", "");
        drivetrain.addTelemetry();

        telemetry.update();
    }

    @Override
    public void stop() {
        // Stop all subsystems
        drivetrain.stop();

        telemetry.addData("Status", "TeleOp Stopped");
        telemetry.addData("Final Runtime", "%.1f seconds", runtime.seconds());
        telemetry.update();
    }
}
