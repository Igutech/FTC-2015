package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Kevin on 8/15/2015.
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class RobotDCMotorDriver extends OpMode {

    /*

    DC MOTOR DRIVER
    This is a dc motor driver designed to drive the motors in non-autonomous phases.

    TO USE:

    first define an object:
    RobotDCMotorDriver MotorDriver;

    then, create it's reference:
    MotorDriver = new RobotDCMotorDriver();

    This runs the code that sets all the motors up to work with the I2C busses.

    After you create the driver's reference, write data to a motor:

    MotorDriver.setBrushesMotorPower(1);

    Finally, push the data to the motor:

    MotorDriver.sendToBrushes();

    To get the motor power of a motor at any given time:

    double brushesPower = MotorDriver.getBrushesMotorPower();

     */

    DcMotor leftDrive1;
    DcMotor leftDrive2;
    DcMotor rightDrive1;
    DcMotor rightDrive2;
    DcMotor armMotor1;
    DcMotor armMotor2;
    DcMotor extenderSpool;
    DcMotor Brushes;

    double leftMotorValue1 = 0;  //needs to be reversed
    double leftMotorValue2 = 0;
    double rightMotorValue1 = 0; //needs to be reversed
    double rightMotorValue2 = 0;
    double brushesMotorPower;
    double armMotorValue1;
    double armMotorValue2;
    double extenderSpoolValue;

    public RobotDCMotorDriver() {
        leftDrive1 = hardwareMap.dcMotor.get("motor_1");
        leftDrive2 = hardwareMap.dcMotor.get("motor_2");
        rightDrive1 = hardwareMap.dcMotor.get("motor_3");
        rightDrive2 = hardwareMap.dcMotor.get("motor_4");
        armMotor1 = hardwareMap.dcMotor.get("motor_5");
        armMotor2 = hardwareMap.dcMotor.get("motor_6");
        extenderSpool = hardwareMap.dcMotor.get("motor_7");
        Brushes = hardwareMap.dcMotor.get("motor_8");
    }

    @Override
    public void init() {  }

    @Override
    public void loop() {  }

    public void setLeftMotorValue(double inputLeftValue) {
        leftMotorValue1 = -inputLeftValue;      //set the motor values for the left motor
        leftMotorValue2 = inputLeftValue;       //set the motor values for the left motor
    }

    public void setRightMotorValue(double inputRightValue) {
        rightMotorValue1 = -inputRightValue;   //set the motor values for the right motor
        rightMotorValue2 = inputRightValue;   //set the motor values for the right motor
    }

    public void setBrushesMotorValue(double inputBrushesValue) {
        brushesMotorPower = inputBrushesValue;
    }
    public void setArmMotorValue(double inputArmValue) {
        armMotorValue1 = inputArmValue;
        armMotorValue2 = -inputArmValue;
    }

    public void setExtenderSpoolValue(double inputExtenderSpool) {
        extenderSpoolValue = inputExtenderSpool;
    }

    public double getLeftMotorValue1() {
        return leftMotorValue1;                 //return the motor values
    }

    public double getRightMotorValue1() {
        return rightMotorValue1;                //return the motor values
    }

    public double getLeftMotorValue2() {
        return leftMotorValue2;
    }

    public double getRightMotorValue2() {
        return rightMotorValue2;
    }
    public double getBrushesMotorValue() {
        return brushesMotorPower;
    }
    public double getArmValue() {
        return armMotorValue1;
    }
    public double getExtenderSpoolValue() {
        return extenderSpoolValue;
    }

    public void sendToMotors() {
        leftDrive1.setPower(leftMotorValue1);
        leftDrive2.setPower(leftMotorValue2);
        rightDrive1.setPower(rightMotorValue1);
        rightDrive2.setPower(rightMotorValue2);
    }
    public void sendToBrushes() {
        Brushes.setPower(brushesMotorPower);
    }
    public void sendToArm() {
        armMotor1.setPower(armMotorValue1);
        armMotor2.setPower(armMotorValue2);
    }
    public void sendToextenderSpool() {
        extenderSpool.setPower(extenderSpoolValue);
    }
}