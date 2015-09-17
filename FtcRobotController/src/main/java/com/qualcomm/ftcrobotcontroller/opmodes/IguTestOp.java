package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Kevin on 8/28/2015.
 */
//public class IguTestOp extends OpMode {

    /*
    Servo servo;
    TestDCMotorDriver motorDriver;
    TestServoDriver servoDriver;
    @Override
    public void init() {
        servoDriver = new TestServoDriver();
        motorDriver = new TestDCMotorDriver(TestRobotConstants.motor);
    }

    @Override
    public void loop() {
        if (TestRobotConstants.motorReverse) {
            motorDriver.setMotorSpeed(-gamepad1.left_stick_y); //run the motor backwards
        } else { //otherwise (it shouldn't be reversed...)
            motorDriver.setMotorSpeed(gamepad1.left_stick_y); //run the motor normally
        }
        servoDriver.setServoPos(servoDriver.scale(gamepad1.right_stick_x));

        // PROPORTIONAL CONTROL TESTING!!!
        /*
        if (gamepad1.x) {
            telemetry.addData("Speed", 1);
            dcmotor.setPower(1);
        } else if (gamepad1.b) {
            telemetry.addData("Speed", .0001);
            dcmotor.setPower(.0001);
        } else if (gamepad1.y) {
            telemetry.addData("Speed", .001);
            dcmotor.setPower(.001);
        } else if (gamepad1.a) {
            telemetry.addData("Speed", .01);
            dcmotor.setPower(.01);
        } else {
            telemetry.addData("Speed", 0);
            //dcmotor.setPower(0);
        }


        // PROPORTIONAL CONTROL TESTING!!!

        try {
            Thread.sleep(10);
        } catch (Exception e) {  }


    }
    */
//}
