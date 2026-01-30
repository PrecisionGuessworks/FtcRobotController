package org.firstinspires.ftc.teamcode18638.Subsystems;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode18638.Constants;

public class Arm {

    // TODO 1: Create armRight and armLeft variables. They are DcMotors.
    private DcMotor rightArm;
    private DcMotor leftArm;

    // Do not edit
    Telemetry telemetry;

    public Arm(HardwareMap hardwareMap, Telemetry telemetry) {
        // TODO 2: Link the motor variables to their physical counterparts
        leftArm = hardwareMap.get(DcMotor.class, "leftArm");
        rightArm = hardwareMap.get(DcMotor.class, "rightArm");

        // TODO 3: Set Motor Directions
        rightArm.setDirection(DcMotorSimple.Direction.REVERSE);

        // No Edit
        leftArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // TODO 4: Set Motor Zero Behavior (BRAKE MODE PLEASE)
        leftArm.setZeroPowerBehavior(BRAKE);
        rightArm.setZeroPowerBehavior(BRAKE);

        // No Edit
        leftArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.telemetry = telemetry;
    }


    public double deadband(double x) {
        // if x is greater than 0.1 or x is less than -0.1, return x. Otherwise return 0.0
        if (Math.abs(x) > 0.1){
            return x;
        } else {
            return 0.0;
        }
    }   // deadband

    public boolean humanControlRequested(double input){
        if (Math.abs(input) > 0.1) {
            return true;
        } else {
            return false;
        }
    }

    // Arm Start Behvior
    public void armSetupProcedure(){
        leftArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftArm.setTargetPosition(Constants.ARM_HOME_POSITION);
        rightArm.setTargetPosition(Constants.ARM_HOME_POSITION);
        leftArm.setPower(1.0);
        rightArm.setPower(1.0);
        leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    // TODO: Finish Set Arm Power Method
    public void setArmPower(double pow){
        pow = pow * 0.5;
        leftArm.setPower(pow);
        rightArm.setPower(pow);

    }

    // Arm - Set Encoder Modes
    public void setRunWithoutEncoderMode(){
        leftArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void setRunToPositionMode(){
        leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void setTargetPositionsToCurrent(){
        leftArm.setTargetPosition(leftArm.getCurrentPosition());
        rightArm.setTargetPosition(rightArm.getCurrentPosition());
    }
    public void stopAndResetEncoder(){
        leftArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void setTargetPositionTo(int position){
        leftArm.setTargetPosition(position);
        rightArm.setTargetPosition(position);
    }

    public double getArmPosition(){
        return leftArm.getCurrentPosition();
    }

    public boolean checksForWatchdog(){
        return leftArm.getMode() == DcMotor.RunMode.RUN_TO_POSITION &&
                leftArm.getTargetPosition() <= Constants.ARM_SHUTDOWN_THRESHOLD &&
                leftArm.getCurrentPosition() <= Constants.ARM_SHUTDOWN_THRESHOLD;
    }
}
