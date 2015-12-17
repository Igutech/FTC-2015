package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by Logan on 12/16/2015.
 */
public class Back_Up_Into_Wall extends LinearOpMode {
    DcMotor Left, Right;
    UltrasonicSensor Ultra1, Ultra2;
    double U1, U2;

    @Override
    public void runOpMode() throws InterruptedException {

        Left = hardwareMap.dcMotor.get("left2");
        Right = hardwareMap.dcMotor.get("right2");
        Ultra1 = hardwareMap.ultrasonicSensor.get("Ultra1");
        Ultra2 = hardwareMap.ultrasonicSensor.get("Ultra2");
        while (true) {
            U1 = Ultra1.getUltrasonicLevel();
            U2 = Ultra2.getUltrasonicLevel();

            telemetry.addData("Ultra1", U1);
            telemetry.addData("Ultra2", U2);
        }
    }

}
