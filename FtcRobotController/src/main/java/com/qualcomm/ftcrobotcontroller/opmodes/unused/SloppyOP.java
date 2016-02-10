package com.qualcomm.ftcrobotcontroller.opmodes.unused;

//------------------------------------------------------------------------------
//
// PushBotManual
//

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Extends the PushBotTelemetry and PushBotHardware classes to provide a basic
 * manual operational mode for the Push Bot.
 *
 * @author SSI Robotics
 * @version 2015-08-01-06-01
 */
public class SloppyOP extends OpMode
{

    /*
    A test DCMotor Driver for use with our Test Platform!!!
     */

    public DcMotor motor;
    public double motorSpeed = 0;

    public SloppyOP(String address) { //When the driver is first created...
        motor = hardwareMap.dcMotor.get(address); //Link the motor with its proper 'address'
    }

    public void setMotorSpeed(double targetMotorSpeed) { //when we call motorDriver.setMotorSpeed...
        motorSpeed = targetMotorSpeed; //set the motor's speed in a variable.
        // if the motor is set to reverse...
    }

    public double getMotorSpeed () { //when we request the motor speed from the driver,
        return motorSpeed; //simply pass the motor speed directly to it.
    }

    // Java complains if we don't override init() and loop() methods even though we don't use them...

    @Override
    public void init() {  } //include it anyway

    @Override
    public void loop() {  } //include it anyway

//    DcMotor m1;
//    DcMotorController mc1;
//    DcMotor m2;
//    DcMotor m3;
//    DcMotorController mc2;
//    DcMotor m4;
//
//
//    @Override public void init ()
//    {
//        m1 = hardwareMap.dcMotor.get("m1");
//        mc1 = hardwareMap.dcMotorController.get("mc1");
//        m1 = hardwareMap.dcMotor.get("m2");
//
//        m1 = hardwareMap.dcMotor.get("m3");
//
//        m1 = hardwareMap.dcMotor.get("m4");
//        mc2 = hardwareMap.dcMotorController.get("mc2");
//        //m1.setDirection(DcMotor.Direction.REVERSE);
//
//    }
//    @Override public void loop ()
//    {
//        m1.setPower(gamepad1.left_stick_y);
//        m2.setPower(gamepad1.left_stick_y);
//        m3.setPower(gamepad1.right_stick_y);
//        m4.setPower(gamepad1.right_stick_y);
//
//        try {
//            Thread.sleep(10);
//        }catch (Exception e){
//
//        }
//    }
}
