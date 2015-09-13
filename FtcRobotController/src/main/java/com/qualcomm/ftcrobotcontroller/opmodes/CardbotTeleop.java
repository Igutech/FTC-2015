package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * Created by Tilman on Igutech 02 on 9/9/2015.
 */

public class CardbotTeleop extends OpMode {

    double JoyThr;
    double JoyYaw;

    double leftPow;
    double rightPow;

    double sp = 0;

    CardbotDriver driver;
    CardbotSensors sensors;
    CardbotPID pidcontroller;

    @Override
    public void init() {
        driver = new CardbotDriver(hardwareMap);
        sensors = new CardbotSensors(hardwareMap);
        pidcontroller.setP(1);
        pidcontroller.setI(0);
        pidcontroller.setD(0);
        //sensors.calibrateCompass();
    }

    @Override
    public void loop() {
        JoyThr = gamepad1.left_stick_y;
        JoyYaw = gamepad1.right_stick_x;

        if (gamepad1.right_bumper) { //PID assisted driving
            sp=+JoyYaw;

            double pidval = pidcontroller.doCalc(sp, sensors.getCompass());

            leftPow = JoyThr;
            rightPow = JoyThr;

            leftPow +=pidval;
            rightPow-=pidval;

            if(leftPow > 1){leftPow = 1;}                       //Removes any excess speed because motors cannot do more than 1
            if(leftPow < -1){leftPow = -1;}                     //Removes any excess speed because motors cannot do more than 1
            if(rightPow > 1){rightPow = 1;}                       //Removes any excess speed because motors cannot do more than 1
            if(rightPow < -1){rightPow = -1;}                     //Removes any excess speed because motors cannot do more than 1

        } else { //normal non-assisted driving
            leftPow = JoyThr;
            rightPow = JoyThr;

            leftPow+=JoyYaw;
            rightPow-=JoyYaw;

            if(leftPow > 1){leftPow = 1;}                       //Removes any excess speed because motors cannot do more than 1
            if(leftPow < -1){leftPow = -1;}                     //Removes any excess speed because motors cannot do more than 1
            if(rightPow > 1){rightPow = 1;}                       //Removes any excess speed because motors cannot do more than 1
            if(rightPow < -1){rightPow = -1;}                     //Removes any excess speed because motors cannot do more than 1

            driver.leftDrive(leftPow);
            driver.rightDrive(rightPow);
        }

        telemetry.addData("Compass Reading: ", sensors.getCompass());
    }
}
