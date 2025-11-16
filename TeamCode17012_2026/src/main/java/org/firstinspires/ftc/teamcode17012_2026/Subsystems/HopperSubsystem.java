package org.firstinspires.ftc.teamcode17012_2026.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode17012_2026.Constants;
import org.firstinspires.ftc.teamcode17012_2026.Constants.ArtifactColor;
import org.firstinspires.ftc.teamcode17012_2026.Constants.HopperConstants;

/**
 * HopperSubsystem - Controls hopper feeding mechanisms for green and purple artifacts
 * Uses continuous rotation servos to feed artifacts into the shooter
 */
public class HopperSubsystem {

    private CRServo greenServo;
    private CRServo purpleServo;
    private Telemetry telemetry;

    private ElapsedTime feedTimer = new ElapsedTime();
    private boolean isFeeding = false;
    private ArtifactColor currentlyFeeding = ArtifactColor.UNKNOWN;

    public HopperSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // Initialize servos
        greenServo = hardwareMap.get(CRServo.class, Constants.HopperHardware.GREEN_SERVO);
        purpleServo = hardwareMap.get(CRServo.class, Constants.HopperHardware.PURPLE_SERVO);

        // Stop both servos initially
        stopAll();
    }

    /**
     * Feeds a green artifact into the shooter
     */
    public void feedGreen() {
        greenServo.setPower(HopperConstants.FEED_SPEED);
        purpleServo.setPower(HopperConstants.STOP_SPEED);
        isFeeding = true;
        currentlyFeeding = ArtifactColor.GREEN;
        feedTimer.reset();
    }

    /**
     * Feeds a purple artifact into the shooter
     */
    public void feedPurple() {
        greenServo.setPower(HopperConstants.STOP_SPEED);
        purpleServo.setPower(HopperConstants.FEED_SPEED);
        isFeeding = true;
        currentlyFeeding = ArtifactColor.PURPLE;
        feedTimer.reset();
    }

    /**
     * Feeds an artifact based on color
     * @param color GREEN or PURPLE
     */
    public void feed(ArtifactColor color) {
        if (color == ArtifactColor.GREEN) {
            feedGreen();
        } else if (color == ArtifactColor.PURPLE) {
            feedPurple();
        } else {
            stopAll();
        }
    }

    /**
     * Stops all hopper servos
     */
    public void stopAll() {
        greenServo.setPower(HopperConstants.STOP_SPEED);
        purpleServo.setPower(HopperConstants.STOP_SPEED);
        isFeeding = false;
        currentlyFeeding = ArtifactColor.UNKNOWN;
    }

    /**
     * Checks if currently feeding an artifact
     * @return True if a feed operation is in progress
     */
    public boolean isFeeding() {
        return isFeeding;
    }

    /**
     * Gets the color currently being fed
     * @return Current artifact color being fed
     */
    public ArtifactColor getCurrentlyFeeding() {
        return currentlyFeeding;
    }

    /**
     * Gets the elapsed time since feed started
     * @return Milliseconds since feed began
     */
    public double getFeedElapsedTime() {
        return feedTimer.milliseconds();
    }

    /**
     * Checks if the current feed operation has completed
     * (based on feed time constant)
     * @return True if feed time has elapsed
     */
    public boolean isFeedComplete() {
        return isFeeding && (feedTimer.milliseconds() >= HopperConstants.FEED_TIME_MS);
    }

    /**
     * Automatically stops feeding if feed time has elapsed
     * Call this periodically in your loop
     */
    public void updateFeedState() {
        if (isFeedComplete()) {
            stopAll();
        }
    }

    /**
     * Reverses the green hopper (for unjamming)
     */
    public void reverseGreen() {
        greenServo.setPower(HopperConstants.REVERSE_SPEED);
    }

    /**
     * Reverses the purple hopper (for unjamming)
     */
    public void reversePurple() {
        purpleServo.setPower(HopperConstants.REVERSE_SPEED);
    }

    /**
     * Adds telemetry data for debugging
     */
    public void addTelemetry() {
        telemetry.addData("Hopper Feeding", isFeeding ? currentlyFeeding.toString() : "STOPPED");
        if (isFeeding) {
            telemetry.addData("Feed Time", "%.1f ms", feedTimer.milliseconds());
            telemetry.addData("Feed Complete", isFeedComplete() ? "YES" : "NO");
        }
        telemetry.addData("Green Servo Power", greenServo.getPower());
        telemetry.addData("Purple Servo Power", purpleServo.getPower());
    }
}
