package org.firstinspires.ftc.teamcode18638;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode18638.Subsystems.Blinkin;
import org.firstinspires.ftc.teamcode18638.Subsystems.BotUtilities;
import org.firstinspires.ftc.teamcode18638.Subsystems.TankDrivetrain;
import org.firstinspires.ftc.teamcode18638.Subsystems.DuckSpinner;

//////////////////////////////////////////////////////////////////////////////////////////
@Autonomous(name="Red Drive Forward", group="Red")
//@Disabled        // Comment/Uncomment this line as needed to show/hide this opmode
//////////////////////////////////////////////////////////////////////////////////////////

public class AutoMode extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    TankDrivetrain drivetrain;
    DuckSpinner duckSpinner;
    BotUtilities utilities;
    Blinkin blinkin;

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void runOpMode() {
        drivetrain = new TankDrivetrain(this.hardwareMap, this.telemetry);
        duckSpinner = new DuckSpinner(this.hardwareMap, this.telemetry);
        utilities = new BotUtilities(this.telemetry);
        blinkin = new Blinkin(this.hardwareMap, this.telemetry);

        // Setup blinkin color
        blinkin.setRedBreath();
        blinkin.runBlinkinPattern();

        waitForStart();

        // Update color after running
        blinkin.setRed();
        blinkin.runBlinkinPattern();

        //Drive Forward for 0.5 seconds
//        drivetrain.arcadeDrive(-1.0, 0.0);
        drivetrain.setPower(-0.7,0.0);
        utilities.delay(1000);
        duckSpinner.setDuckMotorPower(.3);
        drivetrain.setPower(0.0,0.0);
        utilities.delay(10000);
        duckSpinner.setDuckMotorPower(0);
    }

    public void getTelemetry() {
        // Show the elapsed game time
        telemetry.addData("Run Time: ", runtime.toString());

        // Telemetry about motion
        //telemetry.addData("Motors", "leftFront (%.2f), rightFront (%.2f), rightRear (%.2f), leftRear (%.2f)", telemValues[0], telemValues[1], telemValues[2], telemValues[3]);
        telemetry.update();
    }  // getTelemetry

}    // The Almighty Curly Brace For Everything

