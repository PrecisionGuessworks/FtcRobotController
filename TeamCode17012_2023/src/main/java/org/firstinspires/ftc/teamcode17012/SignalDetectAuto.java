package org.firstinspires.ftc.teamcode17012;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode17012.Subsystems.BotUtilities;
import org.firstinspires.ftc.teamcode17012.Subsystems.Camera;
import org.firstinspires.ftc.teamcode17012.Subsystems.MecanumDrivetrain;

//////////////////////////////////////////////////////////////////////////////////////////
@Autonomous(name="Signal Detect", group="Mec")
//@Disabled        // Comment/Uncomment this line as needed to show/hide this opmode
//////////////////////////////////////////////////////////////////////////////////////////

public class SignalDetectAuto extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    MecanumDrivetrain drivetrain;
    BotUtilities utilities;
    Camera camera;

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void runOpMode() {
        drivetrain = new MecanumDrivetrain(this.hardwareMap, this.telemetry);
        utilities = new BotUtilities(this.telemetry);
        camera = new Camera(this.hardwareMap, this.telemetry);

        // Tags of interest
        final int ZONE_1_TAG = 10;
        final int ZONE_2_TAG = 18;
        final int ZONE_3_TAG = 1;

        int spottedTag = -1;

        while (!isStarted() && !isStopRequested()) {
            spottedTag = camera.getCurrentTag();
            telemetry.addData("tag", camera.getCurrentTag());
            if (spottedTag == ZONE_1_TAG) {
                telemetry.addLine("\nZone 1 spotted\n\tMove expected: Forward, left.");
            } else if (spottedTag == ZONE_2_TAG) {
                telemetry.addLine("\nZone 2 spotted\n\tMove expected: Forward.");
            } else if (spottedTag == ZONE_3_TAG) {
                telemetry.addLine("\nZone 3 spotted\n\tMove expected: Forward, right.");
            } else {
                telemetry.addLine("\nNo zone spotted\n\tMove expected: Park.");
            }
            telemetry.update();
        }
        // TODO: Create code to search further if no tag was seen during the init phase

        // Perform drive behavior based on tag seen during init period
        if (spottedTag == ZONE_1_TAG) {
            telemetry.addLine("ZONE_1_TAG");
            telemetry.update();
            drivetrain.mecanumDrive_Cartesian(0.0, -0.75, 0.0);
            utilities.delay( 3250);
            drivetrain.stopDriving();
            drivetrain.mecanumDrive_Cartesian(-.80, 0.0, 0.0);
            utilities.delay( 3250);
        } else if (spottedTag == ZONE_2_TAG) {
            telemetry.addLine( "ZONE_2_TAG");
            telemetry.update();
            drivetrain.mecanumDrive_Cartesian(0.0, -0.75, 0.0);
            utilities.delay( 3750);
            drivetrain.stopDriving();
        } else if (spottedTag == ZONE_3_TAG) {
            telemetry.addLine("ZONE_3_TAG");
            telemetry.update();
            drivetrain.mecanumDrive_Cartesian(0.0, -0.75, 0.0);
            utilities.delay( 3250);
            drivetrain.mecanumDrive_Cartesian(0.8, 0.0, 0.0);
            utilities.delay( 3250);
            drivetrain.stopDriving();
        } else {
            telemetry.addLine("No tag found");
            //default (Park???)
        }
        telemetry.update();



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

