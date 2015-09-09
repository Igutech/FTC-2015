package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * Created by Tilman on Igutech 02 on 9/9/2015.
 */

public class CardbotTeleop extends OpMode{
    CardbotDriver drivetrain;
    CardbotConfig config;
    @Override
    public void init() {
        config = new CardbotConfig();
        drivetrain = new CardbotDriver(config);


    }

    @Override
    public void loop() {

        drivetrain.setLeftPower(-gamepad1.left_stick_y);
        try {
            Thread.sleep(10); //hyperdrive warp speed preventer tool (also gives the timer some time to check if time is up... This prevents the phone from crashing mid-run.
        } catch (Exception exception) {  } //nothing will happen, but java complains if we don't try to catch it.

    }

    //@Override
  //  public void stop() {

//    }
}
