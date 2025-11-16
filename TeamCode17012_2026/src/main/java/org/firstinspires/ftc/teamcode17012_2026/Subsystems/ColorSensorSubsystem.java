package org.firstinspires.ftc.teamcode17012_2026.Subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode17012_2026.Constants;
import org.firstinspires.ftc.teamcode17012_2026.Constants.ArtifactColor;
import org.firstinspires.ftc.teamcode17012_2026.Constants.ColorSensorConstants;

/**
 * ColorSensorSubsystem - Manages three REV Color Sensor V3s for artifact detection
 * Uses voting logic to determine artifact color even when some sensors see holes
 */
public class ColorSensorSubsystem {

    private ColorSensor sensor1;
    private ColorSensor sensor2;
    private ColorSensor sensor3;

    private Telemetry telemetry;

    private ArtifactColor lastDetectedColor = ArtifactColor.UNKNOWN;

    public ColorSensorSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // Initialize all three color sensors
        sensor1 = hardwareMap.get(ColorSensor.class, Constants.SensorHardware.COLOR_SENSOR_1);
        sensor2 = hardwareMap.get(ColorSensor.class, Constants.SensorHardware.COLOR_SENSOR_2);
        sensor3 = hardwareMap.get(ColorSensor.class, Constants.SensorHardware.COLOR_SENSOR_3);
    }

    /**
     * Reads all three sensors and determines artifact color using voting logic
     * @return The detected artifact color (GREEN, PURPLE, or UNKNOWN)
     */
    public ArtifactColor detectArtifactColor() {
        // Get individual sensor readings
        ArtifactColor color1 = detectColorFromSensor(sensor1);
        ArtifactColor color2 = detectColorFromSensor(sensor2);
        ArtifactColor color3 = detectColorFromSensor(sensor3);

        // Count votes for each color
        int greenVotes = 0;
        int purpleVotes = 0;

        if (color1 == ArtifactColor.GREEN) greenVotes++;
        else if (color1 == ArtifactColor.PURPLE) purpleVotes++;

        if (color2 == ArtifactColor.GREEN) greenVotes++;
        else if (color2 == ArtifactColor.PURPLE) purpleVotes++;

        if (color3 == ArtifactColor.GREEN) greenVotes++;
        else if (color3 == ArtifactColor.PURPLE) purpleVotes++;

        // Determine color based on voting (requires 2+ sensors to agree)
        ArtifactColor detectedColor;
        if (greenVotes >= ColorSensorConstants.MIN_SENSORS_REQUIRED) {
            detectedColor = ArtifactColor.GREEN;
            lastDetectedColor = ArtifactColor.GREEN;
        } else if (purpleVotes >= ColorSensorConstants.MIN_SENSORS_REQUIRED) {
            detectedColor = ArtifactColor.PURPLE;
            lastDetectedColor = ArtifactColor.PURPLE;
        } else {
            // No consensus - use last known color or UNKNOWN
            detectedColor = lastDetectedColor;
        }

        return detectedColor;
    }

    /**
     * Detects color from a single sensor using HSV thresholds
     * @param sensor The color sensor to read
     * @return The detected color (GREEN, PURPLE, or UNKNOWN)
     */
    private ArtifactColor detectColorFromSensor(ColorSensor sensor) {
        // Get RGB values from sensor
        int red = sensor.red();
        int green = sensor.green();
        int blue = sensor.blue();

        // Convert RGB to HSV
        float[] hsv = new float[3];
        rgbToHsv(red, green, blue, hsv);

        float hue = hsv[0];
        float saturation = hsv[1];
        float value = hsv[2];

        // Check if value is sufficient (not looking at a hole or nothing)
        if (value < ColorSensorConstants.MIN_VALUE) {
            return ArtifactColor.UNKNOWN;
        }

        // Check for green
        if (hue >= ColorSensorConstants.GREEN_HUE_MIN &&
            hue <= ColorSensorConstants.GREEN_HUE_MAX &&
            saturation >= ColorSensorConstants.GREEN_SAT_MIN) {
            return ArtifactColor.GREEN;
        }

        // Check for purple
        if (hue >= ColorSensorConstants.PURPLE_HUE_MIN &&
            hue <= ColorSensorConstants.PURPLE_HUE_MAX &&
            saturation >= ColorSensorConstants.PURPLE_SAT_MIN) {
            return ArtifactColor.PURPLE;
        }

        return ArtifactColor.UNKNOWN;
    }

    /**
     * Converts RGB values to HSV
     * @param r Red value (0-255)
     * @param g Green value (0-255)
     * @param b Blue value (0-255)
     * @param hsv Output array [hue (0-360), saturation (0-1), value (0-1)]
     */
    private void rgbToHsv(int r, int g, int b, float[] hsv) {
        float rf = r / 255.0f;
        float gf = g / 255.0f;
        float bf = b / 255.0f;

        float max = Math.max(rf, Math.max(gf, bf));
        float min = Math.min(rf, Math.min(gf, bf));
        float delta = max - min;

        // Calculate hue
        float hue = 0;
        if (delta != 0) {
            if (max == rf) {
                hue = 60 * (((gf - bf) / delta) % 6);
            } else if (max == gf) {
                hue = 60 * (((bf - rf) / delta) + 2);
            } else {
                hue = 60 * (((rf - gf) / delta) + 4);
            }
        }
        if (hue < 0) hue += 360;

        // Calculate saturation
        float saturation = (max == 0) ? 0 : (delta / max);

        // Value is just max
        float value = max;

        hsv[0] = hue;
        hsv[1] = saturation;
        hsv[2] = value;
    }

    /**
     * Gets the last reliably detected color (ignores temporary UNKNOWN readings)
     * @return Last detected artifact color
     */
    public ArtifactColor getLastDetectedColor() {
        return lastDetectedColor;
    }

    /**
     * Adds telemetry data for debugging color sensor readings
     */
    public void addTelemetry() {
        ArtifactColor current = detectArtifactColor();
        telemetry.addData("Artifact Color", current.toString());
        telemetry.addData("Last Known Color", lastDetectedColor.toString());

        // Individual sensor debug info
        telemetry.addData("Sensor 1 RGB", String.format("(%d, %d, %d)",
            sensor1.red(), sensor1.green(), sensor1.blue()));
        telemetry.addData("Sensor 2 RGB", String.format("(%d, %d, %d)",
            sensor2.red(), sensor2.green(), sensor2.blue()));
        telemetry.addData("Sensor 3 RGB", String.format("(%d, %d, %d)",
            sensor3.red(), sensor3.green(), sensor3.blue()));
    }
}
