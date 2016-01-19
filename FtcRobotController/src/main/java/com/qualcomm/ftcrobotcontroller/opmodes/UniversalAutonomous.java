package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

 /**
 * Created by Kevin on 1/15/2016.
 */
public class UniversalAutonomous extends LinearOpMode {

    DcMotor leftMotor2, rightMotor2;
    DcMotor armMotor1;
    DcMotor armMotor2;
    Servo servo, deliveryServo;
    DcMotorController leftMotorController, rightMotorController;
    int counter = 1;

    String team = "";
    int pos = 0;

    public void runOpMode() throws InterruptedException {
        initialize();

        while (!gamepad1.start) { //wait for the start button to be pressed on the controller
            telemetry.addData("Alliance", team);
            telemetry.addData("Start Position", pos);
            if (gamepad1.b) {
                team="red";
            } else if (gamepad1.x) {
                team="blue";
            }
            if (gamepad1.y) {
                pos=2;
            } else if (gamepad1.a) {
                pos=4;
            }
        }

        telemetry.addData("READY FOR START!", "");
        telemetry.addData("PUSH START BUTTON ON PHONE TO BEGIN", "");

        waitForStart();

        if (team.equals("red")) {
            if (pos==4) {
                RED_40();
            } else if (pos==2) {
                RED_20();
            }
        } else if (team.equals("blue")) {
            if (pos==4) {
                BLUE_40();
            } else if (pos==2) {
                BLUE_20();
            }
        }

        universalAuto();
    }

    public void RED_40() throws InterruptedException {
        driveDistance(-960, -960, -1); //Drives backwards further than necessary.
        Thread.sleep(100);
        driveDistance(90, 90, 1);
        Thread.sleep(100);
        driveDistance(0, 95, .75);
        Thread.sleep(100);
    }

    public void BLUE_40() throws InterruptedException {
        driveDistance(-960, -960, -1); //Drives backwards further than necessary.
        Thread.sleep(100);
        driveDistance(90, 90, 1);
        Thread.sleep(100);
        driveDistance(95, 0, .75);
        Thread.sleep(100);
    }

    public void RED_20() throws InterruptedException {
        driveDistance(-280, -280, -1); //Drives backwards further than necessary.
        Thread.sleep(100);
        driveDistance(-93, 0, 1);
        Thread.sleep(100);
        driveDistance(-560, -560, -1); //Drives backwards further than necessary.
        Thread.sleep(1000);
        driveDistance(90, 90, 1);
        Thread.sleep(100);
        driveDistance(0, 95, .75);
        Thread.sleep(100);
    }

     public void BLUE_20() throws InterruptedException {
         driveDistance(-280, -280, -1); //Drives backwards further than necessary.
         Thread.sleep(100);
         driveDistance(0, -93, 1);
         Thread.sleep(100);
         driveDistance(-560, -560, -1); //Drives backwards further than necessary.
         Thread.sleep(1000);
         driveDistance(90, 90, 1);
         Thread.sleep(100);
         driveDistance(95, 0, .75);
         Thread.sleep(100);
     }

    public void initialize() {

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
    }

    public void universalAuto() throws InterruptedException {

        armMotor1.setPower(-.25);
        armMotor2.setPower(-.25);
        Thread.sleep(1400);
        armMotor1.setPower(0);
        armMotor2.setPower(0);
        Thread.sleep(300);
        driveDistance(-126, -126, -.5);
        Thread.sleep(100);


        for (double i = .20; i < .7; i+=.0025){
            servo.setPosition(i);
            Thread.sleep(5);
        }

        for (double i = .7; i < 1; i+=0.0025) {
            servo.setPosition(i);
            Thread.sleep(5);
        }
        //left reverse, right forward
        driveDistance(100, 100, 1);
        Thread.sleep(100);
        for (double i = 1; i > .7; i-=0.0035) {
            servo.setPosition(i);
            Thread.sleep(5);
        }
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
        while (counter == 1) {
            if (isCloseto(leftMotor2.getCurrentPosition(), enc1) && isCloseto(rightMotor2.getCurrentPosition(), enc2)) {
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

    public static Boolean isCloseto(int number1, int number2)
    {
        if(Math.abs(number1) >= Math.abs(number2)-10)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
