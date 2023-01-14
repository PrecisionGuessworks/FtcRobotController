package org.firstinspires.ftc.teamcode18638.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Elevator {
    Telemetry telemetry;

    // Temp motor as motor
    private DcMotor liftMotor;

    public Elevator(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // TODO: Use setDirection to set up the direction of elevator function (Up "Forwards", Down "Backwards")
    }

    /**
     * setElevatorPower
     * @param power
     * basic elevator control. allows for direct human input if desired
     */
    public void setElevatorPower(double power) {
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        power = deadband(power);
        power *= 0.375;
        liftMotor.setPower(power);
    }

    public double deadband(double x) {
        if (x>0.1){
            return x;
        } else if (x<-0.1){
            return x;
        } else {
            return 0;
        }
    }   // deadband

    public void stopElevator() {
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setPower(0);
    }

    public void runElevatorToMaxHeight(){
        liftMotor.setTargetPosition(1500);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void runElevatorToMediumJunction(){
        liftMotor.setTargetPosition(1000);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void runElevatorToLowJunction(){
        liftMotor.setTargetPosition(500);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void runElevatorToHome() {
        liftMotor.setTargetPosition(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void printEelvatorTelemetry(){
        telemetry.addData("Elevator encoder", liftMotor.getCurrentPosition());
    }

    // TODO: (Long term) Write method to freeze elevator in place (reading encoder and making sure it doesn't slip)

}