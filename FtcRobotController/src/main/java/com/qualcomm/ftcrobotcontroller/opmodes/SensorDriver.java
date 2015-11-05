package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by Kevin on 11/4/2015.
 */
public class SensorDriver {

    UltrasonicSensor ultrasonic;

    public SensorDriver(HardwareMap hardwareMap) {
        ultrasonic = hardwareMap.ultrasonicSensor.get("ultrasonic");
    }
    public double getUltrasonic() {
        return ultrasonic.getUltrasonicLevel();
    }
}
