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
    DuckSpinner duckSpinner;
    BotUtilities utilities;
    BlockArm blockArm;
    Capper capper;

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void init() {
        drivetrain = new MecanumDrivetrain(this.hardwareMap, this.telemetry);
        duckSpinner = new DuckSpinner(this.hardwareMap, this.telemetry);
        utilities = new BotUtilities(this.telemetry);
        blockArm = new BlockArm(this.hardwareMap, this.telemetry);
        capper = new Capper(this.hardwareMap, this.telemetry);

        // Set up our telemetry dashboard
        getTelemetry();

        // set capper angle position, powering the servo's position
        capper.setOrientation(0);

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
        } else if (gamepad1.triangle) {
            duckSpinner.setDuckMotorPower(-.5);
        } else if (gamepad1.dpad_up) {
            duckSpinner.setDuckMotorPower(1);
        } else {
            duckSpinner.setDuckMotorPower(0);
        }
        if (gamepad1.circle) {
            blockArm.setPosition(1);
            telemetry.addLine("circle pressed");
        } else {
            blockArm.setPosition(0.5);
        }
    }

    public void checkOperatorController(){
        // extending tape measure
        if (gamepad2.dpad_right) {
            capper.setPowerOfExtenderServo(1);
            telemetry.update();
        } else if (gamepad2.dpad_left){
            capper.setPowerOfExtenderServo(-1);
        } else {
            capper.setPowerOfExtenderServo(0);
        }

        // turning capper
        capper.setPowerOfTurretServo(gamepad2.right_stick_x);

        double verticalOrientation = capper.getVerticalOrientation();

        // change angle of capper
        capper.setOrientation(verticalOrientation + gamepad2.right_stick_y);
    }

    public void getTelemetry() {
        // Show the elapsed game time
        telemetry.addData("Run Time: ", runtime.toString());

        // Telemetry about motion
        //telemetry.addData("Motors", "leftFront (%.2f), rightFront (%.2f), rightRear (%.2f), leftRear (%.2f)", telemValues[0], telemValues[1], telemValues[2], telemValues[3]);
        telemetry.update();
    }  // getTelemetry

}    // The Almighty Curly Brace For Everything

