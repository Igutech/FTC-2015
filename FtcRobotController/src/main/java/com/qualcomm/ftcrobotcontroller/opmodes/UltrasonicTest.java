package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by Kevin on 11/4/2015.
 */
public class UltrasonicTest extends OpMode {

    UltrasonicSensor ultrasonic;
    UltrasonicSensor ultrasonic2;

    @Override
    public void init() {
        SensorDriver driver = new SensorDriver(hardwareMap);
        telemetry.addData("state", "START");
        for (int i = 0; i<1000; i++) {
            double throwaway = driver.getUltrasonic();
        }
        telemetry.addData("state", "STOP");
    }

    @Override
    public void loop() {

    }
}
