package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Igutech-02 on 10/28/2015.
 */
public class DcMotorDriver {

    DcMotor leftMotor1;
    DcMotor leftMotor2;

    DcMotor rightMotor1;
    DcMotor rightMotor2;

    public DcMotorDriver(HardwareMap hardwareMap) {
        leftMotor1 = hardwareMap.dcMotor.get("left1");
        leftMotor2 = hardwareMap.dcMotor.get("left2");
        rightMotor1 = hardwareMap.dcMotor.get("right1");
        rightMotor2 = hardwareMap.dcMotor.get("right2");
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

}
