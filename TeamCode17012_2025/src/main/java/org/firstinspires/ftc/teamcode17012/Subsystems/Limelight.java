package org.firstinspires.ftc.teamcode17012.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

public class Limelight {
    private Limelight3A limelight;

    public Limelight(HardwareMap hardwareMap, Telemetry telemetry) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);

        limelight.start();
    }

    public double[] getBotPose(){
        double[] pose = new double[2];

        LLResult result = limelight.getLatestResult();

        if(result != null){
            if(result.isValid()){
                Pose3D botpose = result.getBotpose();
                pose[0] = result.getTx();
                pose[1] = result.getTy();
            }
        }

        return pose;
    }
}
