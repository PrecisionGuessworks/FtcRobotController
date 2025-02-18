package org.firstinspires.ftc.teamcode17012;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode17012.Subsystems.BotUtilities;
import org.firstinspires.ftc.teamcode17012.Subsystems.Climber;
import org.firstinspires.ftc.teamcode17012.Subsystems.Intake;
import org.firstinspires.ftc.teamcode17012.Subsystems.Lift;
import org.firstinspires.ftc.teamcode17012.Subsystems.MecanumDrivetrain;
//import org.firstinspires.ftc.teamcode17012.Subsystems.Camera;
//import org.firstinspires.ftc.teamcode17012.Subsystems.MecanumDrivetrain;
//import org.firstinspires.ftc.teamcode17012.Subsystems.NavX;


//////////////////////////////////////////////////////////////////////////////////////////
@TeleOp(name="This Teleop Mode", group="Mecanum")
//@Disabled        // Comment/Uncomment this line as needed to show/hide this opmode
//////////////////////////////////////////////////////////////////////////////////////////

public class TeleopMode extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    Lift lift;
    MecanumDrivetrain drivetrain;
    Climber climber;
    Intake intake;


//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void init() {

        lift = new Lift(hardwareMap);
        drivetrain = new MecanumDrivetrain(hardwareMap, telemetry);
        climber = new Climber(hardwareMap);
        intake = new Intake(hardwareMap);

        // Set up our telemetry dashboard
        getTelemetry();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized    \n17012 is ready to play.\n\n:)");
    }//

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY */
    @Override
    public void init_loop() {
        telemetry.update();
    }

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits PLAY */
    @Override
    public void start() {
        runtime.reset();
        getTelemetry();
    }

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP */
    @Override
    public void loop() {
        drivetrain.mecanumDrive_Cartesian(
                gamepad1.left_stick_x,
                gamepad1.left_stick_y,
                gamepad1.right_stick_x);


        // Lift
        lift.setLiftPower(-gamepad2.left_stick_y);

        // Intake
        if (gamepad2.circle) {
            intake.extendIntake();
        }
        if (gamepad2.square) {
            intake.retractIntake();
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

        // Call Telemetry
        getTelemetry();

    }

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE after the driver hits STOP */
    @Override
    public void stop() {
        telemetry.addData("Robot Stopped. ", "Have a nice day.");
        telemetry.addData("Final runtime: ", runtime.toString());
        telemetry.update();
    }

//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
    /*                              TELEOP-SPECIFIC METHODS                                 */
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////

    public void checkDriverController() {


        // TODO: Add any controls of operation for the driver
    }

    public void checkOperatorController(){
        // TODO: Add any controls of operation for the operator

    }

    public void getTelemetry() {
        // Show the elapsed game time
        telemetry.addData("Run Time: ", runtime.toString());


        // Gripper Positions

        // Telemetry about motion
        //telemetry.addData("Motors", "leftFront (%.2f), rightFront (%.2f), rightRear (%.2f), leftRear (%.2f)", telemValues[0], telemValues[1], telemValues[2], telemValues[3]);
        telemetry.update();
    }  // getTelemetry

}    // The Almighty Curly Brace For Everything

