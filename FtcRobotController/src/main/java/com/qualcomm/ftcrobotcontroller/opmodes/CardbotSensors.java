package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Kevin on 9/12/2015.
 */

import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CardbotSensors {

    CompassSensor compass;
    double compassval;

    public CardbotSensors(HardwareMap hardwareMap) { //constructor...
        compass = hardwareMap.compassSensor.get("compass"); //define the compass to it's address
    }

    public double getCompass() {
        compassval = compass.getDirection();  //get the compass data
        return compassval;  //send it to whatever called this method
    }
}
