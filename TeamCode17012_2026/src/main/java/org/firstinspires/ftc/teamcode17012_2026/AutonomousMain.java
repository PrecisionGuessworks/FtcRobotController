package org.firstinspires.ftc.teamcode17012_2026;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode17012_2026.Subsystems.CatapultSubsystem;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.FootSubsystem;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode17012_2026.Subsystems.MecanumDrivetrain;

/**
 * AutonomousMain - Basic autonomous program for 2026 season
 *
 * This is a TEMPLATE - customize for your specific strategy!
 */
@Autonomous(name="Autonomous Main 2026", group="Competition")
public class AutonomousMain extends LinearOpMode {

    // Subsystems
    private MecanumDrivetrain drivetrain;
    private IntakeSubsystem intake;
    private CatapultSubsystem catapult;
    private FootSubsystem foot;

    // Timing
    private ElapsedTime runtime = new ElapsedTime();

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
            // TODO: Add your autonomous sequence here
            // Example: Drive forward, use catapult, etc.

            telemetry.addData("Status", "Autonomous Complete!");
            telemetry.addData("Runtime", "%.1f seconds", runtime.seconds());
            telemetry.update();
        }

        // Stop all subsystems
        drivetrain.stop();
        intake.stop();
        catapult.stop();
        foot.stop();
    }

    private void initializeSubsystems() {
        telemetry.addData("Status", "Initializing subsystems...");
        telemetry.update();

        drivetrain = new MecanumDrivetrain(hardwareMap, telemetry);
        intake = new IntakeSubsystem(hardwareMap, telemetry);
        catapult = new CatapultSubsystem(hardwareMap, telemetry);
        foot = new FootSubsystem(hardwareMap, telemetry);

        telemetry.addData("Status", "Initialization complete");
        telemetry.update();
    }
}
