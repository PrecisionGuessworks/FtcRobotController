package org.firstinspires.ftc.teamcode17012_2026.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode17012_2026.Constants;
import org.firstinspires.ftc.teamcode17012_2026.Constants.CatapultConstants;

/**
 * CatapultSubsystem - Controls the dual-motor catapult/pivot mechanism
 * Uses two motors working together to pivot the mechanism up and down
 */
public class CatapultSubsystem {

    private DcMotor catapultMotor1;
    private DcMotor catapultMotor2;
    private Telemetry telemetry;

    private CatapultMode currentMode = CatapultMode.HOLD;

    public enum CatapultMode {
        UP,     // Pivoting up (launch position)
        DOWN,   // Pivoting down (stowed position)
        HOLD    // Holding current position with feed-forward
    }

    public CatapultSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // Initialize motors
        catapultMotor1 = hardwareMap.get(DcMotor.class, Constants.CatapultHardware.CATAPULT_MOTOR_1);
        catapultMotor2 = hardwareMap.get(DcMotor.class, Constants.CatapultHardware.CATAPULT_MOTOR_2);

        // Set motor directions (motors work in opposite physical orientations)
        catapultMotor1.setDirection(DcMotor.Direction.REVERSE);
        catapultMotor2.setDirection(DcMotor.Direction.FORWARD);

        // Set motor modes
        catapultMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        catapultMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set zero power behavior to brake
        catapultMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        catapultMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Pivots the catapult up (toward launch position)
     */
    public void pivotUp() {
        currentMode = CatapultMode.UP;
        catapultMotor1.setPower(CatapultConstants.UP_POWER);
        catapultMotor2.setPower(CatapultConstants.UP_POWER);
    }

    /**
     * Pivots the catapult down (toward stowed position)
     */
    public void pivotDown() {
        currentMode = CatapultMode.DOWN;
        catapultMotor1.setPower(CatapultConstants.DOWN_POWER);
        catapultMotor2.setPower(CatapultConstants.DOWN_POWER);
    }

    /**
     * Holds the catapult in current position with feed-forward power
     * Provides slight power to counteract gravity
     */
    public void hold() {
        currentMode = CatapultMode.HOLD;
        catapultMotor1.setPower(CatapultConstants.HOLD_POWER);
        catapultMotor2.setPower(CatapultConstants.HOLD_POWER);
    }

    /**
     * Stops both motors (no power, relies on brake mode)
     */
    public void stop() {
        currentMode = CatapultMode.HOLD;
        catapultMotor1.setPower(0);
        catapultMotor2.setPower(0);
    }

    /**
     * Gets the current catapult mode
     * @return Current CatapultMode
     */
    public CatapultMode getMode() {
        return currentMode;
    }

    /**
     * Gets the current position of catapult motor 1 (for debugging)
     * @return Encoder position of motor 1
     */
    public int getMotor1Position() {
        return catapultMotor1.getCurrentPosition();
    }

    /**
     * Gets the current position of catapult motor 2 (for debugging)
     * @return Encoder position of motor 2
     */
    public int getMotor2Position() {
        return catapultMotor2.getCurrentPosition();
    }

    /**
     * Adds telemetry data for debugging
     */
    public void addTelemetry() {
        telemetry.addData("Catapult Mode", currentMode.toString());
        telemetry.addData("Catapult Motor1", "pos=%d, power=%.2f",
                catapultMotor1.getCurrentPosition(), catapultMotor1.getPower());
        telemetry.addData("Catapult Motor2", "pos=%d, power=%.2f",
                catapultMotor2.getCurrentPosition(), catapultMotor2.getPower());
    }
}
