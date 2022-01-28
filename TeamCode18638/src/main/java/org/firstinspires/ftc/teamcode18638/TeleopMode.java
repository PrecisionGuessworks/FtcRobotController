package org.firstinspires.ftc.teamcode18638;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode18638.Subsystems.Blinkin;
import org.firstinspires.ftc.teamcode18638.Subsystems.BlockArm;
import org.firstinspires.ftc.teamcode18638.Subsystems.DuckSpinner;
import org.firstinspires.ftc.teamcode18638.Subsystems.BotUtilities;
import org.firstinspires.ftc.teamcode18638.Subsystems.TankDrivetrain;

//////////////////////////////////////////////////////////////////////////////////////////
@TeleOp(name="Tank Mode from AS", group="Mecanum")
//@Disabled        // Comment/Uncomment this line as needed to show/hide this opmode
//////////////////////////////////////////////////////////////////////////////////////////

public class TeleopMode extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    TankDrivetrain drivetrain;
    DuckSpinner duckSpinner;
    BotUtilities utilities;
    Blinkin blinkin;
    BlockArm blockArm;

    Servo blockServo;

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void init() {
        drivetrain = new TankDrivetrain(this.hardwareMap, this.telemetry);
        duckSpinner = new DuckSpinner(this.hardwareMap, this.telemetry);
        utilities = new BotUtilities(this.telemetry);
        blinkin = new Blinkin(this.hardwareMap, this.telemetry);
        blockArm = new BlockArm(this.hardwareMap, this.telemetry);

        // Set up our telemetry dashboard
        getTelemetry();
        blinkin.setRainbowGlitter();
        blinkin.runBlinkinPattern();

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
        blinkin.setColor12Twinkle();
        blinkin.runBlinkinPattern();
    }

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP */
    @Override
    public void loop() {
        checkDriverController();
        checkOperatorController();

        // Call Telemetry
        getTelemetry();

        // Update the Blinkin
        blinkin.runBlinkinPattern();
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
        drivetrain.arcadeDrive(gamepad1.left_stick_y, gamepad1.right_stick_x);
        if (gamepad1.cross) {
            duckSpinner.runDuckMotorForward();
            blinkin.setForestWaves();
        } else if (gamepad1.triangle) {
            duckSpinner.runDuckMotorBackwards();
            blinkin.setOceanWaves();
        } else {
            duckSpinner.setDuckMotorPower(0);
            blinkin.setColor12Twinkle();
        }

        if (gamepad1.circle) {
            blockArm.setPosition(0.5);
        } else {
            blockArm.setPosition(1);
        }

        if (gamepad1.square) {
            intake.intake();
        } else if (gamepad1.dpad_down) {
            intake.spitout();
        } else {
            intake.idle();
        }
    }

    public void checkOperatorController(){
        if (gamepad1.cross) {
            duckSpinner.runDuckMotorForward();
            blinkin.setForestWaves();
        } else if (gamepad1.triangle) {
            duckSpinner.runDuckMotorBackwards();
            blinkin.setOceanWaves();
        } else {
            duckSpinner.setDuckMotorPower(0);
            blinkin.setColor12Twinkle();
        }


    }

    public void getTelemetry() {
        // Show the elapsed game time
        telemetry.addData("Run Time: ", runtime.toString());
        telemetry.addData("Current Blinkin Pattern", blinkin.getBlinkinPattern());
        // Telemetry about motion
        //telemetry.addData("Motors", "leftFront (%.2f), rightFront (%.2f), rightRear (%.2f), leftRear (%.2f)", telemValues[0], telemValues[1], telemValues[2], telemValues[3]);
        telemetry.update();
    }  // getTelemetry

}    // The Almighty Curly Brace For Everything

