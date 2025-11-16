package org.firstinspires.ftc.teamcode17012_2026.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode17012_2026.Constants;
import org.firstinspires.ftc.teamcode17012_2026.Constants.LimelightConstants;
import org.firstinspires.ftc.teamcode17012_2026.Constants.ScoringPattern;

import java.util.List;

/**
 * LimelightSubsystem - Manages Limelight 3A for AprilTag detection
 * Detects obelisk tags (21, 22, 23) for scoring patterns
 * Detects goal tags (20, 24) for distance-based shooter adjustment
 */
public class LimelightSubsystem {

    private Limelight3A limelight;
    private Telemetry telemetry;

    private ScoringPattern currentPattern = ScoringPattern.UNKNOWN;
    private double lastGoalDistance = 0.0;
    private int lastDetectedTagId = -1;

    public LimelightSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        try {
            // Initialize Limelight
            limelight = hardwareMap.get(Limelight3A.class, Constants.VisionHardware.LIMELIGHT);

            // Set pipeline to AprilTag detection
            limelight.pipelineSwitch(LimelightConstants.APRILTAG_PIPELINE);

            // Start the Limelight
            limelight.start();

        } catch (Exception e) {
            telemetry.addData("Limelight Error", "Failed to initialize: " + e.getMessage());
            telemetry.update();
        }
    }

    /**
     * Updates Limelight and processes latest results
     * Should be called periodically in loop
     */
    public void update() {
        if (limelight == null) {
            return;
        }

        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            processAprilTagResults(result);
        }
    }

    /**
     * Processes AprilTag detection results
     * @param result Latest Limelight result
     */
    private void processAprilTagResults(LLResult result) {
        List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();

        if (fiducials == null || fiducials.isEmpty()) {
            return;
        }

        // Process the first (closest/most confident) tag
        LLResultTypes.FiducialResult tag = fiducials.get(0);
        int tagId = (int) tag.getFiducialId();
        lastDetectedTagId = tagId;

        // Check if it's an obelisk tag (scoring pattern)
        if (isObeliskTag(tagId)) {
            currentPattern = ScoringPattern.fromAprilTagId(tagId);
        }

        // Check if it's a goal tag (for distance calculation)
        if (isGoalTag(tagId)) {
            updateGoalDistance(tag);
        }
    }

    /**
     * Scans for obelisk AprilTags (21, 22, 23) to determine scoring pattern
     * Blocks until a tag is found or timeout occurs
     * @param timeoutMs Maximum time to search in milliseconds
     * @return The detected scoring pattern (or UNKNOWN if not found)
     */
    public ScoringPattern scanForObeliskTag(double timeoutMs) {
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeoutMs) {
            update();

            if (currentPattern != ScoringPattern.UNKNOWN) {
                telemetry.addData("Obelisk Tag Found", "Tag %d: %s",
                    lastDetectedTagId, currentPattern.toString());
                telemetry.update();
                return currentPattern;
            }

            try {
                Thread.sleep(50); // Small delay between checks
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        telemetry.addData("Obelisk Tag", "NOT FOUND (timeout)");
        telemetry.update();
        return ScoringPattern.UNKNOWN;
    }

    /**
     * Gets the distance to the goal AprilTag in inches
     * @return Distance in inches, or 0 if no goal tag detected
     */
    public double getGoalDistance() {
        return lastGoalDistance;
    }

    /**
     * Updates the distance to goal based on AprilTag detection
     * @param tag The detected fiducial result
     */
    private void updateGoalDistance(LLResultTypes.FiducialResult tag) {
        // Get 3D pose from Limelight
        Pose3D robotPose = tag.getRobotPoseTargetSpace();

        if (robotPose != null) {
            // Calculate distance using X and Y coordinates (ignore Z for 2D distance)
            double x = robotPose.getPosition().x;
            double y = robotPose.getPosition().y;

            // Convert from meters to inches (Limelight uses meters)
            lastGoalDistance = Math.sqrt(x * x + y * y) * 39.3701;
        }
    }

    /**
     * Gets the horizontal angle to a detected AprilTag
     * @return Horizontal angle in degrees (tx value from Limelight)
     */
    public double getHorizontalAngle() {
        if (limelight == null) {
            return 0.0;
        }

        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
            if (fiducials != null && !fiducials.isEmpty()) {
                return fiducials.get(0).getTargetXDegrees();
            }
        }

        return 0.0;
    }

    /**
     * Checks if a tag ID is an obelisk tag
     * @param tagId AprilTag ID
     * @return True if obelisk tag (21, 22, or 23)
     */
    private boolean isObeliskTag(int tagId) {
        return tagId == LimelightConstants.OBELISK_TAG_21 ||
               tagId == LimelightConstants.OBELISK_TAG_22 ||
               tagId == LimelightConstants.OBELISK_TAG_23;
    }

    /**
     * Checks if a tag ID is a goal tag
     * @param tagId AprilTag ID
     * @return True if goal tag (20 or 24)
     */
    private boolean isGoalTag(int tagId) {
        return tagId == LimelightConstants.BLUE_GOAL_TAG ||
               tagId == LimelightConstants.RED_GOAL_TAG;
    }

    /**
     * Gets the current scoring pattern from obelisk tag
     * @return Current scoring pattern
     */
    public ScoringPattern getScoringPattern() {
        return currentPattern;
    }

    /**
     * Gets the last detected AprilTag ID
     * @return Last detected tag ID, or -1 if none
     */
    public int getLastDetectedTagId() {
        return lastDetectedTagId;
    }

    /**
     * Checks if any AprilTag is currently visible
     * @return True if a tag is detected
     */
    public boolean hasTarget() {
        if (limelight == null) {
            return false;
        }

        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
            return fiducials != null && !fiducials.isEmpty();
        }

        return false;
    }

    /**
     * Manually sets the scoring pattern (for testing)
     * @param pattern The scoring pattern to set
     */
    public void setScoringPattern(ScoringPattern pattern) {
        this.currentPattern = pattern;
    }

    /**
     * Resets the scoring pattern to UNKNOWN
     */
    public void resetScoringPattern() {
        this.currentPattern = ScoringPattern.UNKNOWN;
    }

    /**
     * Stops the Limelight
     */
    public void stop() {
        if (limelight != null) {
            limelight.stop();
        }
    }

    /**
     * Adds telemetry data for debugging
     */
    public void addTelemetry() {
        if (limelight == null) {
            telemetry.addData("Limelight", "NOT INITIALIZED");
            return;
        }

        telemetry.addData("Limelight Status", limelight.isConnected() ? "Connected" : "Disconnected");
        telemetry.addData("Has Target", hasTarget() ? "YES" : "NO");

        if (hasTarget()) {
            telemetry.addData("Last Tag ID", lastDetectedTagId);

            if (isGoalTag(lastDetectedTagId)) {
                telemetry.addData("Goal Distance", "%.1f inches", lastGoalDistance);
                telemetry.addData("Horizontal Angle", "%.1f degrees", getHorizontalAngle());
            }
        }

        telemetry.addData("Scoring Pattern", currentPattern.toString());
    }
}
