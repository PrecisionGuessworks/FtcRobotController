package org.firstinspires.ftc.teamcode31953_2026;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode31953_2026.Subsystems.CatapultSubsystem;
import org.firstinspires.ftc.teamcode31953_2026.Subsystems.FootSubsystem;
import org.firstinspires.ftc.teamcode31953_2026.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode31953_2026.Subsystems.MecanumDrivetrain;

/**
 * TeleOp Main - 2026 Season
 * Complete TeleOp mode with all subsystems integrated
 *
 * DRIVER (Gamepad1) Controls:
 * - Left Stick: Strafe (X/Y movement)
 * - Right Stick X: Rotate
 * - Square Button: Reset heading (field-centric)
 * - Left Trigger: Intake in
 * - Left Bumper: Intake out (outtake)
 * - Cross (A): Lower foot (deploy for stability)
 * - Circle (B): Raise foot (stow)
 * - Right Bumper: Pivot catapult up
 * - Right Trigger: Pivot catapult down
 */
@TeleOp(name="TeleOp Main 2026", group="Competition")
public class TeleopMain extends OpMode {

    // Subsystems
    private MecanumDrivetrain drivetrain;
    private IntakeSubsystem intake;
    private CatapultSubsystem catapult;
    private FootSubsystem foot;

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
            intake = new IntakeSubsystem(hardwareMap, telemetry);
            catapult = new CatapultSubsystem(hardwareMap, telemetry);
            foot = new FootSubsystem(hardwareMap, telemetry);

            telemetry.addData("Status", "Initialized - Ready to start!");
            telemetry.addData("", "");
            telemetry.addData("Drive", "Left stick=strafe, Right stick=rotate");
            telemetry.addData("Drive", "Square=reset heading");
            telemetry.addData("Intake", "LT=in, LB=out");
            telemetry.addData("Foot", "Cross=down, Circle=up");
            telemetry.addData("Catapult", "RB=up, RT=down");

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
        handleDriveControls();
        handleIntakeControls();
        handleFootControls();
        handleCatapultControls();

        // Display telemetry
        updateTelemetry();
    }

    private void handleDriveControls() {
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

    private void handleIntakeControls() {
        // Left trigger = intake in, Left bumper = intake out
        // When both pressed, prioritize out (safer to eject)
        boolean intakeInButton = gamepad1.left_trigger > 0.2;
        boolean intakeOutButton = gamepad1.left_bumper;

        if (intakeInButton && intakeOutButton) {
            intakeInButton = false; // Prioritize out when both pressed
        }

        if (intakeInButton) {
            intake.startIntake();
        } else if (intakeOutButton) {
            intake.startOuttake();
        } else {
            intake.stop();
        }
    }

    private void handleFootControls() {
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
    }

    private void handleCatapultControls() {
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

    private void updateTelemetry() {
        telemetry.addData("=== STATUS ===", "");
        telemetry.addData("Runtime", "%.1f sec", runtime.seconds());

        // Drivetrain
        telemetry.addData("=== DRIVETRAIN ===", "");
        drivetrain.addTelemetry();

        // Intake
        telemetry.addData("=== INTAKE ===", "");
        intake.addTelemetry();

        // Catapult
        telemetry.addData("=== CATAPULT ===", "");
        catapult.addTelemetry();

        // Foot
        telemetry.addData("=== FOOT ===", "");
        foot.addTelemetry();

        telemetry.update();
    }

    @Override
    public void stop() {
        // Stop all subsystems
        drivetrain.stop();
        intake.stop();
        catapult.stop();
        foot.stop();

        telemetry.addData("Status", "TeleOp Stopped");
        telemetry.addData("Final Runtime", "%.1f seconds", runtime.seconds());
        telemetry.update();
    }
}
