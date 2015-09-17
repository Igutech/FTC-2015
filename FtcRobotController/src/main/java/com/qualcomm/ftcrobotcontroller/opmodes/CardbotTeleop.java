package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Tilman on Igutech 02 on 9/9/2015.
 */
//This is a comment, created to explain the fact that it is a comment. Don't question it.
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

        driver = new CardbotDriver(hardwareMap); //create the driver object
        sensors = new CardbotSensors(hardwareMap); //create the sensor object
        pidcontroller = new CardbotPID(); //create the PID object
        pidcontroller.setP(.01); //define PID values
        pidcontroller.setI(0); //define PID values
        pidcontroller.setD(0); //define PID values
    }

    @Override
    public void loop() {
        JoyThr = -gamepad1.left_stick_y;   //get throttle value
        JoyYaw = -gamepad1.right_stick_x;  //get yaw value

        sp=+JoyYaw; //set the setpoint for PID controller

        if (gamepad1.right_bumper) { //PID assisted driving

            double pidval = pidcontroller.doCalc(180, sensors.getCompass()); //do the PID calculation

            leftPow = JoyThr;   //setup the variables for adjustment
            rightPow = JoyThr;  //setup the variables for adjustment

            leftPow += pidval; //adjust the variables according to PID output
            rightPow += -pidval; //adjust the variables according to PID output

            if(leftPow > 1){leftPow = 1;}                       //Removes any excess speed because motors cannot do more than 1
            if(leftPow < -1){leftPow = -1;}                     //Removes any excess speed because motors cannot do more than 1
            if(rightPow > 1){rightPow = 1;}                       //Removes any excess speed because motors cannot do more than 1
            if(rightPow < -1){rightPow = -1;}                     //Removes any excess speed because motors cannot do more than 1

        } else { //normal non-assisted driving
            leftPow = JoyThr;   //setup the variables for yaw adjustment
            rightPow = JoyThr;  //setup the variables for yaw adjustment

            leftPow += JoyYaw;    //adjust the variables due to yaw output
            rightPow+= -JoyYaw;   //adjust the variables due to yaw output

            if(leftPow > 1){leftPow = 1;}                       //Removes any excess speed because motors cannot do more than 1
            if(leftPow < -1){leftPow = -1;}                     //Removes any excess speed because motors cannot do more than 1
            if(rightPow > 1){rightPow = 1;}                       //Removes any excess speed because motors cannot do more than 1
            if(rightPow < -1){rightPow = -1;}                     //Removes any excess speed because motors cannot do more than 1

        }

        driver.leftDrive(-leftPow);    //send the data to the motors
        driver.rightDrive(rightPow);  //send the data to the motors

        telemetry.addData("Compass Reading: ", sensors.getCompass());  //simple telemetry debug

        try {
            Thread.sleep(10); //let the robot react before running another PID loop
        } catch (Exception e) {  }
    }
}
