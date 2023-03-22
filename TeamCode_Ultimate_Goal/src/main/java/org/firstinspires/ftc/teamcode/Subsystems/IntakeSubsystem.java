package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntakeSubsystem {
    private DcMotor intakeMotor;
    private Servo intakeServos;
    Telemetry telemetry;
    //TODO: Check Intake Target Positions
    private static final double DEPLOYTARGET = .995;
    private static final double STOWTARGET = .35;

    public IntakeSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        // Intake Motor
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Intake Servos
        intakeServos = hardwareMap.get(Servo.class, "intakeServos");
        intakeServos.setDirection(Servo.Direction.REVERSE);

        this.telemetry = telemetry;
    }

    public void runIntakeIn(double speed) {
        //Doesn't allow us to run this backwards
        if(speed >= 0.0) {
            intakeMotor.setPower(speed);
        }else{
            System.out.println("Warning: Trying to run Intake In backwards, use " +
                    "\"runIntakeOut\" method instead.");
        }
    }
    public void runIntakeOut(double speed) {
        if(speed >= 0.0) {
            intakeMotor.setPower(-speed);
        }else{
            System.out.println("Warning: Trying to run Intake Out backwards, use " +
                    "\"runIntakeIn\" method instead.");
        }
    }

    public void idle(){
        intakeMotor.setPower(0.0);
    }

    public void deployIntakeArms(){
        intakeServos.setPosition(DEPLOYTARGET);
    }
    public void stowIntakeArms(){
        intakeServos.setPosition(STOWTARGET);
    }
    public void manualAdjustArms(double adjustment){
        double curPos = intakeServos.getPosition();
        double newPos = curPos + adjustment;
        if(newPos < STOWTARGET && newPos > DEPLOYTARGET){
            intakeServos.setPosition(newPos);
        }

    }
}
