package org.firstinspires.ftc.teamcode17012_2026.Subsystems;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode17012_2026.Constants.ArtifactColor;
import org.firstinspires.ftc.teamcode17012_2026.Constants.HopperConstants;
import org.firstinspires.ftc.teamcode17012_2026.Constants.ScoringPattern;

/**
 * ScoringSequence - Manages auto-launch sequence for shooting artifacts
 * Uses state machine to control shooter spin-up, hopper feeding, and shot timing
 */
public class ScoringSequence {

    private ShooterSubsystem shooter;
    private HopperSubsystem hopper;
    private LimelightSubsystem limelight;
    private Telemetry telemetry;

    private ElapsedTime sequenceTimer = new ElapsedTime();
    private SequenceState currentState = SequenceState.IDLE;
    private int currentShotIndex = 0;
    private ArtifactColor[] shotSequence;

    private double targetDistance = 60.0; // Default distance in inches

    public enum SequenceState {
        IDLE,               // Not running
        SPINNING_UP,        // Shooter spinning up to target velocity
        READY_TO_FEED,      // Shooter at speed, ready for first ball
        FEEDING,            // Currently feeding a ball
        WAITING_FOR_NEXT,   // Delay between shots
        COMPLETE            // All shots fired
    }

    public ScoringSequence(ShooterSubsystem shooter, HopperSubsystem hopper,
                           LimelightSubsystem limelight, Telemetry telemetry) {
        this.shooter = shooter;
        this.hopper = hopper;
        this.limelight = limelight;
        this.telemetry = telemetry;
    }

    /**
     * Starts the auto-launch sequence with current scoring pattern from Limelight
     */
    public void startAutoLaunch() {
        ScoringPattern pattern = limelight.getScoringPattern();
        startAutoLaunch(pattern);
    }

    /**
     * Starts the auto-launch sequence with a specific scoring pattern
     * @param pattern The scoring pattern to use
     */
    public void startAutoLaunch(ScoringPattern pattern) {
        if (pattern == ScoringPattern.UNKNOWN) {
            telemetry.addData("Auto-Launch", "ERROR: Unknown scoring pattern!");
            return;
        }

        shotSequence = pattern.getSequence();
        currentShotIndex = 0;
        currentState = SequenceState.SPINNING_UP;
        sequenceTimer.reset();

        // Update shooter velocity based on distance to goal
        updateShooterVelocity();

        telemetry.addData("Auto-Launch", "Started with pattern: " + pattern.toString());
    }

    /**
     * Updates the sequence state machine
     * Call this in your teleop/auto loop
     */
    public void update() {
        switch (currentState) {
            case IDLE:
                // Do nothing
                break;

            case SPINNING_UP:
                // Wait for shooter to reach target velocity
                if (shooter.isReadyToShoot()) {
                    currentState = SequenceState.READY_TO_FEED;
                    sequenceTimer.reset();
                }
                break;

            case READY_TO_FEED:
                // Feed the next ball
                if (currentShotIndex < shotSequence.length) {
                    ArtifactColor colorToShoot = shotSequence[currentShotIndex];
                    hopper.feed(colorToShoot);
                    currentState = SequenceState.FEEDING;
                    sequenceTimer.reset();
                } else {
                    // All shots complete
                    complete();
                }
                break;

            case FEEDING:
                // Wait for feed to complete
                if (hopper.isFeedComplete()) {
                    hopper.stopAll();
                    currentShotIndex++;

                    // Check if more shots remain
                    if (currentShotIndex < shotSequence.length) {
                        currentState = SequenceState.WAITING_FOR_NEXT;
                        sequenceTimer.reset();
                    } else {
                        complete();
                    }
                }
                break;

            case WAITING_FOR_NEXT:
                // Delay between shots
                if (sequenceTimer.milliseconds() >= HopperConstants.SHOT_DELAY_MS) {
                    currentState = SequenceState.READY_TO_FEED;
                    sequenceTimer.reset();
                }
                break;

            case COMPLETE:
                // Sequence finished
                break;
        }

        // Auto-update hopper state
        hopper.updateFeedState();
    }

    /**
     * Completes the sequence and stops the shooter
     */
    private void complete() {
        currentState = SequenceState.COMPLETE;
        shooter.stop();
        hopper.stopAll();
        telemetry.addData("Auto-Launch", "COMPLETE - %d shots fired", currentShotIndex);
    }

    /**
     * Cancels the current sequence
     */
    public void cancel() {
        currentState = SequenceState.IDLE;
        shooter.stop();
        hopper.stopAll();
        currentShotIndex = 0;
    }

    /**
     * Updates shooter velocity based on distance from Limelight
     */
    public void updateShooterVelocity() {
        double distance = limelight.getGoalDistance();

        // Use default distance if no goal tag visible
        if (distance <= 0) {
            distance = targetDistance;
        } else {
            targetDistance = distance;
        }

        shooter.setVelocityForDistance(distance);
    }

    /**
     * Manually sets target distance (for manual shooting)
     * @param distanceInches Distance to target in inches
     */
    public void setTargetDistance(double distanceInches) {
        this.targetDistance = distanceInches;
        shooter.setVelocityForDistance(distanceInches);
    }

    /**
     * Checks if the sequence is currently running
     * @return True if sequence is active
     */
    public boolean isRunning() {
        return currentState != SequenceState.IDLE && currentState != SequenceState.COMPLETE;
    }

    /**
     * Checks if the sequence is complete
     * @return True if all shots have been fired
     */
    public boolean isComplete() {
        return currentState == SequenceState.COMPLETE;
    }

    /**
     * Gets the current sequence state
     * @return Current state
     */
    public SequenceState getState() {
        return currentState;
    }

    /**
     * Gets the number of shots fired so far
     * @return Shot count
     */
    public int getShotsFired() {
        return currentShotIndex;
    }

    /**
     * Gets the total number of shots in sequence
     * @return Total shots
     */
    public int getTotalShots() {
        return shotSequence != null ? shotSequence.length : 0;
    }

    /**
     * Adds telemetry data for debugging
     */
    public void addTelemetry() {
        telemetry.addData("Sequence State", currentState.toString());

        if (isRunning()) {
            telemetry.addData("Shot Progress", "%d / %d", currentShotIndex, getTotalShots());

            if (currentShotIndex < shotSequence.length) {
                telemetry.addData("Next Shot", shotSequence[currentShotIndex].toString());
            }

            telemetry.addData("Sequence Time", "%.1f sec", sequenceTimer.seconds());
        }

        telemetry.addData("Target Distance", "%.1f inches", targetDistance);
    }
}
