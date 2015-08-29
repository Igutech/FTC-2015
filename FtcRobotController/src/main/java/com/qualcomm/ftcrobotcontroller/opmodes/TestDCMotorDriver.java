package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Kevin on 8/29/2015.
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class TestDCMotorDriver extends OpMode{

    /*
    A test DCMotor Driver for use with our Test Platform!!!
     */

    public DcMotor motor;
    public double motorSpeed = 0;

    public TestDCMotorDriver(String address) { //When the driver is first created...
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

}
