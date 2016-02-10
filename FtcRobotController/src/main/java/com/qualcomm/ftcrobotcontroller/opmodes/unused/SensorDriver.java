package com.qualcomm.ftcrobotcontroller.opmodes.unused;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by Kevin on 11/4/2015.
 */
public class SensorDriver {

    UltrasonicSensor ultrasonic;
    ColorSensor color1;
    ColorSensor color2;

    public SensorDriver(HardwareMap hardwareMap) {
        ultrasonic = hardwareMap.ultrasonicSensor.get("ultrasonic");
        color1 = hardwareMap.colorSensor.get("color1");
        color1 = hardwareMap.colorSensor.get("color2");
    }
    public double getUltrasonic() {
        return ultrasonic.getUltrasonicLevel();
    }

    public boolean getcolorside(String color) { //returns if the color is on the right side
        boolean toreturn = true;
        if (color=="blue") {
            double a = color1.blue();
            double b = color2.blue();
            if (a > b) { toreturn = true; }
            if (b > a) { toreturn = false;}
        } else if (color=="red") {
            double a = color1.red();
            double b = color2.red();
            if (a > b) { toreturn = true; }
            if (b > a) { toreturn = false;}
        }
        return toreturn;
    }

    public double getcolor(int sensorid, String color) {
        double toreturn = 0;
        if (sensorid == 1) {
            if (color == "red") {
                toreturn = color1.red();
            } else if (color == "green") {
                toreturn = color1.green();
            } else if (color == "blue") {
                toreturn = color1.blue();
            }
        } else if (sensorid == 2) {
            if (color == "red") {
                toreturn = color2.red();
            } else if (color == "green") {
                toreturn = color2.green();
            } else if (color == "blue") {
                toreturn = color2.blue();
            }
        }
        return toreturn;
    }
    public ColorSensor getColorSensor(int id) {
        if (id==1) {
            return color1;
        } else if (id==2) {
            return color2;
        } else { return null; }
    }
}
