package org.firstinspires.ftc.teamcode31953_2026.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode31953_2026.Constants;
import org.firstinspires.ftc.teamcode31953_2026.Constants.IntakeConstants;

/**
 * IntakeSubsystem - Simple intake roller control
 * Runs forward to intake, backward to outtake
 */
public class IntakeSubsystem {

    private DcMotor intakeMotor;
    private Telemetry telemetry;

    private IntakeState currentState = IntakeState.STOPPED;

    public enum IntakeState {
        INTAKING,
        OUTTAKING,
        STOPPED
    }

    public IntakeSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // Initialize motor
        intakeMotor = hardwareMap.get(DcMotor.class, Constants.IntakeHardware.INTAKE_MOTOR);

        // Set motor direction (forward should intake)
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);

        // Set motor mode
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set zero power behavior to brake
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Starts intaking (motor runs forward)
     */
    public void startIntake() {
        currentState = IntakeState.INTAKING;
        intakeMotor.setPower(IntakeConstants.IN_POWER);
    }

    /**
     * Starts outtaking (motor runs backward)
     */
    public void startOuttake() {
        currentState = IntakeState.OUTTAKING;
        intakeMotor.setPower(IntakeConstants.OUT_POWER);
    }

    /**
     * Stops the intake motor
     */
    public void stop() {
        currentState = IntakeState.STOPPED;
        intakeMotor.setPower(IntakeConstants.OFF_POWER);
    }

    /**
     * Gets the current intake state
     * @return Current IntakeState
     */
    public IntakeState getState() {
        return currentState;
    }

    /**
     * Adds telemetry data for debugging
     */
    public void addTelemetry() {
        telemetry.addData("Intake State", currentState.toString());
        telemetry.addData("Intake Power", "%.2f", intakeMotor.getPower());
    }
}
