package org.firstinspires.ftc.teamcode31953_2026.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode31953_2026.Constants;
import org.firstinspires.ftc.teamcode31953_2026.Constants.FootConstants;

/**
 * FootSubsystem - Controls the stabilizing foot mechanism
 * The foot deploys to provide stability during certain operations
 */
public class FootSubsystem {

    private DcMotor footMotor;
    private Telemetry telemetry;

    private FootMode currentMode = FootMode.BRAKE;

    public enum FootMode {
        UP,    // Foot raised (stowed position)
        DOWN,  // Foot lowered (deployed for stability)
        BRAKE  // Stopped and holding position
    }

    public FootSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // Initialize motor
        footMotor = hardwareMap.get(DcMotor.class, Constants.FootHardware.FOOT_MOTOR);

        // Set motor direction (reverse so "up" is stowed)
        footMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set motor mode
        footMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set zero power behavior to brake for holding position
        footMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Raises the foot (stowed position)
     */
    public void raise() {
        currentMode = FootMode.UP;
        footMotor.setPower(FootConstants.UP_POWER);
    }

    /**
     * Lowers the foot (deployed for stability)
     */
    public void lower() {
        currentMode = FootMode.DOWN;
        footMotor.setPower(FootConstants.DOWN_POWER);
    }

    /**
     * Stops the foot motor and holds position via brake mode
     */
    public void stop() {
        currentMode = FootMode.BRAKE;
        footMotor.setPower(0);
    }

    /**
     * Gets the current foot mode
     * @return Current FootMode
     */
    public FootMode getMode() {
        return currentMode;
    }

    /**
     * Gets the current position of the foot motor (for debugging)
     * @return Encoder position of the motor
     */
    public int getPosition() {
        return footMotor.getCurrentPosition();
    }

    /**
     * Adds telemetry data for debugging
     */
    public void addTelemetry() {
        telemetry.addData("Foot Mode", currentMode.toString());
        telemetry.addData("Foot Motor", "pos=%d, power=%.2f",
                footMotor.getCurrentPosition(), footMotor.getPower());
    }
}
