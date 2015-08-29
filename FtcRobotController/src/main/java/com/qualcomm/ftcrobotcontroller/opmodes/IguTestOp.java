package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Kevin on 8/28/2015.
 */
public class IguTestOp extends OpMode {
    DcMotor dcmotor;
    Servo servo;

    @Override
    public void init() {
        dcmotor = hardwareMap.dcMotor.get("motor");
        servo = hardwareMap.servo.get("servo");

        dcmotor.setPower(0);
        servo.setPosition(0);
    }

    @Override
    public void loop() {
        dcmotor.setPower(gamepad1.left_stick_y);
        double servoPos = gamepad1.right_stick_x/2;
        servoPos+=.5;
        servo.setPosition(servoPos);

        try {
            Thread.sleep(10);
        } catch (Exception e) {  }
    }
}
