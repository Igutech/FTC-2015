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
    UltrasonicSensor UltraL, UltraR;
    double UL, UR, PreviousUL, PreviousUR;
    boolean FirstTry = true;

    @Override
    public void runOpMode() throws InterruptedException {

        Left = hardwareMap.dcMotor.get("left2");
        Right = hardwareMap.dcMotor.get("right2");
        UltraL = hardwareMap.ultrasonicSensor.get("Ultra1");
        UltraR = hardwareMap.ultrasonicSensor.get("Ultra2");
        while (true) {
            UL = UltraL.getUltrasonicLevel();
            UR = UltraR.getUltrasonicLevel();

            telemetry.addData("Left Ultrasonic Sensor:", UL);
            telemetry.addData("Right Ultrasonic Sensor:", UR);

            if (FirstTry == false) {
                if (UL == 0 && UR == 0) {
                    Left.setPower(0);
                    Right.setPower(0);
                }
                else if (UL == 0 || UL == 255) {
                    UL = PreviousUL;
                }
                else if (UR == 0 || UR == 255) {
                    UR = PreviousUR;
                }
                if (UL > UR) {
                    Left.setPower(-.4);
                    Right.setPower(0);
                }
                if (UR > UL) {
                    Left.setPower(0);
                    Right.setPower(-.4);
                }
                if (UL == UR) {
                    Left.setPower(-.4);
                    Right.setPower(-.4);
                }
            }
            FirstTry = false;
        }
    }
}