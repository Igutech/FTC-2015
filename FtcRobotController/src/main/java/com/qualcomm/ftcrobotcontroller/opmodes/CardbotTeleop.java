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
    CardbotDriver driver;

    @Override
    public void init() {
        driver = new CardbotDriver(hardwareMap);
    }

    @Override
    public void loop() {
        driver.leftdrive(gamepad1.left_stick_y);
        driver.rightdrive(gamepad1.right_stick_y);
    }

}
