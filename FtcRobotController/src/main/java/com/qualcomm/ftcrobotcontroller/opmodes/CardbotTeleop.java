package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * Created by Tilman on Igutech 02 on 9/9/2015.
 */

public class CardbotTeleop extends OpMode {

    double JoyThr;
    double JoyYaw;

    double leftPow;
    double rightPow;

    CardbotDriver driver;

    @Override
    public void init() {
        driver = new CardbotDriver(hardwareMap);
    }

    @Override
    public void loop() {
        JoyThr = gamepad1.left_stick_y;
        JoyYaw = gamepad1.right_stick_x;

        leftPow = JoyThr;
        rightPow = JoyThr;

        leftPow+=JoyYaw;
        rightPow-=JoyYaw;

        driver.leftDrive(leftPow);
        driver.rightDrive(rightPow);
    }
}
