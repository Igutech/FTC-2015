package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Kevin on 12/16/2015.
 */



public class ServoTestingAutonomas extends LinearOpMode{
    Servo servo;
    Servo armservo;

    @Override
    public void runOpMode() throws InterruptedException {

        servo = hardwareMap.servo.get("climber");
        armservo = hardwareMap.servo.get("armservo");

        armservo.setPosition(.5);

        waitForStart();
        servo.setPosition(.2);
        Thread.sleep(3000);
        servo.setPosition(.9);
        Thread.sleep(3000);
        servo.setPosition(.2);
        Thread.sleep(3000);

        //time to start the testing of speed control.

        for (double i = .20; i < .9; i+=.01){
            servo.setPosition(i);
            Thread.sleep(10);
        }
    }
}
