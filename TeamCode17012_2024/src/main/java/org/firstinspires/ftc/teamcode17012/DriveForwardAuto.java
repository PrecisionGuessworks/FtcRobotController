package org.firstinspires.ftc.teamcode17012;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode17012.Subsystems.BotUtilities;
import org.firstinspires.ftc.teamcode17012.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode17012.Subsystems.PixelScoop;

//////////////////////////////////////////////////////////////////////////////////////////
@Autonomous(name="Drive Forward REAL", group="Mec")
//@Disabled        // Comment/Uncomment this line as needed to show/hide this opmode
//////////////////////////////////////////////////////////////////////////////////////////

public class DriveForwardAuto extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    MecanumDrivetrain drivetrain;
    BotUtilities utilities;
    PixelScoop scoop;

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void runOpMode() {
        drivetrain = new MecanumDrivetrain(this.hardwareMap, this.telemetry);
        utilities = new BotUtilities(this.telemetry);
        scoop = new PixelScoop(hardwareMap, telemetry);

        scoop.holdPixel();

        waitForStart();



        //Drive Forward for 0.5 seconds
        drivetrain.mecanumDrive_Cartesian(0.0, -0.75, 0.0);
        utilities.delay( 3750);
        drivetrain.stopDriving();

        // Drop the pixel
        scoop.deployPixel();
        utilities.delay(1000);
    }
        //x positive=right, y positive=back, rotation positive=right
    public void getTelemetry() {
        // Show the elapsed game time
        telemetry.addData("Run Time: ", runtime.toString());

        // Telemetry about motion
        //telemetry.addData("Motors", "leftFront (%.2f), rightFront (%.2f), rightRear (%.2f), leftRear (%.2f)", telemValues[0], telemValues[1], telemValues[2], telemValues[3]);
        telemetry.update();
    }  // getTelemetry

}    // The Almighty Curly Brace For Everything

