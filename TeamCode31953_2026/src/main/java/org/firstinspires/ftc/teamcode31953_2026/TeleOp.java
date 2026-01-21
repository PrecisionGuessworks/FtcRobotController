//package org.firstinspires.ftc.teamcode31953_2026;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode31953_2026.Subsystems.MecanumDrivetrain;
//import org.firstinspires.ftc.teamcode31953_2026.Subsystems.Robot;
//
//public class TeleOp extends LinearOpMode {
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//        Robot robot = new Robot(hardwareMap);
//        MecanumDrivetrain drivetrain = new MecanumDrivetrain(hardwareMap, telemetry);
//
//        waitForStart();
//
//        while (opModeIsActive() && !isStopRequested()) {
//            // opmode
//
//            // drive
//            drivetrain.mecanumDrive_Cartesian(
//                    gamepad1.left_stick_x,
//                    gamepad1.left_stick_y,
//                    gamepad1.right_stick_x);
//
//
//            //telemetry.addData("heading", drivetrain.getHeading());
//
//            telemetry.update();
//        }
//    }
//}
