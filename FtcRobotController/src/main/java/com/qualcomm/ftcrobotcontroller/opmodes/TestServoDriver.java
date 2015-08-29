package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Kevin on 8/29/2015.
 */
public class TestServoDriver extends OpMode {
    /*
    A test servo driver for use on our test platform
     */

    Servo servo;
    double servoPos;

    public TestServoDriver() {
        servo = hardwareMap.servo.get(RobotConstants.servo);
    }
    public void setServoPos(double targetServoPos) {
        servoPos = targetServoPos;
    }
    public double getServoPos() {
        return servoPos;
    }
    public void sendToServo() {
        servo.setPosition(servoPos);
    }
    public double scale(double joysticinput) {
        return joysticinput/2+.5;
    }

    @Override
    public void init() {  }

    @Override
    public void loop() {  }

}
