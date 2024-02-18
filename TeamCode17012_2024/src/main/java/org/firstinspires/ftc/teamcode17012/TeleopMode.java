package org.firstinspires.ftc.teamcode17012;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode17012.Subsystems.Arm;
import org.firstinspires.ftc.teamcode17012.Subsystems.BotUtilities;
import org.firstinspires.ftc.teamcode17012.Subsystems.Camera;
import org.firstinspires.ftc.teamcode17012.Subsystems.Climber;
import org.firstinspires.ftc.teamcode17012.Subsystems.DroneLauncher;
import org.firstinspires.ftc.teamcode17012.Subsystems.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode17012.Subsystems.NavX;
import org.firstinspires.ftc.teamcode17012.Subsystems.PixelScoop;


//////////////////////////////////////////////////////////////////////////////////////////
@TeleOp(name="Teleop Mode REAL", group="Mecanum")
//@Disabled        // Comment/Uncomment this line as needed to show/hide this opmode
//////////////////////////////////////////////////////////////////////////////////////////

public class TeleopMode extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    MecanumDrivetrain drivetrain;
    BotUtilities utilities;
    Arm arm;
    Climber climber;
    DroneLauncher drone;
    NavX imu;
    PixelScoop scoop;
    //Camera camera;


//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void init() {
        drivetrain = new MecanumDrivetrain(this.hardwareMap, this.telemetry);
        utilities = new BotUtilities(this.telemetry);
        //camera = new Camera(this.hardwareMap, this.telemetry);
        drone = new DroneLauncher(this.hardwareMap, this.telemetry);
        arm = new Arm(this.hardwareMap, this.telemetry);
        climber = new Climber(this.hardwareMap, this.telemetry);
        scoop = new PixelScoop(this.hardwareMap, this.telemetry);

        // Set up our telemetry dashboard
        getTelemetry();

        // hold the drone
        drone.holdDrone();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized    \n17012 is ready to play.\n\n:)");
    }//

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY */
    @Override
    public void init_loop() {

        //telemetry.addData("tag", camera.getCurrentTag());
        telemetry.update();
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

        // Drive Control
        drivetrain.mecanumDrive_Cartesian(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

        // Arm Controls
        if(gamepad1.left_trigger > 0.1){
            arm.setArmPower(-gamepad1.left_trigger);
        } else if (gamepad1.right_trigger > 0.1) {
            arm.setArmPower(gamepad1.right_trigger);
        } else {
            arm.setArmPower(0.0);
        }

        // Climber Controls
        if(gamepad1.left_bumper) {
            climber.climbDown();
        } else if (gamepad1.right_bumper) {
            climber.climbUp();
        } else {
            climber.holdClimb();
        }

        // Drone Launcher
        if(gamepad1.dpad_left && gamepad1.circle){
            telemetry.update();
            drone.launchDrone();
        }

        // Pixel Scoop - For Testing
        if (gamepad1.dpad_right){
            scoop.deployPixel();
        } else {
            scoop.holdPixel();
        }
    }

    public void checkOperatorController(){
        // TODO: Add any controls of operation for the operator
    }

    public void getTelemetry() {
        // Show the elapsed game time
        telemetry.addData("Run Time: ", runtime.toString());
        scoop.scoopTelemetry();

        // Gripper Positions

        // Telemetry about motion
        //telemetry.addData("Motors", "leftFront (%.2f), rightFront (%.2f), rightRear (%.2f), leftRear (%.2f)", telemValues[0], telemValues[1], telemValues[2], telemValues[3]);
        telemetry.update();
    }  // getTelemetry

}    // The Almighty Curly Brace For Everything

