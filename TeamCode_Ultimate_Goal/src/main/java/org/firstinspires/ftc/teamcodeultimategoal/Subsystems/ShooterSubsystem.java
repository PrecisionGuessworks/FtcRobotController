package org.firstinspires.ftc.teamcodeultimategoal.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcodeultimategoal.BotUtilities;

// THE PEW-PEW SUBSYSTEM
public class ShooterSubsystem {
    private DcMotor flywheel;
    private Telemetry telemetry;
    BotUtilities botStuff;

    private static final double SHOOTERPOWER = 0.7;

    public ShooterSubsystem (HardwareMap hardwareMap, Telemetry telemetry) {
        flywheel = hardwareMap.get(DcMotor.class, "shooterMotor");
        flywheel.setDirection(DcMotor.Direction.REVERSE);
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        botStuff = new BotUtilities(telemetry);
        this.telemetry = telemetry;
    }

    public void setPower(double power) {
        flywheel.setPower(power);
    }
    public void revUp () {
        setPower(SHOOTERPOWER);
    }



    /*
    public void setShooterSpeed(double flywheelSpeed, double acceleratorSpeed) {
        setFlywheel(flywheelSpeed);
    }*/

    public int[] readShooterEncoders() {
        int encoderValues[] = new int[2];
        encoderValues[1] = botStuff.getEncoderValue(flywheel);
        return encoderValues;
    }

}
