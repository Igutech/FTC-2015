package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Kevin on 9/12/2015.
 */

import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CardbotSensors {

    CompassSensor compass;
    double compassval;

    public CardbotSensors(HardwareMap hardwareMap) {
        compass = hardwareMap.compassSensor.get("compass");
    }

    public double getCompass() {
        compassval = compass.getDirection();
        return compassval;
    }
}
