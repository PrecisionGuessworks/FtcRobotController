package org.firstinspires.ftc.teamcode17012_2026.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode17012_2026.Constants;
import org.firstinspires.ftc.teamcode17012_2026.Constants.ArtifactColor;
import org.firstinspires.ftc.teamcode17012_2026.Constants.IntakeConstants;

/**
 * IntakeSubsystem - Controls intake roller and flipper mechanism
 * Integrates with ColorSensorSubsystem to automatically sort artifacts by color
 */
public class IntakeSubsystem {

    private DcMotor intakeMotor;
    private DcMotor flipperMotor;
    private ColorSensorSubsystem colorSensor;
    private Telemetry telemetry;

    private IntakeState currentState = IntakeState.STOPPED;
    private ArtifactColor lastFlipperDirection = ArtifactColor.UNKNOWN;

    public enum IntakeState {
        INTAKING,
        OUTTAKING,
        STOPPED
    }

    public IntakeSubsystem(HardwareMap hardwareMap, ColorSensorSubsystem colorSensor, Telemetry telemetry) {
        this.colorSensor = colorSensor;
        this.telemetry = telemetry;

        // Initialize motors
        intakeMotor = hardwareMap.get(DcMotor.class, Constants.IntakeHardware.INTAKE_MOTOR);
        flipperMotor = hardwareMap.get(DcMotor.class, Constants.IntakeHardware.FLIPPER_MOTOR);

        // Set motor modes
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flipperMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set zero power behavior
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flipperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Starts intaking artifacts
     * Automatically controls flipper based on color sensor readings
     */
    public void startIntake() {
        currentState = IntakeState.INTAKING;
        intakeMotor.setPower(IntakeConstants.INTAKE_POWER);
    }

    /**
     * Reverses intake to outtake artifacts
     */
    public void startOuttake() {
        currentState = IntakeState.OUTTAKING;
        intakeMotor.setPower(IntakeConstants.OUTTAKE_POWER);
        flipperMotor.setPower(IntakeConstants.FLIPPER_STOP_POWER); // Stop flipper during outtake
    }

    /**
     * Stops both intake and flipper
     */
    public void stop() {
        currentState = IntakeState.STOPPED;
        intakeMotor.setPower(0);
        flipperMotor.setPower(IntakeConstants.FLIPPER_STOP_POWER);
    }

    /**
     * Updates flipper direction based on current color sensor readings
     * Should be called periodically (e.g., in teleop loop)
     */
    public void updateFlipper() {
        // Only control flipper when actively intaking
        if (currentState != IntakeState.INTAKING) {
            return;
        }

        // Get current artifact color from sensors
        ArtifactColor detectedColor = colorSensor.detectArtifactColor();

        // Update flipper based on color
        switch (detectedColor) {
            case GREEN:
                // Green artifacts go left (CCW)
                flipperMotor.setPower(IntakeConstants.FLIPPER_LEFT_POWER);
                lastFlipperDirection = ArtifactColor.GREEN;
                break;

            case PURPLE:
                // Purple artifacts go right (CW)
                flipperMotor.setPower(IntakeConstants.FLIPPER_RIGHT_POWER);
                lastFlipperDirection = ArtifactColor.PURPLE;
                break;

            case UNKNOWN:
                // No clear reading - continue in last direction
                // Don't change flipper power, keep spinning in last known direction
                // (This handles holes in artifacts)
                break;
        }
    }

    /**
     * Manually sets flipper direction (for testing/debugging)
     * @param color GREEN for left (CCW), PURPLE for right (CW), UNKNOWN to stop
     */
    public void setFlipperDirection(ArtifactColor color) {
        switch (color) {
            case GREEN:
                flipperMotor.setPower(IntakeConstants.FLIPPER_LEFT_POWER);
                lastFlipperDirection = ArtifactColor.GREEN;
                break;
            case PURPLE:
                flipperMotor.setPower(IntakeConstants.FLIPPER_RIGHT_POWER);
                lastFlipperDirection = ArtifactColor.PURPLE;
                break;
            case UNKNOWN:
                flipperMotor.setPower(IntakeConstants.FLIPPER_STOP_POWER);
                lastFlipperDirection = ArtifactColor.UNKNOWN;
                break;
        }
    }

    /**
     * Gets the current intake state
     * @return Current IntakeState
     */
    public IntakeState getState() {
        return currentState;
    }

    /**
     * Gets the last flipper direction
     * @return Last artifact color that controlled flipper direction
     */
    public ArtifactColor getLastFlipperDirection() {
        return lastFlipperDirection;
    }

    /**
     * Adds telemetry data for debugging
     */
    public void addTelemetry() {
        telemetry.addData("Intake State", currentState.toString());
        telemetry.addData("Intake Power", intakeMotor.getPower());
        telemetry.addData("Flipper Direction", lastFlipperDirection.toString());
        telemetry.addData("Flipper Power", flipperMotor.getPower());
    }
}
