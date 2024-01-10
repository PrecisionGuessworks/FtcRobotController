/*
17012
 */

package org.firstinspires.ftc.teamcodeultimategoal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcodeultimategoal.Subsystems.ConveyorSubsystem;
import org.firstinspires.ftc.teamcodeultimategoal.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcodeultimategoal.Subsystems.MecanumDrivetrainSubsystem;
import org.firstinspires.ftc.teamcodeultimategoal.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcodeultimategoal.Subsystems.WobbleSubsystem;


//////////////////////////////////////////////////////////////////////////////////////////
@Autonomous(name="Mech Auto Mode", group="Mecanum")
//@Disabled        // Comment/Uncomment this line as needed to show/hide this opmode
//////////////////////////////////////////////////////////////////////////////////////////

public class AutoMode extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    BotUtilities utilities;
    MecanumDrivetrainSubsystem mechDrivetrain;
    WobbleSubsystem wobble;
    IntakeSubsystem intake;
    ShooterSubsystem shooter;
    ConveyorSubsystem conveyor;

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void runOpMode() {
        mechDrivetrain = new MecanumDrivetrainSubsystem(this.hardwareMap, this.telemetry);
        wobble = new WobbleSubsystem(this.hardwareMap, this.telemetry);
        intake = new IntakeSubsystem(this.hardwareMap, this.telemetry);
        shooter = new ShooterSubsystem(this.hardwareMap, this.telemetry);
        conveyor = new ConveyorSubsystem(this.hardwareMap, this.telemetry);
        utilities = new BotUtilities(this.telemetry);

        //Drive Forward for half 1 and a half seconds
        mechDrivetrain.mecanumDrive_Cartesian(0.5, 0.0, 0.0);
        utilities.delay(1500);
        mechDrivetrain.mecanumDrive_Cartesian(0.0, 0.0, 0.0);

        //wobble arm up and then release
        wobble.setArmPower(-0.5);
        utilities.delay(500);
        wobble.setArmPower(0.0);
        utilities.delay(500);
        wobble.runIntakeOut();

        //Shoot rings
        shooter.revUp();
        utilities.delay(1000);
        intake.runIntakeIn(0.8);
        conveyor.runConveyorUpShooting();
        utilities.delay(2000);
        shooter.revUp();
        intake.idle();
        conveyor.idle();

        //Drive backwards 0.5 seconds
        mechDrivetrain.mecanumDrive_Cartesian(-0.5, 0.0, 0.0);
        utilities.delay(    500);
        mechDrivetrain.mecanumDrive_Cartesian(0.0, 0.0, 0.0);

    }

    public void getTelemetry() {
        // Show the elapsed game time
        telemetry.addData("Run Time: ", runtime.toString());

        // Telemetry about motion
        //telemetry.addData("Motors", "leftFront (%.2f), rightFront (%.2f), rightRear (%.2f), leftRear (%.2f)", telemValues[0], telemValues[1], telemValues[2], telemValues[3]);
        telemetry.update();
    }  // getTelemetry

}    // The Almighty Curly Brace For Everything

