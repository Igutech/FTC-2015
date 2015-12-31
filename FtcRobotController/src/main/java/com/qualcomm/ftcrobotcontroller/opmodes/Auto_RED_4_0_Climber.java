package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Igutech-02 on 11/14/2015.
 */
public class Auto_RED_4_0_Climber extends LinearOpMode {
    DcMotor leftMotor2, rightMotor2;
    DcMotor armMotor1;
    DcMotor armMotor2;
    Servo servo, deliveryServo;
    DcMotorController leftMotorController, rightMotorController;
    int counter = 1;
    @Override
    public void runOpMode() throws InterruptedException
    {
        leftMotorController = hardwareMap.dcMotorController.get("leftMotorController");
        rightMotorController = hardwareMap.dcMotorController.get("rightMotorController");
        leftMotor2 = hardwareMap.dcMotor.get("left2");
        rightMotor2 = hardwareMap.dcMotor.get("right2");
        armMotor1 = hardwareMap.dcMotor.get("arm1");
        armMotor2 = hardwareMap.dcMotor.get("arm2");
        leftMotor2.setDirection(DcMotor.Direction.REVERSE);
        armMotor1.setDirection(DcMotor.Direction.FORWARD);
        armMotor2.setDirection(DcMotor.Direction.REVERSE);
        servo = hardwareMap.servo.get("climber");
        deliveryServo = hardwareMap.servo.get("armservo");
        deliveryServo.setPosition(.5);


        //driving goes here
        driveDistance(-975, -975, -.5); //Drives backwards further than necessary.
        Thread.sleep(300);
        driveDistance(105, 105, .5);
        Thread.sleep(300);
        driveDistance(0, 95, .5);
        Thread.sleep(300);
        //driveDistance(-75, 0, -1);
        armMotor1.setPower(-.25);
        armMotor2.setPower(-.25);
        Thread.sleep(700);
        armMotor1.setPower(0);
        armMotor2.setPower(0);
        Thread.sleep(300);
        driveDistance(-170, -170, -.5);
        Thread.sleep(300);


        for (double i = .20; i < .7; i+=.0025){
            servo.setPosition(i);
            Thread.sleep(5);
        }

        for (double i = .7; i < .9; i+=0.00125) {
            servo.setPosition(i);
            Thread.sleep(5);
        }
        //left reverse, right forward
        driveDistance(100, 100, 1);
        Thread.sleep(1000);
        servo.setPosition(0);
    }
    public void driveDistance(double ldist, double rdist, double power) throws InterruptedException
    {
        int enc1;
        int enc2;

        enc1 = (int)Math.round(ldist*33.65);
        enc2 = (int)Math.round(rdist*33.65);



        leftMotor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        leftMotor2.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightMotor2.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        waitOneFullHardwareCycle();
        leftMotor2.setTargetPosition(enc1);
        rightMotor2.setTargetPosition(enc2);
        leftMotor2.setPower(power);
        rightMotor2.setPower(power);
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        leftMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY); //Change to read
        rightMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
        waitOneFullHardwareCycle();
        while(counter == 1)
        {
            if(isCloseto(leftMotor2.getCurrentPosition(), enc1) && isCloseto(rightMotor2.getCurrentPosition(),enc2))
            {
                counter = 2;
            }
            waitOneFullHardwareCycle();
        }
        waitOneFullHardwareCycle();
        leftMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY); //Change to read
        rightMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        waitOneFullHardwareCycle();
        leftMotor2.setPower(0);
        rightMotor2.setPower(0);
        leftMotor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        waitOneFullHardwareCycle();
        leftMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY); //Change to read
        rightMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
        waitOneFullHardwareCycle();
        telemetry.addData("Currentvalue", leftMotor2.getCurrentPosition());
        waitOneFullHardwareCycle();
        leftMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY); //Change to read
        rightMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        waitOneFullHardwareCycle();
        leftMotor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        counter = 1;
    }
    public Boolean isCloseto(int number1, int number2)
    {
        if(Math.abs(number1) >= Math.abs(number2))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
