package org.firstinspires.ftc.teamcode18638.Subsystems;

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
        leftArm.setDirection(DcMotorSimple.Direction.REVERSE);

        // No Edit
        armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // TODO 4: Set Motor Zero Behavior (BRAKE MODE PLEASE)
        leftArm.setZeroPowerBehavior(BRAKE);
        rightArm.setZeroPowerBehavior(BRAKE);

        // No Edit
        armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.telemetry = telemetry;
    }



    // TODO: Create a deadband method (hint, you can copy it from the drivetrain file)
        throttle = deadband(throttle);
        rotation = deadband(rotation);

    public boolean humanControlRequested(double input){
        if (Math.abs(input) > 0.1) {
            return true;
        } else {
            return false;
        }
    }

    // Arm Start Behvior
    public void armSetupProcedure(){
        armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLeft.setTargetPosition(Constants.ARM_HOME_POSITION);
        armRight.setTargetPosition(Constants.ARM_HOME_POSITION);
        armLeft.setPower(1.0);
        armRight.setPower(1.0);
        armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    // TODO: Finish Set Arm Power Method
    public void setArmPower(double leftArm, double rightArm){
        leftArm.setPower(leftArm);
        rightArm.setPower(rightArm);

    }

    // Arm - Set Encoder Modes
    public void setRunWithoutEncoderMode(){
        armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void setRunToPositionMode(){
        armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void setTargetPositionsToCurrent(){
        armLeft.setTargetPosition(armLeft.getCurrentPosition());
        armRight.setTargetPosition(armRight.getCurrentPosition());
    }
    public void stopAndResetEncoder(){
        armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void setTargetPositionTo(int position){
        armLeft.setTargetPosition(position);
        armRight.setTargetPosition(position);
    }

    public boolean checksForWatchdog(){
        return armLeft.getMode() == DcMotor.RunMode.RUN_TO_POSITION &&
                armLeft.getTargetPosition() <= Constants.ARM_SHUTDOWN_THRESHOLD &&
                armLeft.getCurrentPosition() <= Constants.ARM_SHUTDOWN_THRESHOLD;
    }
}
