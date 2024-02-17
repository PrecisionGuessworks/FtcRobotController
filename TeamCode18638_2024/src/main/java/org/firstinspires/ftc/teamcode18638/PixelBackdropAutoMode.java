package org.firstinspires.ftc.teamcode18638;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode18638.Subsystems.Arm;
import org.firstinspires.ftc.teamcode18638.Subsystems.BotUtilities;
import org.firstinspires.ftc.teamcode18638.Subsystems.Grabber;
import org.firstinspires.ftc.teamcode18638.Subsystems.TankDrivetrain;

//////////////////////////////////////////////////////////////////////////////////////////
@Autonomous(name="Backdrop Pixel Auto", group="Mec")
//@Disabled        // Comment/Uncomment this line as needed to show/hide this opmode
//////////////////////////////////////////////////////////////////////////////////////////

public class PixelBackdropAutoMode extends LinearOpMode {
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
        grabber = new Grabber(this.hardwareMap, this.telemetry);
        arm = new Arm(this.hardwareMap, this.telemetry);
        waitForStart();

        // TODO: Write autonomous code here - This is the Pixel Backdrop Auto

        // Drive Forward
        drivetrain.arcadeDrive(0.75, 0);
        utilities.delay(1250);
        drivetrain.stopDriving();

        // Turn right 90
        drivetrain.arcadeDrive(0, 0.75);
        utilities.delay(750);
        drivetrain.stopDriving();

        // Drive forward
        drivetrain.arcadeDrive(0.75, 0);
        utilities.delay(1250);
        drivetrain.stopDriving();

        // Turn left 90
        drivetrain.arcadeDrive(0, -0.75);
        utilities.delay(750);
        drivetrain.stopDriving();

        // Slight forward to backdrop
        drivetrain.arcadeDrive(0.40, 0);
        utilities.delay(400);
        drivetrain.stopDriving();

        // Raise Aim to Scoring Height
        arm.setArmPower(0.5);
        utilities.delay(250);
        arm.setArmPower(0);

        // Flip out Grabber
        grabber.setWristDown();

        // Drop Pixel
        grabber.openGripper();

        // Retract Grabber
        grabber.setWristUp();

        // Drive back slightly
        drivetrain.arcadeDrive(-0.25, 0);
        utilities.delay(150);
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

