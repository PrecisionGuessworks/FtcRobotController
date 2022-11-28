package org.firstinspires.ftc.teamcode17012;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode17012.Subsystems.BlockArm;
import org.firstinspires.ftc.teamcode17012.Subsystems.BotUtilities;
import org.firstinspires.ftc.teamcode17012.Subsystems.Capper;
import org.firstinspires.ftc.teamcode17012.Subsystems.DuckSpinner;
import org.firstinspires.ftc.teamcode17012.Subsystems.MecanumDrivetrain;


//////////////////////////////////////////////////////////////////////////////////////////
@TeleOp(name="MEC Mode from AS", group="Mecanum")
//@Disabled        // Comment/Uncomment this line as needed to show/hide this opmode
//////////////////////////////////////////////////////////////////////////////////////////

public class TeleopMode extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    MecanumDrivetrain drivetrain;
    BotUtilities utilities;


//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void init() {
        drivetrain = new MecanumDrivetrain(this.hardwareMap, this.telemetry);
        utilities = new BotUtilities(this.telemetry);

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
        if (gamepad1.cross) {
            duckSpinner.setDuckMotorPower(.5);
        } else if (gamepad1.dpad_down) {
            duckSpinner.setDuckMotorPower(0.5);
        } else if (gamepad1.triangle) {
            duckSpinner.setDuckMotorPower(-0.5);
        } else if (gamepad1.dpad_up) {
            duckSpinner.setDuckMotorPower(-0.5);
        } else if (gamepad1.dpad_right) { // panic
            duckSpinner.setDuckMotorPower(1);
        } else if (gamepad1.circle) { // panic
            duckSpinner.setDuckMotorPower(-1);
        } else {
            duckSpinner.setDuckMotorPower(0);
        }

        if (gamepad1.right_bumper) {
            blockArm.setPosition(1);
            telemetry.addLine("block throw");
        } else {
            blockArm.setPosition(0.5);
        }
    }

    public void checkOperatorController(){
        // extending tape measure
        if (gamepad2.left_bumper) {
            capper.setPowerOfExtenderServo(1);
            telemetry.update();
        } else if (gamepad2.right_bumper){
            capper.setPowerOfExtenderServo(-1);
        } else {
            capper.setPowerOfExtenderServo(0);
        }

        // turning capper
        capper.setPowerOfTurretServo(-gamepad2.right_stick_x * 0.25);

        double verticalOrientation = capper.getVerticalOrientation();
        telemetry.addData("Angle val", verticalOrientation);
        // change angle of capper
        capper.setOrientation(verticalOrientation + (gamepad2.left_stick_y * 0.001));
    }

    public void getTelemetry() {
        // Show the elapsed game time
        telemetry.addData("Run Time: ", runtime.toString());

        // Telemetry about motion
        //telemetry.addData("Motors", "leftFront (%.2f), rightFront (%.2f), rightRear (%.2f), leftRear (%.2f)", telemValues[0], telemValues[1], telemValues[2], telemValues[3]);
        telemetry.update();
    }  // getTelemetry

}    // The Almighty Curly Brace For Everything

