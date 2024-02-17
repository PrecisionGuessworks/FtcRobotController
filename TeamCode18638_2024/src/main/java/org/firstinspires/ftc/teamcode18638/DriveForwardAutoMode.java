package org.firstinspires.ftc.teamcode18638;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode18638.Subsystems.Arm;
import org.firstinspires.ftc.teamcode18638.Subsystems.BotUtilities;
import org.firstinspires.ftc.teamcode18638.Subsystems.Grabber;
import org.firstinspires.ftc.teamcode18638.Subsystems.TankDrivetrain;

//////////////////////////////////////////////////////////////////////////////////////////
@Autonomous(name="Drive Forward", group="Mec")
//@Disabled        // Comment/Uncomment this line as needed to show/hide this opmode
//////////////////////////////////////////////////////////////////////////////////////////

public class DriveForwardAutoMode extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    TankDrivetrain drivetrain;
    BotUtilities utilities;
    Grabber grabber;
    Arm arm;
//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void runOpMode() {
        drivetrain = new TankDrivetrain(this.hardwareMap, this.telemetry);
        utilities = new BotUtilities(this.telemetry);
        arm = new Arm(this.hardwareMap, this.telemetry);
        grabber = new Grabber(this.hardwareMap, this.telemetry);

        waitForStart();

        // TODO: Write autonomous code here - This is the DRIVE FORWARD (only) auto

        // Drive to Backstage
        drivetrain.arcadeDrive(1, 0);
        utilities.delay(1500);
        drivetrain.stopDriving();

        // Flick wrist forward
        grabber.setWristToAuto();
        grabber.setWristDown();
        utilities.delay(1000);

        // Drop pixel
        grabber.openGripper();
        utilities.delay(1000);

        // Back up slightly
        drivetrain.arcadeDrive(-0.35, 0);
        utilities.delay(750);
        drivetrain.stopDriving();

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

