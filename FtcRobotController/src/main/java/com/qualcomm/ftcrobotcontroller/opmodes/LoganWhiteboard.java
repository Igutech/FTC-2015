package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Edited by Logan at some point in time.
 */

public class LoganWhiteboard extends OpMode {
    Servo servo;
    TestDCMotorDriver motorDriverL;
    TestServoDriver servoDriverL;
    @Override

    public void init() {
        //Elite Code
        servoDriverL = new TestServoDriver();
        motorDriverL = new TestDCMotorDriver(TestRobotConstants.motor);
    }

    @Override
    public void loop() {
        double MSpeedL;
        MSpeedL = .8;
        motorDriverL.setMotorSpeed(MSpeedL);
            try {
                Thread.sleep(3000);
            } catch (Exception e) {}
        MSpeedL = 0;
        motorDriverL.setMotorSpeed(MSpeedL);


        try {
            Thread.sleep(5000);
        } catch (Exception e) {}

        try {

            Thread.sleep(10);
        } catch (Exception e) {  }
    }
}
