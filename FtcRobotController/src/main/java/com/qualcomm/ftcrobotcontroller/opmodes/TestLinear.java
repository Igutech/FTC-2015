package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;

/**
 * Created by Kevin on 10/25/2015.
 */
public class TestLinear extends LinearOpMode {

    DcMotorDriver driver;

    DcMotor leftMotor1;
    DcMotor leftMotor2;

    DcMotor rightMotor1;
    DcMotor rightMotor2;

    CompassSensor compass;

    ColorSensor color;
    LightSensor light;

    @Override
    public void runOpMode() throws InterruptedException {
        leftMotor1 = hardwareMap.dcMotor.get("left1");
        leftMotor2 = hardwareMap.dcMotor.get("left2");

        rightMotor1 = hardwareMap.dcMotor.get("right1");
        rightMotor2 = hardwareMap.dcMotor.get("right2");

        compass = hardwareMap.compassSensor.get("compass");
        light = hardwareMap.lightSensor.get("light");

        driver = new DcMotorDriver(hardwareMap);

        waitForStart();

        compass.setMode(CompassSensor.CompassMode.MEASUREMENT_MODE);
        double starting = compass.getDirection();
        double setpoint = starting+45;
        telemetry.addData("CompassStarting", starting);
        telemetry.addData("CompassSetpoint", setpoint);

        //light sensor testing

        /*
        light.enableLed(true);
        while(true){
            telemetry.addData("LightValue", light.getLightDetected());
        }
        */


        driver.driveForward(.5);
        Thread.sleep(1000);
        driver.stopDriving();
        Thread.sleep(200);
        driver.driveRightTrain(.5);
        while(compass.getDirection() < setpoint){
            waitOneFullHardwareCycle();
            telemetry.addData("Compass", compass.getDirection());
        }
        driver.stopDriving();

    }

    //basic functions used for driving for the time being
    public void driveForward(double power) {
        leftMotor1.setPower(power);
        leftMotor2.setPower(-power);

        rightMotor1.setPower(-power);
        rightMotor2.setPower(power);
    }

    public void driveRightTrain(double power) {
        rightMotor1.setPower(-power);
        rightMotor2.setPower(power);
    }

    public void driveLeftTrain(double power) {
        leftMotor1.setPower(power);
        leftMotor2.setPower(-power);
    }

    public void waitForCompass(double sp) throws InterruptedException {
        while(compass.getDirection() > sp) {
            waitOneFullHardwareCycle();
        }
    }

    public void stopDriving() {
        leftMotor1.setPower(0);
        leftMotor2.setPower(0);

        rightMotor1.setPower(0);
        rightMotor2.setPower(0);
    }

}
