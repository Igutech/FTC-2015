package com.qualcomm.ftcrobotcontroller.opmodes.unused;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Tilman on 10/28/2015.
 */
public class EncoderDriver{
    DcMotor leftMotor2;
    DcMotor rightMotor2;
    DcMotor singleMotor;
    int ticksPerWheelRot;
    double wheelDiameter, ticksneeded;
    String motor1name, motor2name;
    double distanceForOneRotation;
    double conversionFactor = 2.54;

    private void setupDrivetrain(HardwareMap hardwareMap)
    {
        leftMotor2 = hardwareMap.dcMotor.get("left2");
        rightMotor2 = hardwareMap.dcMotor.get("right");
        leftMotor2.setDirection(DcMotor.Direction.FORWARD);
        rightMotor2.setDirection(DcMotor.Direction.REVERSE);
    }

    /**
     * This constructor creates a two wheel drivetrain, created by Tilman G
     *
     * @param  hardwareMap  Hardwaremap is needed to map the motors to proper locations
     * @param  wheelDiam Diameter of the wheel in inches, automatically converts to cm
     * @param motortype Used to set motor type, use "40" for neverrest 40 and "60" for neverrest 60
     * @param m1 motor name of the left motor
     * @param m2 motor name of the right motor
     */
    public EncoderDriver(HardwareMap hardwareMap, int wheelDiam, int motortype, String m1, String m2) {
        setupDrivetrain(hardwareMap);
        wheelDiameter = wheelDiam;
        wheelDiameter = wheelDiameter*conversionFactor;
        distanceForOneRotation = wheelDiameter*3.141592;
        if (motortype==40)
        {
            ticksPerWheelRot = 1120;
        }
        else if (motortype==60)
        {
            ticksPerWheelRot = 1680;
        }
        else
        {
            ticksPerWheelRot = 1120;
        }
        motor1name = m1;
        motor2name = m2;
    }

    public void resetEncoders()
    {
        leftMotor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightMotor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        shortDelay();

    }
    public void setToRTPmode()
    {
        leftMotor2.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightMotor2.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        shortDelay();
    }
    public void setTargets(int ticks)
    {
        leftMotor2.setTargetPosition(ticks);
        rightMotor2.setTargetPosition(ticks);
    }
    public void shortDelay()
    {
        try {
            Thread.sleep(50);
        } catch (Exception exception) {  } //nothing will happen, but java complains if we don't try to catch it.
    }
    public void driveDistance(int cm, float power) //power must always be positive, direction must be negative
    {
        ticksneeded = cm / distanceForOneRotation; //divide the distance needed to travel by distance covered in one wheel rotation
        ticksneeded = ticksneeded / ticksPerWheelRot; //calculate ticks of encoder needed to travel distance
        resetEncoders(); //set encoders to zero
        setToRTPmode(); //sets motors to RTP mode
        setTargets(Integer.valueOf((int) Math.round(ticksneeded))); //gets the integer value of the double ticks needed and sets as target
        leftMotor2.setPower(power);
        rightMotor2.setPower(power);
        shortDelay();
        while (leftMotor2.isBusy() && rightMotor2.isBusy())
        {
            //delay, stuff is happening
        }
        while (leftMotor2.isBusy())
        {
            //secondCheck
        }
        while (rightMotor2.isBusy())
        {
            //secondCheck
        }
        shortDelay();
        leftMotor2.setPower(0);
        rightMotor2.setPower(0);


    }

}
