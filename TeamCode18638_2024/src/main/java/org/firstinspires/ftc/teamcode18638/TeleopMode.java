package org.firstinspires.ftc.teamcode18638;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode18638.Subsystems.Arm;
import org.firstinspires.ftc.teamcode18638.Subsystems.BotUtilities;
import org.firstinspires.ftc.teamcode18638.Subsystems.TankDrivetrain;
import org.firstinspires.ftc.teamcode18638.Subsystems.Grabber;


//////////////////////////////////////////////////////////////////////////////////////////
@TeleOp(name="Teleop Mode", group="Mecanum")
//@Disabled        // Comment/Uncomment this line as needed to show/hide this opmode
//////////////////////////////////////////////////////////////////////////////////////////

public class TeleopMode extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    TankDrivetrain drivetrain;
    BotUtilities utilities;
    Arm arm;
    Grabber grabber;


//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void init() {
        drivetrain = new TankDrivetrain(this.hardwareMap, this.telemetry);
        utilities = new BotUtilities(this.telemetry);
        arm = new Arm(this.hardwareMap, this.telemetry);
        grabber = new Grabber(this.hardwareMap, this.telemetry);

        // Set up our telemetry dashboard
        getTelemetry();

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
        // TODO: Map button controls for robot motion
        telemetry.addLine("Updating DriverControl");
        drivetrain.arcadeDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x);
        armControl();

    }

    public void checkOperatorController(){
        // TODO: Map button controls if using a second human robot driver/operator, otherwise, add those to the driver method
    }

    public void getTelemetry() {
        // Show the elapsed game time
        telemetry.addData("Run Time: ", runtime.toString());
        telemetry.update();
    }  // getTelemetry



    // Arm Control
    // Code provided by Rev. Somewhat modified.
    public void armControl(){
        boolean manualMode = false;
        double manualArmPower = gamepad1.right_trigger - gamepad1.left_trigger;
        manualArmPower = arm.deadband(manualArmPower);
        if (arm.humanControlRequested(manualArmPower)) {
            if (!manualMode) {
                arm.setArmPower(0);
                arm.setRunWithoutEncoderMode();
                manualMode = true;
            }
            arm.setArmPower(manualArmPower);
        } else {
            if (manualMode) {
                arm.setTargetPositionsToCurrent();
                arm.setArmPower(1.0);
                arm.setRunToPositionMode();
                manualMode = false;
            }

            //preset buttons
            if (gamepad1.a) {
                arm.setTargetPositionTo(Constants.ARM_HOME_POSITION);
                arm.setArmPower(1.0);
                arm.setRunToPositionMode();
                grabber.setWristUp();
            } else if (gamepad1.b) {
                arm.setTargetPositionTo(Constants.ARM_INTAKE_POSITION);
                arm.setArmPower(1.0);
                arm.setRunToPositionMode();
                grabber.setWristDown();
            } else if (gamepad1.y) {
                arm.setTargetPositionTo(Constants.ARM_SCORE_POSITION);
                arm.setArmPower(1.0);
                arm.setRunToPositionMode();
                grabber.setWristUp();
            }
        }

        //Re-zero encoder button
        if (gamepad1.start) {
            arm.stopAndResetEncoder();
            arm.setArmPower(0.0);
            manualMode = false;
        }

        //Watchdog to shut down motor once the arm reaches the home position
        if (!manualMode && arm.checksForWatchdog()) {
            arm.setArmPower(0.0);
            arm.setRunWithoutEncoderMode();
        }

        //GRIPPER
        if (gamepad1.left_bumper || gamepad1.right_bumper) {
            grabber.openGripper();
        }
        else {
            grabber.closeGripper();
        }
    }

}    // The Almighty Curly Brace For Everything

