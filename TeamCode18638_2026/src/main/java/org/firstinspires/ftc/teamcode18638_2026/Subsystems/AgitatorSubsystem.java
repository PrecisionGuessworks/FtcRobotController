package org.firstinspires.ftc.teamcode18638_2026.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode18638_2026.Constants;
import org.firstinspires.ftc.teamcode18638_2026.Constants.AgitatorConstants;

/**
 * AgitatorSubsystem - Controls the continuous rotation servo in the hopper
 * Used to agitate balls and help feed them to the flywheel
 */
public class AgitatorSubsystem {

    private CRServo agitatorServo;
    private Telemetry telemetry;

    private AgitatorState currentState = AgitatorState.STOPPED;

    public enum AgitatorState {
        FORWARD,
        REVERSE,
        STOPPED
    }

    public AgitatorSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // Initialize CRServo
        agitatorServo = hardwareMap.get(CRServo.class, Constants.AgitatorHardware.AGITATOR_SERVO);

        // Ensure servo starts stopped
        agitatorServo.setPower(AgitatorConstants.STOP_POWER);
    }

    /**
     * Runs agitator forward
     */
    public void forward() {
        currentState = AgitatorState.FORWARD;
        agitatorServo.setPower(AgitatorConstants.FORWARD_POWER);
    }

    /**
     * Runs agitator in reverse
     */
    public void reverse() {
        currentState = AgitatorState.REVERSE;
        agitatorServo.setPower(AgitatorConstants.REVERSE_POWER);
    }

    /**
     * Stops the agitator
     */
    public void stop() {
        currentState = AgitatorState.STOPPED;
        agitatorServo.setPower(AgitatorConstants.STOP_POWER);
    }

    /**
     * Gets the current agitator state
     * @return Current AgitatorState
     */
    public AgitatorState getState() {
        return currentState;
    }

    /**
     * Checks if agitator is currently running
     * @return true if agitator is running (forward or reverse)
     */
    public boolean isRunning() {
        return currentState != AgitatorState.STOPPED;
    }

    /**
     * Adds telemetry data for debugging
     */
    public void addTelemetry() {
        telemetry.addData("Agitator State", currentState.toString());
        telemetry.addData("Agitator Power", "%.2f", agitatorServo.getPower());
    }
}
