package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Kevin on 8/16/2015.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TimerThread extends Thread {
    @Override
    public void run() {
        try {
            HardwareMap hwmap = new HardwareMap(); //generate a hardware map
            OpModeManager manager = new OpModeManager(hwmap); //create an opmode manager using the hardware map
            System.out.println("Timer thread is running"); // a line that helps debugging...
            Thread.sleep(120000); //waits 2 mins (Java will run IguTeleOp until this finishes and IguTeleOp completes a full loop cycle.
            //manager.pauseOpMode("IguTeleOp"); //stops teleop mode 'IguTeleOp'

        } catch (InterruptedException e) { e.printStackTrace(); } //java complains if we don't have this line.
    }
}
