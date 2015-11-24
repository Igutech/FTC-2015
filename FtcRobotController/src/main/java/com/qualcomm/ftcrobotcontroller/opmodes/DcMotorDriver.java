package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Igutech-02 on 10/28/2015.
 */
public class DcMotorDriver {

    DcMotor leftMotor1;
    DcMotor leftMotor2;

    DcMotor rightMotor1;
    DcMotor rightMotor2;

    DcMotorController leftMotorController;
    DcMotorController rightMotorController;

    double rightEncoderValue;
    double leftEncoderValue;

    long rightLastTime = 0;
    long leftLastTime = 0;

    public DcMotorDriver(HardwareMap hardwareMap) {
        leftMotor1 = hardwareMap.dcMotor.get("left1");
        leftMotor2 = hardwareMap.dcMotor.get("left2");
        rightMotor1 = hardwareMap.dcMotor.get("right1");
        rightMotor2 = hardwareMap.dcMotor.get("right2");
        leftMotorController = hardwareMap.dcMotorController.get("leftMotorController");
        rightMotorController = hardwareMap.dcMotorController.get("rightMotorController");

        rightEncoderValue = 0;
        leftEncoderValue = 0;
    }
    public void driveForward(double power) {
        leftMotor1.setPower(power);
        leftMotor2.setPower(-power);
        rightMotor1.setPower(-power);
        rightMotor2.setPower(power);
    }
    public void stopDriving() {
        leftMotor1.setPower(0);
        leftMotor2.setPower(0);
        rightMotor1.setPower(0);
        rightMotor2.setPower(0);
    }
    public void driveRightTrain(double power) {
        rightMotor1.setPower(-power);
        rightMotor2.setPower(power);
    }
    public void driveLeftTrain(double power) {
        leftMotor1.setPower(power);
        leftMotor2.setPower(-power);
    }
    public void driveDistance(double centimeters) {

    }

    public void getEncoderPosition(String motor) {
        java.util.Date time = new java.util.Date();
        if (motor == "right") {
            long newtime = time.getTime();
            if (newtime>rightLastTime+200) {
                rightEncoderValue = getRawEncoderPosition("right");
                rightLastTime = time.getTime();
            }
        }
        if (motor == "left") {
            long newtime = time.getTime();
            if (newtime>leftLastTime+200) {
                rightEncoderValue = getRawEncoderPosition("left");
                leftLastTime = time.getTime();
            }
        }
    }

    public void resetEncoder(String motor) {
        if (motor == "right") {
            rightEncoderValue = 0;
        } else if (motor == "left") {
            leftEncoderValue = 0;
        }
    }

    public double getRawEncoderPosition(String motor) {
        double value = 0;
        if (motor == "right") {
            rightMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
            while (rightMotorController.getMotorControllerDeviceMode() != DcMotorController.DeviceMode.READ_ONLY) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {   }
            }
            value = rightMotorController.getMotorCurrentPosition(1);
            rightMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
            while (rightMotorController.getMotorControllerDeviceMode() != DcMotorController.DeviceMode.WRITE_ONLY) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {   }
            }
        } else if (motor == "left") {
            leftMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
            while (leftMotorController.getMotorControllerDeviceMode() != DcMotorController.DeviceMode.READ_ONLY) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {   }
            }
            value = leftMotorController.getMotorCurrentPosition(1);
            leftMotorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
            while (leftMotorController.getMotorControllerDeviceMode() != DcMotorController.DeviceMode.WRITE_ONLY) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {   }
            }
        }
        return value;
    }

}
