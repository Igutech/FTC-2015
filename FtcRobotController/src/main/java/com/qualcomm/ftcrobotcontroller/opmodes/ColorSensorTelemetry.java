package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Logan on 10/31/2015.
 */
public class ColorSensorTelemetry extends OpMode {
    ColorSensor colorsensor;
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        colorsensor = hardwareMap.colorSensor.get("color");
        telemetry.addData("Red", colorsensor.red());
        telemetry.addData("Green", colorsensor.green());
        telemetry.addData("Blue", colorsensor.blue());
    }
}
