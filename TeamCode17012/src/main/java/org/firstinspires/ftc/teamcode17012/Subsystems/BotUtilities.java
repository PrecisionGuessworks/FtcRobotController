package org.firstinspires.ftc.teamcode17012.Subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BotUtilities {
    Telemetry telemetry;

    public BotUtilities(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // no-op
        }
    }
}
