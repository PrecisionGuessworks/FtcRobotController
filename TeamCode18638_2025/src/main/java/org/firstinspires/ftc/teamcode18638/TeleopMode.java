package org.firstinspires.ftc.teamcode18638;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode18638.Subsystems.Arm;
import org.firstinspires.ftc.teamcode18638.Subsystems.BotUtilities;
import org.firstinspires.ftc.teamcode18638.Subsystems.MecanumDrivetrain;


//////////////////////////////////////////////////////////////////////////////////////////
@TeleOp(name="Teleop Mode", group="Mecanum")
//@Disabled        // Comment/Uncomment this line as needed to show/hide this opmode
//////////////////////////////////////////////////////////////////////////////////////////

public class TeleopMode extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    MecanumDrivetrain drivetrain;
    Arm arm;
    BotUtilities utilities;


//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void init() {
        drivetrain = new MecanumDrivetrain(this.hardwareMap, this.telemetry);
        arm = new Arm(this.hardwareMap, this.telemetry);
        utilities = new BotUtilities(this.telemetry);

        // Set up our telemetry dashboard
        getTelemetry();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized    \n18638 is ready to play.\n\n:)");
    }//

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY */
    @Override
    public void init_loop() {
        telemetry.addData("Run Time: ", runtime.toString());

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
        // TODO: Map button controls for robot motion
        telemetry.addLine("Updating DriverControl");
        drivetrain.mecanumDrive_Cartesian(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
    }


    public void checkOperatorController(){
        // TODO: Map button controls if using a second human robot driver/operator, otherwise, add those to the driver method
        if(gamepad2.right_bumper){
            arm.intaking();
        } else if (gamepad2.left_bumper){
            arm.outtaking();
        } else {
            arm.stopIntake();
        }

        if(gamepad2.triangle || gamepad2.dpad_up) {
            arm.openClaw();
        } else if (gamepad2.cross || gamepad2.dpad_down) {
            arm.closeClaw();
        }

        arm.moveShoulder(-gamepad2.left_stick_y);

        arm.moveWrist(-gamepad2.right_stick_y);

    }

    public void getTelemetry() {
        // Show the elapsed game time
        telemetry.addData("Run Time: ", runtime.toString());
        telemetry.addData("Servo", arm.getServoPosition());
        telemetry.update();
    }  // getTelemetry


}    // The Almighty Curly Brace For Everything

