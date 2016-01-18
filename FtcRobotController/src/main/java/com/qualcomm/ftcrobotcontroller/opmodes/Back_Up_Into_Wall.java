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

        Right = hardwareMap.dcMotor.get("left2");
        Left = hardwareMap.dcMotor.get("right2");
        Right.setDirection(DcMotor.Direction.REVERSE);
        UltraL = hardwareMap.ultrasonicSensor.get("UltraL");
        UltraR = hardwareMap.ultrasonicSensor.get("UltraR");
        while (true) {
            UL = UltraL.getUltrasonicLevel();
            Thread.sleep(100);
            UR = UltraR.getUltrasonicLevel();
            Thread.sleep(100);

            waitForStart();

            telemetry.addData("Left Ultrasonic Sensor:", UL);
            telemetry.addData("Right Ultrasonic Sensor:", UR);

            if (FirstTry == false) {
                if (UL == 0 && UR == 0) {
                    Left.setPower(0);
                    Right.setPower(0);
                } else if (UL == 0 || UL == 255) {
                    UL = PreviousUL;
                } else if (UR == 0 || UR == 255) {
                    UR = PreviousUR;
                }
                if (UL > UR) {
                    Left.setPower(-.4);
                    Right.setPower(0);
                } else if (UR > UL) {
                    Left.setPower(0);
                    Right.setPower(-.4);
                } else if (UL == UR) {
                    Left.setPower(-.4);
                    Right.setPower(-.4);
                }
            }
            FirstTry = false;
            PreviousUL = UL;
            PreviousUR = UR;
        }
    }
}