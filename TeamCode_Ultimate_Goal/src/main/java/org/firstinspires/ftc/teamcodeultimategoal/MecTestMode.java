/*
17012
 */

package org.firstinspires.ftc.teamcodeultimategoal;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.teamcodeultimategoal.Subsystems.ConveyorSubsystem;
import org.firstinspires.ftc.teamcodeultimategoal.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcodeultimategoal.Subsystems.MecanumDrivetrainSubsystem;
import org.firstinspires.ftc.teamcodeultimategoal.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcodeultimategoal.Subsystems.WobbleSubsystem;

import java.util.Map;

//////////////////////////////////////////////////////////////////////////////////////////
@TeleOp(name="Solo Mode", group="Mecanum")
//@Disabled        // Comment/Uncomment this line as needed to show/hide this opmode
//////////////////////////////////////////////////////////////////////////////////////////

public class MecTestMode extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    BotUtilities utilities;
    MecanumDrivetrainSubsystem mecdrivetrain;
    WobbleSubsystem wobble;
    IntakeSubsystem intake;
    ShooterSubsystem shooter;
    ConveyorSubsystem conveyor;

//////////////////////////////////////////////////////////////////////////////////////////

    /* Code to run ONCE when the driver hits INIT */
    @Override
    public void init() {
        mecdrivetrain = new MecanumDrivetrainSubsystem(this.hardwareMap, this.telemetry);
        wobble = new WobbleSubsystem(this.hardwareMap, this.telemetry);
        intake = new IntakeSubsystem(this.hardwareMap, this.telemetry);
        shooter = new ShooterSubsystem(this.hardwareMap, this.telemetry);
        conveyor = new ConveyorSubsystem(this.hardwareMap, this.telemetry);
        utilities = new BotUtilities(this.telemetry);
        // Set up our telemetry dashboard
        getTelemetry();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized    \nEggCheese 2: Electric Boogaloo is ready to play.\n\n:)");
    }

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
        mecdrivetrain.mecanumDrive_Cartesian(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        // Solo Driver Mode - Operator Controls
        // Conveyor
        if (gamepad1.dpad_up) {
            conveyor.runConveyorUpShooting();
        } else if (gamepad1.dpad_down) {
            conveyor.flushConveyorDown();
        } else {
            conveyor.setBrakeMode();
            conveyor.idle();
        }

        // Shooter
        if (gamepad1.left_bumper){
            shooter.revUp();
        }else{
            shooter.setPower(0);
        }

        // Wooble Arm
        if (gamepad1.triangle) {
            wobble.setArmPower(0.5);
        } else if (gamepad1.cross) {
            wobble.setArmPower(-0.5);
        } else {
            wobble.setArmPower(0);
        }

        //Wobble Intake
        if (gamepad1.right_trigger > 0.1){
            wobble.runIntakeIn();
        }else if(gamepad1.left_trigger > 0.1){
            wobble.runIntakeOut();
        }else{
            wobble.runIntakeIdle();
        }

        // Intake
        if(gamepad2.right_bumper || gamepad1.right_bumper) { //Shooting
            intake.runIntakeIn(0.8);
        }else if(gamepad1.circle){ //Intaking but not shooting
            intake.runIntakeIn(0.8);
            conveyor.setCoastMode();
            telemetry.addLine("circle - intake in");
        }else if(gamepad1.square) { //Outtaking
            intake.runIntakeOut(0.8);
            conveyor.setBrakeMode();
            telemetry.addLine("square - intake out");
        }else{
            conveyor.setBrakeMode();
            intake.idle();
        }
    }

    public void checkOperatorController(){
        /*
        Desired controller function:
        - The operator has many tasks that need to be mapped
        - Wobble Control
            - Use of single joy stick to deploy/retract wobble mech
                - pull down to deploy
                - push up to retract
                - (we like airplanes around here)
            - Button control for intaking
                - Servos in
                - Servos out
                - probably should be press and hold
        - Intake Control
            - Push button deploy/retract
                - This action is servo driven, so we can hit specific targets
                - Do we want a manual override? If yes, throw in joystick control
                    - pull down to deploy
                    - push up to retract
            - Push button control for intaking
                - Motors in
                - Motors out
        - Conveyor Control
            - Most of the time, the conveyor probably should be tied in with other
             systems, however, it's probably wise to also include manual overrides
            - Moving up/down
                - D-pad control preferred
                - up for up
                - down for down
        - Shooter control
            - Push button to start acceleration
                - Shooter does not get up to speed super fast, operator should be
                able to do this early without a commit to shoot yet
            - Push button to shot ring
                - Ideally, this should only be doable if the shooter is up to
                speed, the shooter will be encoder driven, so this should be
                checkable
            - Push button to stop shooter
            - Do we want/need to be able to spin it backwards during a match?
         */
    }

    public void getTelemetry() {
        // Show the elapsed game time
        telemetry.addData("Run Time: ", runtime.toString());

        // Telemetry about motion
        //telemetry.addData("Motors", "leftFront (%.2f), rightFront (%.2f), rightRear (%.2f), leftRear (%.2f)", telemValues[0], telemValues[1], telemValues[2], telemValues[3]);
        telemetry.update();
    }  // getTelemetry

}    // The Almighty Curly Brace For Everything

