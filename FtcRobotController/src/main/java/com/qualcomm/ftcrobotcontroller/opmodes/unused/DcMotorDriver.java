package com.qualcomm.ftcrobotcontroller.opmodes.unused;

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

    public static final double circumference = 1;

    private void setup(HardwareMap hardwareMap, boolean useEncoders) {
        leftMotor1 = hardwareMap.dcMotor.get("left1");
        leftMotor2 = hardwareMap.dcMotor.get("left2");
        rightMotor1 = hardwareMap.dcMotor.get("right1");
        rightMotor2 = hardwareMap.dcMotor.get("right2");

        if (!useEncoders){
            leftMotor1.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            leftMotor2.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            rightMotor1.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            rightMotor2.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        } else {
            leftMotor1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            leftMotor2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            rightMotor1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            rightMotor2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        }

        leftMotorController = hardwareMap.dcMotorController.get("leftMotorController");
        rightMotorController = hardwareMap.dcMotorController.get("rightMotorController");

        rightEncoderValue = 0;
        leftEncoderValue = 0;
    }

    public DcMotorDriver(HardwareMap hardwareMap, boolean useEncoders) {
        setup(hardwareMap,useEncoders);
    }
    public DcMotorDriver(HardwareMap hardwareMap) {
        setup(hardwareMap, true);
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
    public void driveDistance(double centimeters, double speed) {
        resetEncoder("right");
        resetEncoder("left");
        double requiredrotations = centimeters/circumference;
        boolean requiredhit = false;
        while (requiredhit == false) {
            driveForward(speed);
            if ((getEncoderPosition("right")+getEncoderPosition("left"))/2 > requiredrotations) {
                requiredhit = true;
            }
        }
        stopDriving();
        resetEncoder("right");
        resetEncoder("left");
    }

    public double getEncoderPosition(String motor) {
        long newtime = System.nanoTime();
        if (motor == "right") {
            if (newtime>rightLastTime+200000000) {
                rightEncoderValue += getRawEncoderPosition("right");
                rightLastTime = newtime;
            }
            return rightEncoderValue;
        }
        if (motor == "left") {
            if (newtime>leftLastTime+200000000) {
                leftEncoderValue += getRawEncoderPosition("left");
                leftLastTime = newtime;
            }
            return leftEncoderValue;
        }
        return 0;
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
