package com.qualcomm.ftcrobotcontroller.opmodes.unused;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Igutech-02 on 9/9/2015.
 *
 * if you have more problems feel free to email me at brandon@brandonbarker.me
 */
public class CardbotDriver {

    protected DcMotor m1, m2, m3, m4;

    public CardbotDriver(HardwareMap hardwareMap)
    {
        m1 = hardwareMap.dcMotor.get("m1"); //create the motor objects
        m2 = hardwareMap.dcMotor.get("m2"); //create the motor objects
        m3 = hardwareMap.dcMotor.get("m3"); //create the motor objects
        m4 = hardwareMap.dcMotor.get("m4"); //create the motor objects
    }


    public void leftDrive(double power) {   //set the left drive train's power
        m1.setPower(power);
        m2.setPower(power);
    }
    public void rightDrive(double power) { //set the right drive train's power
        m3.setPower(power);
        m4.setPower(power);
    }


}
