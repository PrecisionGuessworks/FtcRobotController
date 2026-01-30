package org.firstinspires.ftc.teamcode18638_2026.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode18638_2026.Constants;
import org.firstinspires.ftc.teamcode18638_2026.Constants.FeederConstants;

/**
 * FeederSubsystem - Controls the feeder motor via Rev SparkMini motor controller
 * SparkMini is connected to a PWM port and accessed as a CRServo
 */
public class FeederSubsystem {

    private CRServo feederMotor;  // Connected via SparkMini motor controller
    private Telemetry telemetry;

    private FeederState currentState = FeederState.STOPPED;

    public enum FeederState {
        FEEDING,
        REVERSING,
        STOPPED
    }

    public FeederSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // Initialize CRServo (SparkMini motor controller on PWM port)
        feederMotor = hardwareMap.get(CRServo.class, Constants.FeederHardware.FEEDER_MOTOR);

        // Set motor direction via CRServo.Direction
        feederMotor.setDirection(CRServo.Direction.FORWARD);
    }

    /**
     * Runs feeder to feed balls to flywheel (full power)
     */
    public void feed() {
        currentState = FeederState.FEEDING;
        feederMotor.setPower(FeederConstants.FEED_POWER);
    }

    /**
     * Runs feeder forward at manual (reduced) power
     */
    public void feedManual() {
        currentState = FeederState.FEEDING;
        feederMotor.setPower(FeederConstants.MANUAL_POWER);
    }

    /**
     * Reverses feeder to clear jams
     */
    public void reverse() {
        currentState = FeederState.REVERSING;
        feederMotor.setPower(FeederConstants.REVERSE_POWER);
    }

    /**
     * Stops the feeder
     */
    public void stop() {
        currentState = FeederState.STOPPED;
        feederMotor.setPower(0);
    }

    /**
     * Gets the current feeder state
     * @return Current FeederState
     */
    public FeederState getState() {
        return currentState;
    }

    /**
     * Adds telemetry data for debugging
     */
    public void addTelemetry() {
        telemetry.addData("Feeder State", currentState.toString());
        telemetry.addData("Feeder Power", "%.2f", feederMotor.getPower());
    }
}
