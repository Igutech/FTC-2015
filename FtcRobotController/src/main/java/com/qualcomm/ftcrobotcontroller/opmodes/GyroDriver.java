package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Sahas on 11/11/2015.
 */

import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class GyroDriver {

    GyroSensor gyro;
    double gyroval;

    public GyroDriver(HardwareMap hardwareMap) { //constructor...
        gyro = hardwareMap.gyroSensor.get("gyro"); //define the gyro to it's address
    }

    public double getGyro() {
        gyroval = gyro.getRotation();  //get the gyro data
        return gyroval;  //send it to whatever called this method
    }
}