package org.firstinspires.ftc.teamcode17012;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode17012.Subsystems.Climber;
import org.firstinspires.ftc.teamcode17012.Subsystems.Intake;
import org.firstinspires.ftc.teamcode17012.Subsystems.Lift;
import org.firstinspires.ftc.teamcode17012.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode17012.Subsystems.Robot;

public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Robot robot = new Robot(hardwareMap);
        Lift lift = new Lift(hardwareMap);
        MecanumDrivetrain drivetrain = new MecanumDrivetrain(hardwareMap, telemetry);
        Climber climber = new Climber(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            // opmode

            // drive
            drivetrain.mecanumDrive_Cartesian(
                    gamepad1.left_stick_x,
                    gamepad1.left_stick_y,
                    gamepad1.right_stick_x);


            // Lift
            lift.setLiftPower(-gamepad2.left_stick_y);

            // Intake
            if (gamepad2.circle) {
                intake.extendIntake();
            } else if (gamepad2.square) {
                intake.retractIntake();
            } else {
                intake.stopIntakeExtension();
            }

            // Virtual 4 Bar
            if (gamepad2.triangle) {
                intake.deployV4Bar();
            }
            if (gamepad2.cross) {
                intake.retractV4Bar();
            }

            // Boat Hook - Extension
            if (gamepad1.dpad_down){
                climber.retractBoatHook();
            } else if (gamepad1.dpad_up) {
                climber.extendBoatHook();
            } else {
                climber.stopBoatHook();
            }

            // Boat Hook - Rotation
            if (gamepad1.dpad_left){
                climber.pivotBoatHookDown();
            } else if (gamepad1.dpad_right) {
                climber.pivotBoatHookUp();
            } else {
                climber.stopBoatHookPivot();
            }




            //telemetry.addData("heading", drivetrain.getHeading());
            telemetry.addData("lift power", lift.getPower());

            telemetry.update();
        }
    }
}
