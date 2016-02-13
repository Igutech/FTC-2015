package com.qualcomm.ftcrobotcontroller.opmodes.unused;

/**
 * Created by Kevin on 8/8/2015.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

// above imports all code needed to made the below function

public class IguTeleOp extends OpMode {

    double JoyThr;   //initialize all the variables needed
    double JoyYaw;
    double LeftPow;
    double RigtPow;
    double LeftPowV;
    double RigtPowV;
    double armvalue;
    double negAdder;
    double negAdder2;
    String BrushesOn;
    double extenderspool1;
    int loopVar;
    String servoPos;

    // set up servos

    Servo releaseServo;

    //set up dc motors

    DcMotor Brushes;

    RobotDCMotorDriver DcMotorDriver;
    RobotServoDriver ServoDriver;
    public IguTeleOp() {   }//neccessary constructor (nothing in it)}

    @Override //overrides method from OpMode class
    public void init() { //what happens when the TeleOp mode first starts (happens once)

        TimerThread timer = new TimerThread(); //create the timer object
        timer.start(); //start the timer thread counting

        releaseServo = hardwareMap.servo.get("servo_1"); // set up the release servo.


        DcMotorDriver = new RobotDCMotorDriver(); //create the dc motor driver object
        ServoDriver = new RobotServoDriver(); //create the servo driver object

        ServoDriver.setReleaseServoPos(1); //set the release servo position
        ServoDriver.sendReleaseServoPos(); //push the data to the servo

        try {
            Thread.sleep(10); //wait 10 milliseconds for everything to stabilize. Also gives the timer thread some time to catch up
        } catch (Exception exception) {  } //nothing will happen, but java complains if we don't try to catch it.
    }
    @Override //overrides method from OpMode class
    public void loop() { //what happens after init() finishes. Loops over and over until game stop.
        //phase one: Joystic settings gettings
        JoyThr = gamepad1.left_stick_y;
        JoyYaw = gamepad1.right_stick_x;
        //phase two: Conversion to Throttle
        LeftPow = JoyThr;
        RigtPow = JoyThr;
        //phase three: Adding the steering yaw
        LeftPow = LeftPow+JoyYaw;
        RigtPow = RigtPow-JoyYaw;
        //phase four: Removing errors
        if(LeftPow > 1){LeftPow = 1;}                       //Removes any excess speed because motors cannot do more than 1
        if(LeftPow < -1){LeftPow = -1;}                     //Removes any excess speed because motors cannot do more than 1
        if(RigtPow > 1){RigtPow = 1;}                       //Removes any excess speed because motors cannot do more than 1
        if(RigtPow < -1){RigtPow = -1;}                     //Removes any excess speed because motors cannot do more than 1
        if(RigtPow < 0.06 && RigtPow > -0.06){RigtPow = 0;}           //compensates for no stick calibration/human error
        if(LeftPow < 0.06 && LeftPow > -0.06){LeftPow = 0;}           //compensates for no stick calibration/human error

        LeftPowV = LeftPow;
        RigtPowV = RigtPow;


        if(gamepad1.right_bumper) { //If the driver's right trigger is pressed, slow-mo mode!
            LeftPow = LeftPowV * .3;
            RigtPow = RigtPowV * .3;
        }

        DcMotorDriver.setLeftMotorValue(LeftPow); //set the motors to the desired powers
        DcMotorDriver.setRightMotorValue(RigtPow);

        //phase five: Making the motors move!
        DcMotorDriver.sendToMotors(); //push data via I2C to the motor controllers.

        //phase 6: We forgot the arm!
        armvalue = -gamepad2.left_stick_y;
        if(armvalue < 0.00){negAdder = -0.01;}else{negAdder = 0.01;}
        if(armvalue < 0.05 && armvalue > -0.05){armvalue = 0.00;}
        armvalue = (armvalue * armvalue)/(1.27*0.04);
        DcMotorDriver.setArmMotorValue(armvalue*negAdder);
        DcMotorDriver.sendToArm();
        //phase se7en: The extend-o-deliverator
        extenderspool1 = -gamepad2.right_stick_y;
        if(extenderspool1 < 0.00){negAdder2 = -0.01;}else{negAdder2 = 0.01;}
        if(extenderspool1 < 0.05 && extenderspool1 > -0.05){extenderspool1=0.00;}
        extenderspool1 = (extenderspool1 * extenderspool1)/(1.27*0.04);
        DcMotorDriver.setExtenderSpoolValue(extenderspool1*negAdder2);
        DcMotorDriver.sendToextenderSpool();

        //phase 8: the release servo
        if (gamepad2.left_bumper) {
            servoPos = "Open";
            ServoDriver.setReleaseServoPos(0); //ready the servo data for pushing
        } else {
            servoPos = "Closed";
            ServoDriver.setReleaseServoPos(1); //ready the servo data for pushing
            //releaseServo.setPosition(255);
        }
        ServoDriver.sendReleaseServoPos(); //push the previous data to the servo controller

        //phase 9: I forgot to add the brushes motor
        if(gamepad2.x) {
            BrushesOn = "OFF";
            DcMotorDriver.setBrushesMotorValue(0); //set the brushes motor's value to desired position
        } else {
            BrushesOn = "ON";
            DcMotorDriver.setBrushesMotorValue(-1); //set the brushes motor's value to desired position
        }
        DcMotorDriver.sendToBrushes(); //push data via I2C to motor comtroller


        //phase 10: send telemetry
        telemetry.addData("Text", "***ROBOT DATA***");
        telemetry.addData("motorPowor", String.format("%.2f", (DcMotorDriver.getLeftMotorValue2()+DcMotorDriver.getRightMotorValue2())/2));
        telemetry.addData("release", "Release State: " + servoPos);
        telemetry.addData("arm", "Arm Position: " + String.format("%.2f", armvalue));
        telemetry.addData("brushes", "Brushes " + BrushesOn);



        try {
            Thread.sleep(10); //hyperdrive warp speed preventer tool (also gives the timer some time to check if time is up... This prevents the phone from crashing mid-run.
        } catch (Exception exception) {  } //nothing will happen, but java complains if we don't try to catch it.

    }
}
