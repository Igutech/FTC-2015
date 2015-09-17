package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.configuration.MotorControllerConfiguration;


/**
 * Created by Igutech-02 on 9/9/2015.
 */
/*
public class CardbotConfig extends CardbotDriver{
    private DcMotorController v_dc_motor_controller_drive;
    private DcMotor v_motor_left_drive;
    final int v_channel_left_drive = 1;
    private DcMotor v_motor_left_drive2;
    final int v_channel_left_drive2 = 2;

    //public CardbotConfig()
    //{
        //v_dc_motor_controller_drive = hardwareMap.dcMotorController.get("mc1");
        //v_motor_left_drive = hardwareMap.dcMotor.get ("m1");
        //v_motor_left_drive2 = hardwareMap.dcMotor.get ("m2");
    //}
    public DcMotor getLeft1()
    {
        return v_motor_left_drive;
    }
    public DcMotor getLeft2()
    {
        return v_motor_left_drive2;
    }
    public void reverseLeft1()
    {
        if(v_motor_left_drive.getDirection() == DcMotor.Direction.REVERSE)
        {
            v_motor_left_drive.setDirection (DcMotor.Direction.FORWARD);
        }
        else
        {
            v_motor_left_drive.setDirection (DcMotor.Direction.REVERSE);
        }

    }
    public void reverseLeft2()
    {
        if(v_motor_left_drive2.getDirection() == DcMotor.Direction.REVERSE)
        {
            v_motor_left_drive2.setDirection (DcMotor.Direction.FORWARD);
        }
        else
        {
            v_motor_left_drive2.setDirection (DcMotor.Direction.REVERSE);
        }

    }
}
*/