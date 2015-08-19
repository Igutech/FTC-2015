package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Kevin on 8/15/2015.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotServoDriver extends OpMode {

    Servo releaseServo;

    double releaseServoPos;

    public RobotServoDriver() {
        releaseServo = hardwareMap.servo.get("servo_1");
    }
    @Override
    public void init() {   }

    @Override
    public void loop() {   }

    public void setReleaseServoPos(double inputReleaseServoPos) {
        releaseServoPos = inputReleaseServoPos;
    }

    public double getRelaseServoPos() {
        return releaseServoPos;
    }

    public void sendReleaseServoPos() {
        releaseServo.setPosition(releaseServoPos);
    }

}
