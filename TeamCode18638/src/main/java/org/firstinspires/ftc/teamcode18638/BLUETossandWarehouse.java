package org.firstinspires.ftc.teamcode18638;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode18638.Subsystems.Blinkin;
import org.firstinspires.ftc.teamcode18638.Subsystems.BlockArm;
import org.firstinspires.ftc.teamcode18638.Subsystems.BotUtilities;
import org.firstinspires.ftc.teamcode18638.Subsystems.TankDrivetrain;
import org.firstinspires.ftc.teamcode18638.Subsystems.DuckSpinner;

//////////////////////////////////////////////////////////////////////////////////////////
@Autonomous(name="BLUE toss and warehouse", group="Blue")
//@Disabled        // Comment/Uncomment this line as needed to show/hide this opmode
//////////////////////////////////////////////////////////////////////////////////////////

public class BLUETossandWarehouse extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    TankDrivetrain drivetrain;
    DuckSpinner duckSpinner;
    BotUtilities utilities;
    Blinkin blinkin;
    BlockArm blockArm;

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void runOpMode() {
        drivetrain = new TankDrivetrain(this.hardwareMap, this.telemetry);
        duckSpinner = new DuckSpinner(this.hardwareMap, this.telemetry);
        utilities = new BotUtilities(this.telemetry);
        blinkin = new Blinkin(this.hardwareMap, this.telemetry);
        blockArm = new BlockArm(this.hardwareMap, this.telemetry);

        waitForStart();

        // Update color after running
        blinkin.setRed();
        blinkin.runBlinkinPattern();

        //Drive backwards to hub
        drivetrain.arcadeDrive(0.7, 0);
        utilities.delay(1000);
        drivetrain.arcadeDrive(0,0);
        drivetrain.stopDriving();

        // yeet block
        blockArm.setPosition(0.75);
        utilities.delay(1000);
        blockArm.setPosition(1);
        utilities.delay(1000);

        // clear the hub
        drivetrain.arcadeDrive(-0.7, 0);
        utilities.delay(500);

        // turn to the warehouse
        drivetrain.tankDrive(-0.45, 0.45);
        utilities.delay(600);
        drivetrain.stopDriving();

        // run to the warehouse
        drivetrain.arcadeDrive(0.7, 0);
        utilities.delay(4000);
        drivetrain.stopDriving();



    }

    public void getTelemetry() {
        // Show the elapsed game time
        telemetry.addData("Run Time: ", runtime.toString());

        // Telemetry about motion
        //telemetry.addData("Motors", "leftFront (%.2f), rightFront (%.2f), rightRear (%.2f), leftRear (%.2f)", telemValues[0], telemValues[1], telemValues[2], telemValues[3]);
        telemetry.update();
    }  // getTelemetry

}    // The Almighty Curly Brace For Everything

