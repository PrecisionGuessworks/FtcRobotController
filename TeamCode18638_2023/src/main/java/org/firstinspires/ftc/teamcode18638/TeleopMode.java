package org.firstinspires.ftc.teamcode18638;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode18638.Subsystems.BotUtilities;
import org.firstinspires.ftc.teamcode18638.Subsystems.Elevator;
import org.firstinspires.ftc.teamcode18638.Subsystems.Gripper;
import org.firstinspires.ftc.teamcode18638.Subsystems.MecanumDrivetrain;


//////////////////////////////////////////////////////////////////////////////////////////
@TeleOp(name="Teleop Mode", group="Mecanum")
//@Disabled        // Comment/Uncomment this line as needed to show/hide this opmode
//////////////////////////////////////////////////////////////////////////////////////////

public class TeleopMode extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    MecanumDrivetrain drivetrain;
    BotUtilities utilities;
    Gripper gripper;
    Elevator elevator;

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void init() {
        drivetrain = new MecanumDrivetrain(this.hardwareMap, this.telemetry);
        utilities = new BotUtilities(this.telemetry);
        gripper = new Gripper(this.hardwareMap, this.telemetry);
        elevator = new Elevator(this.hardwareMap, this.telemetry);

        // Set up our telemetry dashboard
        getTelemetry();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized    \n18638 is ready to play.\n\n:)");
    }//

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY */
    @Override
    public void init_loop() {
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
        checkDriverController();
        checkOperatorController();

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
        telemetry.addLine("Updating DriverControl");
        drivetrain.mecanumDrive_Cartesian(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

        // Gripper Control - If single driver/operator
        if (gamepad1.left_bumper) {
            gripper.openClaw();
        } else if (gamepad1.right_bumper) {
            gripper.closeClaw();
        }

        // Elevator Control - If single driver/operator
//        if (gamepad1.right_trigger > 0 && gamepad1.left_trigger > 0) {
//            // Double Button Activated, Turn off Elevator Power
//            elevator.setElevatorPower(0);
//        } else if (gamepad1.right_trigger > 0) {
//            // Right Trigger Raises elevator
//            elevator.setElevatorPower(gamepad1.right_trigger);
//        } else if (gamepad1.left_trigger > 0) {
//            // Left Trigger Lowers Elevator
//            elevator.setElevatorPower(-gamepad1.left_trigger);
//        } else {
//            elevator.setElevatorPower(0);
//        }

    }

    public void checkOperatorController(){
        if (gamepad2.triangle){
            gripper.openClaw();
            telemetry.addLine("triangle");
        } else if (gamepad2.cross) {
            gripper.closeClaw();
        }

        // Elevator Button Control
        if (gamepad2.dpad_up) {
            elevator.runElevatorToMaxHeight();
            telemetry.addLine("dpadup");
        } else if (gamepad2.dpad_right) {
            elevator.runElevatorToMediumJunction();
        } else if (gamepad2.dpad_left) {
            elevator.runElevatorToLowJunction();
        } else if (gamepad2.dpad_down) {
            elevator.runElevatorToHome();
        } else {
            elevator.setElevatorPower(gamepad2.left_stick_y);
        }
    }

    public void getTelemetry() {
        // Show the elapsed game time
        telemetry.addData("Run Time: ", runtime.toString());
        elevator.printEelvatorTelemetry();
        // Telemetry about motion
        //telemetry.addData("Motors", "leftFront (%.2f), rightFront (%.2f), rightRear (%.2f), leftRear (%.2f)", telemValues[0], telemValues[1], telemValues[2], telemValues[3]);
        telemetry.update();
    }  // getTelemetry

}    // The Almighty Curly Brace For Everything

