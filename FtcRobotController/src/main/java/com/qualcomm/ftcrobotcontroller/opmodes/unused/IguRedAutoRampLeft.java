package com.qualcomm.ftcrobotcontroller.opmodes.unused;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Igutech-02 on 11/14/2015.
 */



public class IguRedAutoRampLeft extends LinearOpMode {

    DcMotor leftMotor2, rightMotor2;
    DcMotorController leftMotorController, rightMotorController;
    int counter = 1;
    Servo climberservo;

    @Override
    public void runOpMode() throws InterruptedException
    {
        leftMotorController = hardwareMap.dcMotorController.get("leftMotorController");
        rightMotorController = hardwareMap.dcMotorController.get("rightMotorController");
        leftMotor2 = hardwareMap.dcMotor.get("left2");
        rightMotor2 = hardwareMap.dcMotor.get("right2");

        climberservo = hardwareMap.servo.get("climber");
        leftMotor2.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        //driving goes here

        climberservo.setPosition(.1);

        driveDistance(-850, -850, -1);

        /*
        driveDistance(198, 198, 0.35);
        driveDistance(0, 29, 0.35);
        driveDistance(82, 82, 0.35);
        driveDistance(0,0,0);
        */
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
        waitOneFullHardwareCycle();
        leftMotor2.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightMotor2.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
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
            if(Math.abs(leftMotor2.getCurrentPosition()) > Math.abs(enc1)-30 && Math.abs(rightMotor2.getCurrentPosition()) > Math.abs(enc2)-30)
            {
                counter = 2;
            }
            waitOneFullHardwareCycle();
        }
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        leftMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY); //Change to read
        rightMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        waitOneFullHardwareCycle();
        leftMotor2.setPower(0);
        rightMotor2.setPower(0);
        leftMotor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        leftMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY); //Change to read
        rightMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        telemetry.addData("Currentvalue", leftMotor2.getCurrentPosition());
        waitOneFullHardwareCycle();
        leftMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY); //Change to read
        rightMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        leftMotor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        counter = 1;
    }
    public Boolean isCloseto(int number1, int number2)
    {
        if(number1 - number2 < 15 && number1 - number2 > -15)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
