package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Igutech-02 on 9/9/2015.
 */
public class CardbotDriver extends CardbotTeleop {
    CardbotConfig config;
    DcMotor left1 = config.getLeft1();
    DcMotor left2 = config.getLeft2();

    public CardbotDriver(CardbotConfig d)
    {
        config = d;
    }

    public CardbotDriver() {

    }


    public void setLeftPower(float x)
    {
        left1.setPower(x);
        left2.setPower(x);
    }

    public void stopLeftDrive()
    {
        left1.setPower(0);
        left2.setPower(0);
    }

}
