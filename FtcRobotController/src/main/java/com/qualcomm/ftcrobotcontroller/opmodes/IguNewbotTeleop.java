package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by shiva on 10/17/2015.
 */
public class IguNewbotTeleop extends OpMode {

    DcMotor leftMotor1;
    DcMotor leftMotor2;
    DcMotor rightMotor1;
    DcMotor rightMotor2;


    double JoyThr;
    double JoyYaw;

    double rightPow;
    double leftPow;

    @Override
    public void init() {
        leftMotor1 = hardwareMap.dcMotor.get("left1");
        leftMotor2 = hardwareMap.dcMotor.get("left2");

        rightMotor1 = hardwareMap.dcMotor.get("right1");
        rightMotor2 = hardwareMap.dcMotor.get("right2");
    }

    @Override
    public void loop() {

        JoyThr = -gamepad1.left_stick_y;
        JoyYaw = gamepad1.right_stick_x;

        if (JoyThr == 0) {
            rightPow = -JoyYaw;
            leftPow = JoyYaw;
        } else if (JoyYaw == 0) {
            rightPow = JoyThr;
            leftPow = JoyThr;
        } else if (JoyYaw < 0 && JoyYaw >= -1) {
            leftPow = ((2 * JoyYaw) + 1) * JoyThr;
            rightPow = JoyThr;
            //Joyyaw will be -1 through 0
            //if the joyyaw = -0.5,  0
            //if the joyyaw = -0.75, -.5
            //if the joyyaw = -1.0,  -1
        } else if (JoyYaw > 0 && JoyYaw <= 1) {
            rightPow = ((-2 * JoyYaw) + 1) * JoyThr;
            leftPow = JoyThr;
        }

        leftMotor1.setPower(leftPow);
        leftMotor2.setPower(-leftPow);

        rightMotor1.setPower(-rightPow);
        rightMotor2.setPower(rightPow);

    }

}
